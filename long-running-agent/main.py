#!/usr/bin/env python3
"""
长时运行智能体框架 v2 — 主编排器

用法:
    python main.py --task "构建一个用户管理系统"       # 新任务
    python main.py --resume                            # 从断点恢复
    python main.py --resume --config custom.json       # 指定配置恢复
    python main.py --status                            # 查看进度
    python main.py --report                            # 生成报告
"""
import argparse
import json
import os
import sys
import time

# Windows 控制台 UTF-8 支持
if os.name == 'nt':
    os.environ.setdefault('PYTHONIOENCODING', 'utf-8')
    try:
        sys.stdout.reconfigure(encoding='utf-8')
        sys.stderr.reconfigure(encoding='utf-8')
    except Exception:
        pass

from config import Config
from task_manager import TaskManager
from agent_runner import AgentRunner
from logger import ExecutionLogger


# Claude 模型定价 (USD per million tokens)
MODEL_PRICING = {
    'opus': {'input': 15.0, 'output': 75.0},
    'sonnet': {'input': 3.0, 'output': 15.0},
    'haiku': {'input': 0.25, 'output': 1.25},
    'claude-sonnet-4-6': {'input': 3.0, 'output': 15.0},
    'claude-opus-4-6': {'input': 15.0, 'output': 75.0},
    'claude-haiku-4-5-20251001': {'input': 0.25, 'output': 1.25},
}


class BudgetTracker:
    """追踪 token 用量和费用"""

    def __init__(self, budget_max: float = 10.0):
        self.budget_max = budget_max
        self.total_input_tokens = 0
        self.total_output_tokens = 0
        self.total_cost = 0.0
        self.call_count = 0

    def record(self, usage: dict, model: str):
        input_t = usage.get('input_tokens', 0)
        output_t = usage.get('output_tokens', 0)
        self.total_input_tokens += input_t
        self.total_output_tokens += output_t
        self.call_count += 1

        # 计算费用
        pricing = MODEL_PRICING.get(model, MODEL_PRICING.get('sonnet'))
        cost = (input_t * pricing['input'] + output_t * pricing['output']) / 1_000_000
        self.total_cost += cost

    def is_over_budget(self) -> bool:
        return self.budget_max > 0 and self.total_cost >= self.budget_max

    def summary(self) -> str:
        return (
            f"Token 用量: input={self.total_input_tokens:,} output={self.total_output_tokens:,} "
            f"| 调用次数: {self.call_count} | 累计费用: ${self.total_cost:.2f} / ${self.budget_max:.2f}"
        )


def parse_args():
    parser = argparse.ArgumentParser(description='长时运行智能体框架 v2')
    parser.add_argument('--task', type=str, help='任务描述')
    parser.add_argument('--resume', action='store_true', help='从断点恢复')
    parser.add_argument('--config', type=str, default=None, help='配置文件路径')
    parser.add_argument('--status', action='store_true', help='查看当前进度')
    parser.add_argument('--report', action='store_true', help='生成最终报告')
    parser.add_argument('--max-sessions', type=int, default=None, help='覆盖最大会话数')
    return parser.parse_args()


def find_config(explicit_path=None):
    """查找配置文件"""
    if explicit_path:
        return explicit_path
    candidates = [
        'config.json',
        os.path.join(os.path.dirname(os.path.abspath(__file__)), 'config.json'),
    ]
    for c in candidates:
        if os.path.exists(c):
            return c
    print("错误: 找不到 config.json，请用 --config 指定", file=sys.stderr)
    sys.exit(1)


