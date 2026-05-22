#!/usr/bin/env python3
"""完整测试套件 - 验证所有功能"""
import sys
import os

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

import subprocess


def run_test(test_name, test_script):
    """运行单个测试脚本"""
    print(f"\n{'=' * 60}")
    print(f"运行测试: {test_name}")
    print(f"{'=' * 60}")

    try:
        result = subprocess.run(
            [sys.executable, test_script],
            capture_output=True,
            text=True,
            encoding='utf-8',
            errors='replace',
            timeout=30,
        )

        if result.returncode == 0:
            print(f"✓ {test_name} 通过")
            return True
        else:
            print(f"✗ {test_name} 失败")
            print("STDOUT:", result.stdout)
            print("STDERR:", result.stderr)
            return False
    except Exception as e:
        print(f"✗ {test_name} 异常: {e}")
        return False


def test_framework_commands():
    """测试框架命令"""
    print(f"\n{'=' * 60}")
    print(f"测试框架命令")
    print(f"{'=' * 60}")

    commands = [
        ("main.py --help", "帮助命令"),
        ("main.py --status", "状态查询"),
    ]

    results = []
    for cmd, name in commands:
        try:
            result = subprocess.run(
                [sys.executable] + cmd.split(),
                capture_output=True,
                text=True,
                encoding='utf-8',
                errors='replace',
                timeout=10,
                cwd=os.path.dirname(os.path.abspath(__file__)),
            )

            if result.returncode == 0 or 'usage' in result.stdout.lower() or 'help' in result.stdout.lower():
                print(f"✓ {name} 通过")
                results.append(True)
            else:
                print(f"✗ {name} 失败")
                results.append(False)
        except Exception as e:
            print(f"✗ {name} 异常: {e}")
            results.append(False)

    return all(results)


def check_files():
    """检查重要文件是否存在"""
    print(f"\n{'=' * 60}")
    print(f"检查文件完整性")
    print(f"{'=' * 60}")

    required_files = [
        'main.py',
        'config.py',
        'task_manager.py',
        'agent_runner.py',
        'model_provider.py',
        'logger.py',
        'prompt_renderer.py',
        'config.json',
        'config.qoder.json',
        'config.deepseek.json',
        'config.glm.json',
        'config.doubao.json',
        'README.md',
        'CHANGELOG.md',
        'MODEL_PROVIDER_GUIDE.md',
        'REFACTORING_SUMMARY.md',
        'QUICK_REFERENCE.md',
    ]

    all_exist = True
    for file in required_files:
        if os.path.exists(file):
            print(f"✓ {file}")
        else:
            print(f"✗ {file} 不存在")
            all_exist = False

    return all_exist


def check_imports():
    """检查模块导入"""
    print(f"\n{'=' * 60}")
    print(f"检查模块导入")
    print(f"{'=' * 60}")

    modules = [
        'config',
        'task_manager',
        'agent_runner',
        'model_provider',
        'logger',
        'prompt_renderer',
    ]

    all_imported = True
    for module in modules:
        try:
            __import__(module)
            print(f"✓ {module}")
        except ImportError as e:
            print(f"✗ {module} 导入失败: {e}")
            all_imported = False

    return all_imported


def main():
    """主测试函数"""
    print("\n" + "=" * 60)
    print("长时运行智能体框架 - 完整测试套件")
    print("=" * 60)

    results = {}

    # 1. 检查文件
    results['文件完整性'] = check_files()

    # 2. 检查导入
    results['模块导入'] = check_imports()

    # 3. 测试模型提供者
    if os.path.exists('test_model_provider.py'):
        results['模型提供者'] = run_test('模型提供者测试', 'test_model_provider.py')

    # 4. 测试日志格式
    if os.path.exists('test_log_format.py'):
        results['日志格式'] = run_test('日志格式测试', 'test_log_format.py')

    # 5. 测试框架命令
    results['框架命令'] = test_framework_commands()

    # 汇总结果
    print(f"\n{'=' * 60}")
    print("测试结果汇总")
    print(f"{'=' * 60}")

    total = len(results)
    passed = sum(1 for v in results.values() if v)

    for test_name, passed_flag in results.items():
        status = "✓ 通过" if passed_flag else "✗ 失败"
        print(f"{test_name:20} {status}")

    print(f"\n总计: {passed}/{total} 通过 ({passed/total*100:.1f}%)")

    if passed == total:
        print("\n🎉 所有测试通过！")
        return 0
    else:
        print(f"\n⚠️  有 {total - passed} 个测试失败")
        return 1


if __name__ == '__main__':
    sys.exit(main())
