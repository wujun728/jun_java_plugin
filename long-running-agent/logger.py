#!/usr/bin/env python3
"""结构化日志 — JSONL 文件 + 控制台输出"""
import json
import os
from datetime import datetime


class ExecutionLogger:
    """双输出日志：JSONL 文件（机器可读）+ 控制台（人可读）"""

    def __init__(self, log_path: str):
        self.log_path = log_path
        log_dir = os.path.dirname(log_path)
        if log_dir:
            os.makedirs(log_dir, exist_ok=True)

    def event(self, event_type: str, data: dict = None):
        """记录一个事件"""
        entry = {
            'ts': datetime.now().isoformat(timespec='seconds'),
            'event': event_type,
            'data': data or {}
        }
        # 写入 JSONL
        with open(self.log_path, 'a', encoding='utf-8') as f:
            f.write(json.dumps(entry, ensure_ascii=False) + '\n')
        # 控制台
        ts = entry['ts'][:19]
        data_str = json.dumps(data, ensure_ascii=False) if data else ''
        print(f"[{ts}] {event_type} {data_str}")

    def info(self, msg: str):
        self.event('info', {'message': msg})

    def warn(self, msg: str):
        self.event('warn', {'message': msg})

    def error(self, msg: str):
        self.event('error', {'message': msg})
