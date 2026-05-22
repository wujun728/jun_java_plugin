#!/usr/bin/env python3
"""测试新增功能：分层模型支持、BudgetTracker、预算熔断"""
import sys
import os
import json
import tempfile
import shutil

if os.name == 'nt':
    os.environ.setdefault('PYTHONIOENCODING', 'utf-8')
    try:
        sys.stdout.reconfigure(encoding='utf-8')
        sys.stderr.reconfigure(encoding='utf-8')
    except Exception:
        pass

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from config import Config
from logger import ExecutionLogger
from model_provider import create_model_provider, ClaudeProvider

# 导入 BudgetTracker
sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
from main import BudgetTracker, MODEL_PRICING


def create_config_file(temp_dir, overrides=None):
    """创建测试配置文件"""
    data = {
        "project_name": "test",
        "work_directory": temp_dir,
        "agent": {
            "model_provider": "claude",
            "model": "sonnet",
            "planner_model": "",
            "worker_model": "",
            "evaluator_model": "",
            "planner_max_turns": 10,
            "worker_max_turns": 10,
            "evaluator_max_turns": 5,
            "max_sessions": 5,
            "budget_max_dollars": 10.0,
        },
        "retry": {"max_attempts": 3},
        "paths": {}
    }
    if overrides:
        for k, v in overrides.items():
            if isinstance(v, dict) and k in data:
                data[k].update(v)
            else:
                data[k] = v

    path = os.path.join(temp_dir, 'config.json')
    with open(path, 'w', encoding='utf-8') as f:
        json.dump(data, f)
    return path


def test_layered_model_defaults():
    """测试分层模型默认值（为空时 fallback 到 model）"""
    print("=" * 60)
    print("测试分层模型 — 默认 fallback")
    print("=" * 60)

    temp_dir = tempfile.mkdtemp()
    try:
        path = create_config_file(temp_dir)
        config = Config.load(path)

        assert config.model == 'sonnet', f"期望 sonnet，得到 {config.model}"
        assert config.planner_model == 'sonnet', f"planner_model 应 fallback 到 sonnet，得到 {config.planner_model}"
        assert config.worker_model == 'sonnet', f"worker_model 应 fallback 到 sonnet，得到 {config.worker_model}"
        assert config.evaluator_model == 'sonnet', f"evaluator_model 应 fallback 到 sonnet，得到 {config.evaluator_model}"
        print("  ✓ 分层模型为空时正确 fallback 到默认 model")
        return True
    except AssertionError as e:
        print(f"  ✗ {e}")
        return False
    finally:
        shutil.rmtree(temp_dir)


def test_layered_model_custom():
    """测试分层模型自定义值"""
    print("\n" + "=" * 60)
    print("测试分层模型 — 自定义值")
    print("=" * 60)

    temp_dir = tempfile.mkdtemp()
    try:
        path = create_config_file(temp_dir, {
            "agent": {
                "model_provider": "claude",
                "model": "sonnet",
                "planner_model": "opus",
                "worker_model": "sonnet",
                "evaluator_model": "haiku",
                "budget_max_dollars": 5.0,
            }
        })
        config = Config.load(path)

        assert config.planner_model == 'opus', f"期望 opus，得到 {config.planner_model}"
        assert config.worker_model == 'sonnet', f"期望 sonnet，得到 {config.worker_model}"
        assert config.evaluator_model == 'haiku', f"期望 haiku，得到 {config.evaluator_model}"
        print("  ✓ planner=opus, worker=sonnet, evaluator=haiku")
        return True
    except AssertionError as e:
        print(f"  ✗ {e}")
        return False
    finally:
        shutil.rmtree(temp_dir)


