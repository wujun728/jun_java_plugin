#!/usr/bin/env python3
"""任务生命周期管理 — 状态机 + 持久化"""
import json
import os
from datetime import datetime


class TaskManager:
    """
    管理 task_list.json 的读写和任务状态流转。
    状态: pending -> in_progress -> evaluating -> passed / failed -> skipped
    """

    def __init__(self, task_list_path: str, max_attempts: int = 3):
        self.path = task_list_path
        self.max_attempts = max_attempts
        self.meta = {}
        self.tasks = []

    def has_existing_tasks(self) -> bool:
        return os.path.exists(self.path)

    def load(self):
        with open(self.path, 'r', encoding='utf-8') as f:
            data = json.load(f)
        # 兼容两种格式: 纯数组（v1）或 {meta, tasks}（v2）
        if isinstance(data, list):
            self.meta = {}
            self.tasks = data
            self._migrate_v1()
        else:
            self.meta = data.get('meta', {})
            self.tasks = data.get('tasks', [])
            # 确保所有字段存在
            for t in self.tasks:
                self._ensure_fields(t)

    def _migrate_v1(self):
        """将 v1 格式（passes/steps）迁移为 v2 格式（status/verification_steps）"""
        for t in self.tasks:
            if 'passes' in t and 'status' not in t:
                t['status'] = 'passed' if t['passes'] else 'pending'
            if 'steps' in t and 'verification_steps' not in t:
                t['verification_steps'] = t['steps']
            if 'description' in t and 'title' not in t:
                t['title'] = t['description']
            self._ensure_fields(t)

    def _ensure_fields(self, t: dict):
        """确保任务包含所有必需字段"""
        t.setdefault('status', 'pending')
        t.setdefault('attempts', 0)
        t.setdefault('max_attempts', self.max_attempts)
        t.setdefault('result', None)
        t.setdefault('evaluation', None)
        t.setdefault('error_history', [])
        t.setdefault('started_at', None)
        t.setdefault('completed_at', None)
        t.setdefault('verification_steps', [])
        t.setdefault('dependencies', [])
        t.setdefault('priority', 99)
        t.setdefault('category', 'other')
        t.setdefault('title', t.get('description', ''))

    def save(self):
        data = {'meta': self.meta, 'tasks': self.tasks}
        with open(self.path, 'w', encoding='utf-8') as f:
            json.dump(data, f, ensure_ascii=False, indent=2)

    def next_task(self):
        """返回下一个可执行的任务，或 None"""
        candidates = []
        for t in self.tasks:
            if t['status'] in ('passed', 'skipped'):
                continue
            if t['status'] == 'failed' and t['attempts'] >= t.get('max_attempts', self.max_attempts):
                t['status'] = 'skipped'
                continue
            if not self._dependencies_met(t):
                # 如果依赖被 skipped，则此任务也 skip
                if self._has_skipped_dependency(t):
                    t['status'] = 'skipped'
                continue
            candidates.append(t)
        if not candidates:
            return None
        candidates.sort(key=lambda x: (x.get('priority', 99), x.get('id', '')))
        return candidates[0]

    def _dependencies_met(self, task) -> bool:
        deps = task.get('dependencies', [])
        if not deps:
            return True
        status_map = {t['id']: t['status'] for t in self.tasks}
        for dep_id in deps:
            if status_map.get(dep_id) != 'passed':
                return False
        return True

    def _has_skipped_dependency(self, task) -> bool:
        deps = task.get('dependencies', [])
        status_map = {t['id']: t['status'] for t in self.tasks}
        for dep_id in deps:
            if status_map.get(dep_id) == 'skipped':
                return True
        return False

    def update_status(self, task_id: str, status: str):
        task = self._find(task_id)
        task['status'] = status
        if status == 'in_progress':
            task['started_at'] = datetime.now().isoformat(timespec='seconds')
            task['attempts'] = task.get('attempts', 0) + 1

    def mark_passed(self, task_id: str, eval_result: dict):
        task = self._find(task_id)
        task['status'] = 'passed'
        task['evaluation'] = eval_result
        task['completed_at'] = datetime.now().isoformat(timespec='seconds')

    def mark_failed(self, task_id: str, eval_result: dict):
        task = self._find(task_id)
        if task['attempts'] >= task.get('max_attempts', self.max_attempts):
            task['status'] = 'skipped'
        else:
            task['status'] = 'failed'
        task['evaluation'] = eval_result
        task.setdefault('error_history', [])
        task['error_history'].append({
            'attempt': task['attempts'],
            'feedback': eval_result.get('feedback', ''),
            'ts': datetime.now().isoformat(timespec='seconds')
        })

    def _find(self, task_id: str) -> dict:
        for t in self.tasks:
            if t['id'] == task_id:
                return t
        raise ValueError(f"任务 {task_id} 不存在")

    # ---- 统计 ----

    def total_count(self) -> int:
        return len(self.tasks)

    def pending_count(self) -> int:
        return len([t for t in self.tasks if t['status'] in ('pending', 'failed')])

    def passed_count(self) -> int:
        return len([t for t in self.tasks if t['status'] == 'passed'])

    def skipped_count(self) -> int:
        return len([t for t in self.tasks if t['status'] == 'skipped'])

    def summary(self) -> str:
        total = self.total_count()
        passed = self.passed_count()
        skipped = self.skipped_count()
        pending = self.pending_count()
        pct = (passed / total * 100) if total else 0

        lines = [
            f"进度: {passed}/{total} ({pct:.1f}%)",
            f"  已完成: {passed}  失败/跳过: {skipped}  待处理: {pending}",
        ]

        # 按类别
        cats = {}
        for t in self.tasks:
            c = t.get('category', 'other')
            cats.setdefault(c, [0, 0])
            cats[c][0] += 1
            if t['status'] == 'passed':
                cats[c][1] += 1
        for c, (tot, done) in sorted(cats.items()):
            bar_len = int(done / tot * 20) if tot else 0
            bar = '#' * bar_len + '.' * (20 - bar_len)
            lines.append(f"  {c:<18} {done}/{tot:<4} [{bar}]")

        # 添加已完成任务的详细信息
        if passed > 0:
            lines.append('')
            lines.append('  已完成任务详情:')
            for t in self.tasks:
                if t['status'] == 'passed':
                    # 计算任务耗时
                    duration_str = '未知'
                    if t.get('started_at') and t.get('completed_at'):
                        try:
                            start = datetime.fromisoformat(t['started_at'])
                            end = datetime.fromisoformat(t['completed_at'])
                            duration = (end - start).total_seconds()
                            duration_str = f"{duration:.2f}秒"
                        except (ValueError, TypeError):
                            pass

                    # 获取评估结果
                    score = t.get('evaluation', {}).get('score', 0) if t.get('evaluation') else 0
                    passed_str = '✓'

                    title = t.get('title', t.get('description', ''))[:50]
                    lines.append(f"    {passed_str} [{t['id']}] {title}")
                    lines.append(f"      得分: {score}/100 | 耗时: {duration_str}")

        return '\n'.join(lines)

    def generate_report(self) -> dict:
        return {
            'total': self.total_count(),
            'passed': self.passed_count(),
            'skipped': self.skipped_count(),
            'pending': self.pending_count(),
            'tasks': [
                {
                    'id': t['id'],
                    'title': t.get('title', t.get('description', '')),
                    'status': t['status'],
                    'attempts': t.get('attempts', 0),
                    'started_at': t.get('started_at'),
                    'completed_at': t.get('completed_at'),
                    'evaluation': t.get('evaluation'),
                }
                for t in self.tasks
            ]
        }
