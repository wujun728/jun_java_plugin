#!/usr/bin/env python3
"""运行所有测试的主脚本"""
import sys
import os
import subprocess
import time

# Windows 控制台 UTF-8 支持
if os.name == 'nt':
    os.environ.setdefault('PYTHONIOENCODING', 'utf-8')
    try:
        sys.stdout.reconfigure(encoding='utf-8')
        sys.stderr.reconfigure(encoding='utf-8')
    except Exception:
        pass


def run_test_script(script_name, description):
    """运行单个测试脚本"""
    print("\n" + "=" * 70)
    print(f"运行测试: {description}")
    print("=" * 70)

    script_path = os.path.join('tests', script_name)

    if not os.path.exists(script_path):
        print(f"⚠️  测试脚本不存在: {script_path}")
        return None

    start_time = time.time()

    try:
        result = subprocess.run(
            [sys.executable, script_path],
            capture_output=True,
            text=True,
            encoding='utf-8',
            errors='replace',
            timeout=120,
        )

        elapsed = time.time() - start_time

        # 输出测试结果
        print(result.stdout)

        if result.stderr:
            print("STDERR:")
            print(result.stderr)

        if result.returncode == 0:
            print(f"\n✅ {description} - 通过 ({elapsed:.2f}秒)")
            return True
        else:
            print(f"\n❌ {description} - 失败 ({elapsed:.2f}秒)")
            return False

    except subprocess.TimeoutExpired:
        print(f"\n⏱️  {description} - 超时")
        return False
    except Exception as e:
        print(f"\n❌ {description} - 异常: {e}")
        return False


def main():
    """主函数"""
    print("\n" + "=" * 70)
    print("长时运行智能体框架 - 完整测试套件")
    print("=" * 70)
    print(f"测试时间: {time.strftime('%Y-%m-%d %H:%M:%S')}")
    print("=" * 70)

    # 确保在正确的目录
    if not os.path.exists('main.py'):
        print("错误: 请在 long-running-agent 目录中运行此脚本")
        return 1

    results = []
    start_time = time.time()

    # 定义要运行的测试
    tests = [
        ('test_model_provider.py', '模型提供者测试'),
        ('test_integration.py', '集成测试'),
        ('test_core_functionality.py', '核心功能测试'),
    ]

    # 运行所有测试
    for script, description in tests:
        result = run_test_script(script, description)
        if result is not None:
            results.append((description, result))

    # 汇总结果
    total_time = time.time() - start_time

    print("\n" + "=" * 70)
    print("测试结果汇总")
    print("=" * 70)

    total = len(results)
    passed = sum(1 for _, success in results if success)

    for test_name, success in results:
        status = "✅ 通过" if success else "❌ 失败"
        print(f"{test_name:40} {status}")

    print("\n" + "-" * 70)
    print(f"总计: {passed}/{total} 通过 ({passed/total*100 if total > 0 else 0:.1f}%)")
    print(f"总耗时: {total_time:.2f}秒")
    print("=" * 70)

    if passed == total:
        print("\n🎉 所有测试通过！")
        print("\n测试报告已生成: TEST_REPORT.md")
        return 0
    else:
        print(f"\n⚠️  有 {total - passed} 个测试失败")
        return 1


if __name__ == '__main__':
    sys.exit(main())
