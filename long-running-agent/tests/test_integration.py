#!/usr/bin/env python3
"""集成测试 - 测试各组件的集成"""
import sys
import os
import json
import tempfile
import shutil
from unittest.mock import patch, MagicMock

# Windows 控制台 UTF-8 支持
if os.name == 'nt':
    os.environ.setdefault('PYTHONIOENCODING', 'utf-8')
    try:
        sys.stdout.reconfigure(encoding='utf-8')
        sys.stderr.reconfigure(encoding='utf-8')
    except Exception:
        pass

# 添加父目录到路径
sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from config import Config
from task_manager import TaskManager
from logger import ExecutionLogger


def test_config_with_all_providers():
    """测试所有模型提供者的配置"""
    print("=" * 60)
    print("测试配置加载（所有模型提供者）")
    print("=" * 60)

    test_cases = [
        ('claude', 'opus', 'ClaudeProvider'),
        ('qodercli', 'qwen-plus', 'QoderCliProvider'),
        ('deepseek', 'deepseek-chat', 'DeepSeekProvider'),
        ('glm', 'glm-4-plus', 'GLMProvider'),
        ('doubao', 'doubao-pro-32k', 'DoubaoProvider'),
    ]

    results = []
    temp_dir = tempfile.mkdtemp()

    try:
        for provider, model, expected_class in test_cases:
            # 创建配置
            config_data = {
                "project_name": "test",
                "work_directory": temp_dir,
                "agent": {
                    "model_provider": provider,
                    "model": model,
                    f"{provider}_config" if provider != 'claude' else "qoder_config": {
                        "api_key": "test_key",
                        "base_url": "https://test.com"
                    }
                },
                "retry": {"max_attempts": 3},
                "paths": {}
            }

            config_file = os.path.join(temp_dir, f'config_{provider}.json')
            with open(config_file, 'w', encoding='utf-8') as f:
                json.dump(config_data, f)

            # 加载配置
            config = Config.load(config_file)

            # 验证
            success = (
                config.model_provider == provider and
                config.model == model
            )

            results.append((provider, success))
            print(f"  {provider:15} {model:20} {'✓' if success else '✗'}")

    finally:
        shutil.rmtree(temp_dir)

    passed = sum(1 for _, success in results if success)
    print(f"\n通过: {passed}/{len(results)}")
    return passed == len(results)


def test_task_manager_workflow():
    """测试任务管理器工作流"""
    print("\n" + "=" * 60)
    print("测试任务管理器工作流")
    print("=" * 60)

    temp_dir = tempfile.mkdtemp()

    try:
        task_file = os.path.join(temp_dir, 'tasks.json')

        # 创建任务列表
        tasks = {
            "meta": {"project_name": "test"},
            "tasks": [
                {
                    "id": "T001",
                    "title": "任务1",
                    "description": "第一个任务",
                    "status": "pending",
                    "dependencies": [],
                    "priority": 1,
                    "verification_steps": [],
                    "attempts": 0,
                    "max_attempts": 3
                },
                {
                    "id": "T002",
                    "title": "任务2",
                    "description": "第二个任务",
                    "status": "pending",
                    "dependencies": ["T001"],
                    "priority": 2,
                    "verification_steps": [],
                    "attempts": 0,
                    "max_attempts": 3
                }
            ]
        }

        with open(task_file, 'w', encoding='utf-8') as f:
            json.dump(tasks, f)

        # 加载任务
        mgr = TaskManager(task_file, max_attempts=3)
        mgr.load()

        print(f"  加载任务: {mgr.total_count()} 个")

        # 测试获取下一个任务（应该是T001）
        task1 = mgr.next_task()
        assert task1['id'] == 'T001', f"期望 T001，得到 {task1['id']}"
        print("  ✓ 获取第一个任务: T001")

        # 更新状态为进行中
        mgr.update_status('T001', 'in_progress')
        print("  ✓ 更新状态为 in_progress")

        # 标记为通过
        mgr.mark_passed('T001', {'passed': True, 'score': 90})
        print("  ✓ 标记 T001 为通过")

        # 现在应该可以获取T002
        task2 = mgr.next_task()
        assert task2['id'] == 'T002', f"期望 T002，得到 {task2['id']}"
        print("  ✓ 获取第二个任务: T002（依赖已满足）")

        # 测试依赖阻塞
        mgr2 = TaskManager(task_file, max_attempts=3)
        mgr2.load()
        # 重新加载，T001还是pending
        mgr2.tasks[0]['status'] = 'pending'
        mgr2.tasks[1]['status'] = 'pending'

        next_task = mgr2.next_task()
        assert next_task['id'] == 'T001', "依赖未满足时应返回T001"
        print("  ✓ 依赖阻塞测试通过")

        # 测试摘要
        summary = mgr.summary()
        assert '进度:' in summary
        print("  ✓ 生成摘要成功")

        # 测试报告
        report = mgr.generate_report()
        assert 'total' in report
        assert 'passed' in report
        print("  ✓ 生成报告成功")

        print("\n所有任务管理器测试通过 ✓")
        return True

    except AssertionError as e:
        print(f"\n测试失败: {e}")
        return False
    finally:
        shutil.rmtree(temp_dir)