def format_report(report):
    """格式化最终报告"""
    lines = [
        '',
        '=' * 60,
        '  最终报告',
        '=' * 60,
        f"  总任务数:  {report['total']}",
        f"  已完成:    {report['passed']}",
        f"  已跳过:    {report['skipped']}",
        f"  待处理:    {report['pending']}",
        '',
    ]
    if report['passed'] > 0:
        lines.append('  已完成的任务:')
        for t in report['tasks']:
            if t['status'] == 'passed':
                # 计算任务耗时
                duration_str = '未知'
                if t.get('started_at') and t.get('completed_at'):
                    from datetime import datetime
                    try:
                        start = datetime.fromisoformat(t['started_at'])
                        end = datetime.fromisoformat(t['completed_at'])
                        duration = (end - start).total_seconds()
                        duration_str = f"{duration:.2f}秒"
                    except (ValueError, TypeError):
                        pass

                # 获取评估结果
                score = t.get('evaluation', {}).get('score', 0) if t.get('evaluation') else 0
                passed_str = '通过' if t['status'] == 'passed' else '未通过'

                lines.append(f"    + [{t['id']}] {t['title']}")
                lines.append(f"      状态: {passed_str} | 得分: {score} | 耗时: {duration_str}")
    if report['skipped'] > 0:
        lines.append('  已跳过的任务 (失败超过重试上限):')
        for t in report['tasks']:
            if t['status'] == 'skipped':
                lines.append(f"    x [{t['id']}] {t['title']} (尝试 {t['attempts']} 次)")
    if report['pending'] > 0:
        lines.append('  未完成的任务:')
        for t in report['tasks']:
            if t['status'] in ('pending', 'failed'):
                lines.append(f"    - [{t['id']}] {t['title']}")
    lines.append('=' * 60)
    return '\n'.join(lines)


