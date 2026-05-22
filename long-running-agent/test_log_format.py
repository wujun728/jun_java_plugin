#!/usr/bin/env python3
"""测试日志格式输出 - 验证任务耗时、得分等信息的显示"""
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

# 添加当前目录到路径
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

from task_manager import TaskManager
from datetime import datetime

def test_summary_format():
    """测试 summary 格式是否包含任务详情"""
    print("=" * 60)
    print("测试 TaskManager.summary() 输出格式")
    print("=" * 60)

    task_mgr = TaskManager('../task_list.json', max_attempts=3)

    if not task_mgr.has_existing_tasks():
        print("错误: task_list.json 不存在")
        return

    task_mgr.load()

    print("\n当前任务摘要:")
    print(task_mgr.summary())
    print()


def test_report_format():
    """测试 format_report 格式是否包含任务耗时和得分"""
    print("=" * 60)
    print("测试 format_report() 输出格式")
    print("=" * 60)

    task_mgr = TaskManager('../task_list.json', max_attempts=3)

    if not task_mgr.has_existing_tasks():
        print("错误: task_list.json 不存在")
        return

    task_mgr.load()

    # 模拟 main.py 中的 format_report 函数
    from main import format_report

    report = task_mgr.generate_report()
    print("\n最终报告:")
    print(format_report(report))
    print()


def test_task_timing():
    """测试任务时间计算"""
    print("=" * 60)
    print("测试任务时间计算")
    print("=" * 60)

    task_mgr = TaskManager('../task_list.json', max_attempts=3)

    if not task_mgr.has_existing_tasks():
        print("错误: task_list.json 不存在")
        return

    task_mgr.load()

    print("\n已完成任务的时间信息:")
    for t in task_mgr.tasks:
        if t['status'] == 'passed' and t.get('started_at') and t.get('completed_at'):
            try:
                start = datetime.fromisoformat(t['started_at'])
                end = datetime.fromisoformat(t['completed_at'])
                duration = (end - start).total_seconds()

                title = t.get('title', t.get('description', ''))[:40]
                score = t.get('evaluation', {}).get('score', 0) if t.get('evaluation') else 0

                print(f"\n任务 {t['id']}: {title}")
                print(f"  开始: {t['started_at']}")
                print(f"  结束: {t['completed_at']}")
                print(f"  耗时: {duration:.2f}秒 ({duration/60:.2f}分钟)")
                print(f"  得分: {score}/100")
            except (ValueError, TypeError) as e:
                print(f"任务 {t['id']} 时间解析错误: {e}")


if __name__ == '__main__':
    print("\n测试日志格式改进\n")

    # 测试 summary
    test_summary_format()
    print("\n" + "=" * 60 + "\n")

    # 测试 report
    test_report_format()
    print("\n" + "=" * 60 + "\n")

    # 测试时间计算
    test_task_timing()
    print("\n测试完成！")
