#!/usr/bin/env python3
"""配置加载与验证"""
import json
import os
import sys


class Config:
    """框架配置，从 config.json 加载"""

    def __init__(self, data: dict, config_dir: str):
        self.project_name = data.get('project_name', 'my-project')
        self.project_type = data.get('project_type', 'java-springboot')
        self.project_description = data.get('project_description', '')
        self.user_task = data.get('user_task', '')

        # 工作目录
        work_dir = data.get('work_directory', '.')
        if work_dir == '.':
            self.work_directory = os.getcwd()
        else:
            self.work_directory = os.path.abspath(work_dir)

        # 智能体参数
        agent = data.get('agent', {})
        self.model_provider = agent.get('model_provider', 'claude')  # 模型提供者: claude, qodercli
        self.model = agent.get('model', 'sonnet')
        # 分层模型：planner/worker/evaluator 可各自指定模型，为空则使用默认 model
        self.planner_model = agent.get('planner_model', '') or self.model
        self.worker_model = agent.get('worker_model', '') or self.model
        self.evaluator_model = agent.get('evaluator_model', '') or self.model
        self.planner_max_turns = agent.get('planner_max_turns', 50)
        self.worker_max_turns = agent.get('worker_max_turns', 30)
        self.evaluator_max_turns = agent.get('evaluator_max_turns', 15)
        self.max_sessions = agent.get('max_sessions', 20)
        self.cooldown_seconds = agent.get('cooldown_seconds', 5)
        self.task_timeout_seconds = agent.get('task_timeout_seconds', 600)
        # 预算控制
        self.budget_max_dollars = agent.get('budget_max_dollars', 10.0)

        # 模型提供者特定配置
        self.qoder_config = agent.get('qoder_config', {})
        self.deepseek_config = agent.get('deepseek_config', {})
        self.glm_config = agent.get('glm_config', {})
        self.doubao_config = agent.get('doubao_config', {})

        # 重试
        retry = data.get('retry', {})
        self.max_attempts = retry.get('max_attempts', 3)

        # Git
        git = data.get('git', {})
        self.git_auto_commit = git.get('auto_commit', True)

        # 路径
        paths = data.get('paths', {})
        self.task_list_file = paths.get('task_list', 'task_list.json')
        self.progress_file = paths.get('progress_file', 'claude-progress.txt')
        self.execution_log_file = paths.get('execution_log', 'execution_log.jsonl')
        self.log_dir = paths.get('log_dir', 'logs')

        # 构建命令
        build = data.get('build', {})
        self.compile_command = build.get('compile_command', '')
        self.test_command = build.get('test_command', '')
        self.run_command = build.get('run_command', '')

        # 框架自身目录（prompts/ 所在位置）
        self.framework_dir = config_dir

    @classmethod
    def load(cls, config_path: str) -> 'Config':
        config_path = os.path.abspath(config_path)
        if not os.path.exists(config_path):
            print(f"错误: 配置文件不存在: {config_path}", file=sys.stderr)
            sys.exit(1)
        with open(config_path, 'r', encoding='utf-8') as f:
            data = json.load(f)
        config_dir = os.path.dirname(config_path)
        return cls(data, config_dir)

    @property
    def task_list_path(self) -> str:
        return os.path.join(self.work_directory, self.task_list_file)

    @property
    def progress_path(self) -> str:
        return os.path.join(self.work_directory, self.progress_file)

    @property
    def execution_log_path(self) -> str:
        return os.path.join(self.work_directory, self.execution_log_file)

    @property
    def log_dir_path(self) -> str:
        return os.path.join(self.work_directory, self.log_dir)