def test_logger():
    """测试日志记录器"""
    print("\n" + "=" * 60)
    print("测试日志记录器")
    print("=" * 60)

    temp_dir = tempfile.mkdtemp()

    try:
        log_file = os.path.join(temp_dir, 'test.jsonl')
        logger = ExecutionLogger(log_file)

        # 测试事件记录
        logger.event('test_event', {'key': 'value'})
        logger.info('测试信息')
        logger.warn('测试警告')
        logger.error('测试错误')

        print("  ✓ 记录各种类型的事件")

        # 验证日志文件
        assert os.path.exists(log_file), "日志文件未创建"
        print("  ✓ 日志文件创建成功")

        # 读取并验证
        with open(log_file, 'r', encoding='utf-8') as f:
            lines = f.readlines()
            assert len(lines) == 4, f"期望4行日志，得到{len(lines)}行"
            print(f"  ✓ 记录了 {len(lines)} 条日志")

            # 验证JSON格式
            for line in lines:
                data = json.loads(line)
                assert 'ts' in data
                assert 'event' in data
            print("  ✓ 日志格式正确（JSON）")

        print("\n所有日志测试通过 ✓")
        return True

    except AssertionError as e:
        print(f"\n测试失败: {e}")
        return False
    finally:
        shutil.rmtree(temp_dir)


def test_error_handling():
    """测试错误处理"""
    print("\n" + "=" * 60)
    print("测试错误处理")
    print("=" * 60)

    temp_dir = tempfile.mkdtemp()

    try:
        # 测试加载不存在的配置文件
        print("  测试加载不存在的配置文件...")
        try:
            Config.load(os.path.join(temp_dir, 'nonexistent.json'))
            print("  ✗ 应该抛出异常")
            return False
        except SystemExit:
            print("  ✓ 正确抛出SystemExit")

        # 测试加载不存在的任务列表
        print("  测试加载不存在的任务列表...")
        mgr = TaskManager(os.path.join(temp_dir, 'nonexistent.json'))
        assert not mgr.has_existing_tasks(), "应该返回False"
        print("  ✓ 正确返回False")

        # 测试更新不存在的任务
        print("  测试更新不存在的任务...")
        try:
            task_file = os.path.join(temp_dir, 'tasks.json')
            with open(task_file, 'w') as f:
                json.dump({"meta": {}, "tasks": []}, f)

            mgr = TaskManager(task_file)
            mgr.load()
            mgr.update_status('NONEXISTENT', 'in_progress')
            print("  ✗ 应该抛出异常")
            return False
        except ValueError as e:
            print(f"  ✓ 正确抛出ValueError: {e}")

        print("\n所有错误处理测试通过 ✓")
        return True

    except Exception as e:
        print(f"\n意外错误: {e}")
        return False
    finally:
        shutil.rmtree(temp_dir)