def main():
    args = parse_args()
    config_path = find_config(args.config)
    config = Config.load(config_path)

    if args.max_sessions:
        config.max_sessions = args.max_sessions

    task_mgr = TaskManager(config.task_list_path, config.max_attempts)

    # --status
    if args.status:
        if not task_mgr.has_existing_tasks():
            print("尚未开始任何任务。")
            return
        task_mgr.load()
        print(task_mgr.summary())
        return

    # --report
    if args.report:
        if not task_mgr.has_existing_tasks():
            print("尚未开始任何任务。")
            return
        task_mgr.load()
        print(format_report(task_mgr.generate_report()))
        return

    # 需要任务描述或恢复
    if not args.resume and not args.task and not config.user_task:
        print("错误: 请用 --task 指定任务，或在 config.json 中设置 user_task", file=sys.stderr)
        sys.exit(1)

    user_task = args.task or config.user_task
    logger = ExecutionLogger(config.execution_log_path)
    runner = AgentRunner(config, logger)
    budget = BudgetTracker(config.budget_max_dollars)

    # ---- 阶段一: 规划 ----
    if args.resume and task_mgr.has_existing_tasks():
        task_mgr.load()
        logger.event('session_resume', {
            'pending': task_mgr.pending_count(),
            'passed': task_mgr.passed_count(),
        })
        print(f"\n从断点恢复。\n{task_mgr.summary()}\n")
    else:
        logger.event('planner_start', {'task': user_task})
        print(f"\n启动规划器智能体...\n任务: {user_task}\n")
        runner.run_planner(user_task)

        if not task_mgr.has_existing_tasks():
            logger.error("规划器未生成 task_list.json")
            print("错误: 规划器未生成 task_list.json", file=sys.stderr)
            sys.exit(1)

        task_mgr.load()
        logger.event('planner_done', {'tasks_count': task_mgr.total_count()})
        print(f"\n规划完成。共 {task_mgr.total_count()} 个任务。\n")

    # ---- 阶段二: 执行循环 ----
    session = 0
    try:
        while True:
            task = task_mgr.next_task()
            if task is None:
                logger.event('all_done', {
                    'passed': task_mgr.passed_count(),
                    'skipped': task_mgr.skipped_count(),
                })
                print("\n没有更多可执行的任务。")
                break

            if session >= config.max_sessions:
                logger.warn(f"达到最大会话数 {config.max_sessions}")
                break

            session += 1
            attempt = task.get('attempts', 0) + 1
            task_id = task['id']
            task_title = task.get('title', task.get('description', ''))

            print(f"\n{'='*60}")
            print(f"  会话 #{session} | 任务 {task_id}: {task_title}")
            print(f"  尝试: {attempt}/{task.get('max_attempts', config.max_attempts)}")
            print(f"  剩余: {task_mgr.pending_count()} 个任务")
            print(f"{'='*60}\n")

            # 记录任务开始时间
            from datetime import datetime
            task_start_time = datetime.now()
            start_time_str = task_start_time.strftime('%Y-%m-%d %H:%M:%S')
            print(f"  开始时间: {start_time_str}")

            # Worker 执行
            logger.event('worker_start', {
                'task_id': task_id, 'session': session, 'attempt': attempt
            })
            task_mgr.update_status(task_id, 'in_progress')
            task_mgr.save()

            worker_result = runner.run_worker(task, session)
            budget.record(worker_result.get('usage', {}), config.worker_model)
            logger.event('worker_done', {
                'task_id': task_id,
                'success': worker_result.get('success', False),
            })
            print(f"  [{budget.summary()}]")

            # Worker 本身失败（超时等）
            if not worker_result.get('success', False):
                logger.event('worker_failed', {
                    'task_id': task_id,
                    'error': worker_result.get('error', 'unknown'),
                })
                task_mgr.mark_failed(task_id, {
                    'passed': False,
                    'score': 0,
                    'feedback': f"Worker 执行失败: {worker_result.get('error', 'unknown')}",
                    'checks': [],
                })
                task_mgr.save()

                # 计算任务耗时
                task_end_time = datetime.now()
                end_time_str = task_end_time.strftime('%Y-%m-%d %H:%M:%S')
                duration = (task_end_time - task_start_time).total_seconds()
                print(f"  结束时间: {end_time_str}")
                print(f"  任务耗时: {duration:.2f}秒")

                time.sleep(config.cooldown_seconds)
                continue

            # Evaluator 验证
            logger.event('eval_start', {'task_id': task_id})
            task_mgr.update_status(task_id, 'evaluating')
            task_mgr.save()

            eval_result = runner.run_evaluator(task)
            # evaluator 返回的是解析后的 JSON，usage 可能在原始 result 中丢失
            # 这里做一个安全的记录
            if isinstance(eval_result, dict) and 'usage' in eval_result:
                budget.record(eval_result.get('usage', {}), config.evaluator_model)
            passed = eval_result.get('passed', False)
            score = eval_result.get('score', 0)

            logger.event('eval_done', {
                'task_id': task_id,
                'passed': passed,
                'score': score,
            })

            # 计算任务耗时
            task_end_time = datetime.now()
            end_time_str = task_end_time.strftime('%Y-%m-%d %H:%M:%S')
            duration = (task_end_time - task_start_time).total_seconds()

            print(f"  结束时间: {end_time_str}")
            print(f"  任务耗时: {duration:.2f}秒")

            if passed:
                task_mgr.mark_passed(task_id, eval_result)
                print(f"  + 任务 {task_id} 通过 (得分: {score})")
            else:
                task_mgr.mark_failed(task_id, eval_result)
                feedback = eval_result.get('feedback', '')
                remaining = task.get('max_attempts', config.max_attempts) - task['attempts']
                if remaining > 0:
                    print(f"  x 任务 {task_id} 未通过 (得分: {score})，剩余重试: {remaining}")
                    print(f"    原因: {feedback[:200]}")
                else:
                    print(f"  x 任务 {task_id} 已跳过 (超过最大重试次数)")

            task_mgr.save()

            # 预算熔断检查
            if budget.is_over_budget():
                logger.warn(f"预算超限！{budget.summary()}")
                print(f"\n  预算超限，自动停止。{budget.summary()}")
                break

            time.sleep(config.cooldown_seconds)

    except KeyboardInterrupt:
        logger.event('interrupted', {
            'session': session,
            'pending': task_mgr.pending_count(),
        })
        task_mgr.save()
        print(f"\n\n中断。已保存进度。使用 --resume 恢复执行。\n")
        print(task_mgr.summary())
        return

    # ---- 阶段三: 报告 ----
    report = task_mgr.generate_report()
    logger.event('session_end', {**report, 'budget': budget.summary()})
    print(format_report(report))
    print(f"\n  费用统计: {budget.summary()}")


if __name__ == '__main__':
    main()