def test_budget_tracker_basic():
    """测试 BudgetTracker 基本功能"""
    print("\n" + "=" * 60)
    print("测试 BudgetTracker — 基本计费")
    print("=" * 60)

    bt = BudgetTracker(budget_max=10.0)

    # 模拟一次 sonnet 调用: 50K input + 10K output
    bt.record({'input_tokens': 50000, 'output_tokens': 10000}, 'sonnet')

    # sonnet: input=$3/M, output=$15/M
    # 50K * 3 / 1M + 10K * 15 / 1M = 0.15 + 0.15 = 0.30
    expected_cost = 0.30
    assert abs(bt.total_cost - expected_cost) < 0.001, f"期望 ${expected_cost}，得到 ${bt.total_cost}"
    assert bt.total_input_tokens == 50000
    assert bt.total_output_tokens == 10000
    assert bt.call_count == 1
    assert not bt.is_over_budget()
    print(f"  ✓ 单次调用计费正确: ${bt.total_cost:.2f}")
    print(f"  ✓ {bt.summary()}")
    return True


def test_budget_tracker_multi_model():
    """测试 BudgetTracker 多模型计费"""
    print("\n" + "=" * 60)
    print("测试 BudgetTracker — 多模型混合计费")
    print("=" * 60)

    bt = BudgetTracker(budget_max=10.0)

    # Opus 调用: 100K input + 20K output
    # 100K * 15/M + 20K * 75/M = 1.5 + 1.5 = 3.0
    bt.record({'input_tokens': 100000, 'output_tokens': 20000}, 'opus')

    # Sonnet 调用: 50K input + 10K output = 0.30
    bt.record({'input_tokens': 50000, 'output_tokens': 10000}, 'sonnet')

    # Haiku 调用: 30K input + 5K output
    # 30K * 0.25/M + 5K * 1.25/M = 0.0075 + 0.00625 = 0.01375
    bt.record({'input_tokens': 30000, 'output_tokens': 5000}, 'haiku')

    expected = 3.0 + 0.30 + 0.01375
    assert abs(bt.total_cost - expected) < 0.001, f"期望 ${expected:.4f}，得到 ${bt.total_cost:.4f}"
    assert bt.call_count == 3
    assert bt.total_input_tokens == 180000
    assert bt.total_output_tokens == 35000
    print(f"  ✓ 混合计费正确: ${bt.total_cost:.4f}")
    print(f"  ✓ {bt.summary()}")
    return True


def test_budget_over_limit():
    """测试预算熔断"""
    print("\n" + "=" * 60)
    print("测试 BudgetTracker — 预算熔断")
    print("=" * 60)

    bt = BudgetTracker(budget_max=1.0)

    # Opus: 100K input + 20K output = $3.0，超过 $1.0
    bt.record({'input_tokens': 100000, 'output_tokens': 20000}, 'opus')

    assert bt.is_over_budget(), f"应该超预算，当前 ${bt.total_cost:.2f}"
    print(f"  ✓ 超预算正确触发: ${bt.total_cost:.2f} > ${bt.budget_max:.2f}")

    # 预算为 0 时不限制
    bt2 = BudgetTracker(budget_max=0)
    bt2.record({'input_tokens': 1000000, 'output_tokens': 1000000}, 'opus')
    assert not bt2.is_over_budget(), "budget_max=0 时不应触发熔断"
    print("  ✓ budget_max=0 时不限制")

    return True


def test_budget_empty_usage():
    """测试空 usage 数据"""
    print("\n" + "=" * 60)
    print("测试 BudgetTracker — 空 usage")
    print("=" * 60)

    bt = BudgetTracker(budget_max=10.0)
    bt.record({}, 'sonnet')  # 空 usage
    bt.record({'input_tokens': 0, 'output_tokens': 0}, 'sonnet')  # 零 token

    assert bt.total_cost == 0.0
    assert bt.call_count == 2
    print("  ✓ 空 usage 不会导致错误，费用为 $0")
    return True