def test_task_retry_logic():
    """测试任务重试逻辑"""
    print("\n" + "=" * 60)
    print("测试任务重试逻辑")
    print("=" * 60)

    temp_dir = tempfile.mkdtemp()

    try:
        task_file = os.path.join(temp_dir, 'tasks.json')

        tasks = {
            "meta": {"project_name": "test"},
            "tasks": [{
                "id": "T001",
                "title": "测试任务",
                "description": "测试重试",
                "status": "pending",
                "dependencies": [],
                "priority": 1,
                "verification_steps": [],
                "attempts": 0,
                "max_attempts": 3,
                "error_history": []
            }]
        }

        with open(task_file, 'w', encoding='utf-8') as f:
            json.dump(tasks, f)

        mgr = TaskManager(task_file, max_attempts=3)
        mgr.load()

        # 第一次尝试
        task = mgr.next_task()
        mgr.update_status('T001', 'in_progress')
        print(f"  第1次尝试，attempts={mgr.tasks[0]['attempts']}")

        # 标记失败
        mgr.mark_failed('T001', {'passed': False, 'score': 0, 'feedback': '第一次失败'})
        print(f"  标记失败，status={mgr.tasks[0]['status']}")

        # 应该还能获取（重试）
        task = mgr.next_task()
        assert task is not None, "应该还能重试"
        assert task['id'] == 'T001'
        print("  ✓ 可以重试")

        # 第二次尝试
        mgr.update_status('T001', 'in_progress')
        print(f"  第2次尝试，attempts={mgr.tasks[0]['attempts']}")

        mgr.mark_failed('T001', {'passed': False, 'score': 0, 'feedback': '第二次失败'})

        # 第三次尝试
        task = mgr.next_task()
        assert task is not None, "应该还能重试"
        mgr.update_status('T001', 'in_progress')
        print(f"  第3次尝试，attempts={mgr.tasks[0]['attempts']}")

        mgr.mark_failed('T001', {'passed': False, 'score': 0, 'feedback': '第三次失败'})

        # 应该被跳过
        task = mgr.next_task()
        assert task is None, "超过重试次数应该返回None"
        assert mgr.tasks[0]['status'] == 'skipped', "状态应该是skipped"
        print("  ✓ 超过重试次数后被跳过")

        # 检查错误历史
        assert len(mgr.tasks[0]['error_history']) == 3, "应该有3条错误记录"
        print(f"  ✓ 记录了 {len(mgr.tasks[0]['error_history'])} 条错误历史")

        print("\n所有重试逻辑测试通过 ✓")
        return True

    except AssertionError as e:
        print(f"\n测试失败: {e}")
        return False
    finally:
        shutil.rmtree(temp_dir)


def main():
    """运行所有集成测试"""
    print("\n" + "=" * 60)
    print("集成测试套件")
    print("=" * 60)

    results = []

    # 运行所有测试
    results.append(('配置加载', test_config_with_all_providers()))
    results.append(('任务管理器工作流', test_task_manager_workflow()))
    results.append(('日志记录器', test_logger()))
    results.append(('错误处理', test_error_handling()))
    results.append(('任务重试逻辑', test_task_retry_logic()))

    # 汇总结果
    print("\n" + "=" * 60)
    print("集成测试结果")
    print("=" * 60)

    total = len(results)
    passed = sum(1 for _, success in results if success)

    for test_name, success in results:
        status = "✓ 通过" if success else "✗ 失败"
        print(f"{test_name:30} {status}")

    print(f"\n总计: {passed}/{total} 通过 ({passed/total*100:.1f}%)")

    if passed == total:
        print("\n🎉 所有集成测试通过！")
        return 0
    else:
        print(f"\n⚠️  有 {total - passed} 个测试失败")
        return 1


if __name__ == '__main__':
    sys.exit(main())