def test_model_pricing_table():
    """测试模型定价表完整性"""
    print("\n" + "=" * 60)
    print("测试模型定价表")
    print("=" * 60)

    expected_models = ['opus', 'sonnet', 'haiku']
    for m in expected_models:
        assert m in MODEL_PRICING, f"缺少 {m} 定价"
        assert 'input' in MODEL_PRICING[m], f"{m} 缺少 input 定价"
        assert 'output' in MODEL_PRICING[m], f"{m} 缺少 output 定价"
        print(f"  ✓ {m}: input=${MODEL_PRICING[m]['input']}/M, output=${MODEL_PRICING[m]['output']}/M")

    # Opus 应该比 Sonnet 贵
    assert MODEL_PRICING['opus']['input'] > MODEL_PRICING['sonnet']['input']
    assert MODEL_PRICING['sonnet']['input'] > MODEL_PRICING['haiku']['input']
    print("  ✓ 定价层级正确: opus > sonnet > haiku")
    return True


def test_config_budget_field():
    """测试配置文件中的 budget_max_dollars 字段"""
    print("\n" + "=" * 60)
    print("测试配置 budget_max_dollars")
    print("=" * 60)

    temp_dir = tempfile.mkdtemp()
    try:
        # 默认值
        path = create_config_file(temp_dir)
        config = Config.load(path)
        assert config.budget_max_dollars == 10.0, f"默认应为 10.0，得到 {config.budget_max_dollars}"
        print(f"  ✓ 默认预算: ${config.budget_max_dollars}")

        # 自定义值
        path2 = create_config_file(temp_dir, {"agent": {
            "model_provider": "claude", "model": "sonnet",
            "budget_max_dollars": 25.5,
        }})
        config2 = Config.load(path2)
        assert config2.budget_max_dollars == 25.5
        print(f"  ✓ 自定义预算: ${config2.budget_max_dollars}")
        return True
    except AssertionError as e:
        print(f"  ✗ {e}")
        return False
    finally:
        shutil.rmtree(temp_dir)


def test_model_override_in_provider():
    """测试 ClaudeProvider invoke 的 model_override 参数"""
    print("\n" + "=" * 60)
    print("测试 model_override 参数传递")
    print("=" * 60)

    temp_dir = tempfile.mkdtemp()
    try:
        path = create_config_file(temp_dir)
        config = Config.load(path)
        logger = ExecutionLogger(os.path.join(temp_dir, 'test.jsonl'))
        provider = create_model_provider(config, logger)

        assert isinstance(provider, ClaudeProvider)

        # 验证 invoke 方法接受 model_override 参数（不实际调用）
        import inspect
        sig = inspect.signature(provider.invoke)
        assert 'model_override' in sig.parameters, "invoke 缺少 model_override 参数"
        print("  ✓ ClaudeProvider.invoke 接受 model_override 参数")

        return True
    except AssertionError as e:
        print(f"  ✗ {e}")
        return False
    finally:
        shutil.rmtree(temp_dir)


def main():
    print("\n" + "=" * 60)
    print("新功能测试：分层模型 + BudgetTracker + 预算熔断")
    print("=" * 60)

    tests = [
        ('分层模型默认 fallback', test_layered_model_defaults),
        ('分层模型自定义值', test_layered_model_custom),
        ('BudgetTracker 基本计费', test_budget_tracker_basic),
        ('BudgetTracker 多模型混合', test_budget_tracker_multi_model),
        ('预算熔断', test_budget_over_limit),
        ('空 usage 处理', test_budget_empty_usage),
        ('模型定价表', test_model_pricing_table),
        ('配置 budget_max_dollars', test_config_budget_field),
        ('model_override 参数', test_model_override_in_provider),
    ]

    results = []
    for name, fn in tests:
        try:
            ok = fn()
            results.append((name, ok))
        except Exception as e:
            print(f"  ✗ 异常: {e}")
            results.append((name, False))

    print("\n" + "=" * 60)
    print("测试结果汇总")
    print("=" * 60)
    total = len(results)
    passed = sum(1 for _, ok in results if ok)
    for name, ok in results:
        print(f"  {'✓' if ok else '✗'} {name}")
    print(f"\n总计: {passed}/{total} 通过 ({passed/total*100:.1f}%)")

    if passed == total:
        print("\n所有新功能测试通过！")
        return 0
    else:
        print(f"\n有 {total - passed} 个测试失败")
        return 1


if __name__ == '__main__':
    sys.exit(main())
