#!/usr/bin/env python3
"""AI 模型调用封装 — 驱动 Planner/Worker/Evaluator"""
import json
import os
import re

from config import Config
from prompt_renderer import render_prompt, build_variables
from logger import ExecutionLogger
from model_provider import create_model_provider


class AgentRunner:
    """封装对 AI 模型的调用（支持 Claude、Qoder 等）"""

    def __init__(self, config: Config, logger: ExecutionLogger):
        self.config = config
        self.logger = logger
        self.prompts_dir = os.path.join(config.framework_dir, 'prompts')
        os.makedirs(config.log_dir_path, exist_ok=True)

        # 创建模型提供者
        self.model_provider = create_model_provider(config, logger)

    # ---- Planner ----

    def run_planner(self, user_task: str):
        """运行规划器智能体，生成 task_list.json"""
        variables = build_variables(self.config, {'USER_TASK': user_task})
        prompt = render_prompt(
            os.path.join(self.prompts_dir, 'planner.md'), variables
        )
        log_file = os.path.join(self.config.log_dir_path, 'planner.log')
        self._run_model(prompt, self.config.planner_max_turns, log_file, model_override=self.config.planner_model)

    # ---- Worker ----

    def run_worker(self, task: dict, session: int) -> dict:
        """运行执行器智能体，实现一个任务"""
        error_context = ''
        if task.get('error_history'):
            last_err = task['error_history'][-1]
            error_context = (
                f"\n\n## 上次失败信息（第 {last_err['attempt']} 次尝试）\n"
                f"{last_err['feedback']}\n"
                f"请根据以上反馈修复问题。\n"
            )

        variables = build_variables(self.config, {
            'SESSION_NUMBER': str(session),
            'TASK_ID': task['id'],
            'TASK_TITLE': task.get('title', task.get('description', '')),
            'TASK_DESCRIPTION': task.get('description', ''),
            'VERIFICATION_STEPS': json.dumps(
                task.get('verification_steps', []), ensure_ascii=False
            ),
            'ERROR_CONTEXT': error_context,
        })
        prompt = render_prompt(
            os.path.join(self.prompts_dir, 'worker.md'), variables
        )
        log_file = os.path.join(
            self.config.log_dir_path,
            f"worker-{task['id']}-s{session}.log"
        )
        return self._run_model(prompt, self.config.worker_max_turns, log_file, model_override=self.config.worker_model)

    # ---- Evaluator ----

    def run_evaluator(self, task: dict) -> dict:
        """运行评估器智能体，验证任务完成质量"""
        variables = build_variables(self.config, {
            'TASK_ID': task['id'],
            'TASK_TITLE': task.get('title', task.get('description', '')),
            'TASK_DESCRIPTION': task.get('description', ''),
            'VERIFICATION_STEPS': json.dumps(
                task.get('verification_steps', []), ensure_ascii=False
            ),
        })
        prompt = render_prompt(
            os.path.join(self.prompts_dir, 'evaluator.md'), variables
        )
        log_file = os.path.join(
            self.config.log_dir_path, f"eval-{task['id']}.log"
        )
        result = self._run_model(prompt, self.config.evaluator_max_turns, log_file, model_override=self.config.evaluator_model)
        return self._parse_evaluation(result, log_file)

    def _parse_evaluation(self, result: dict, log_file: str) -> dict:
        """从 Evaluator 输出中解析评估结果 JSON"""
        # 尝试从输出和日志文件中提取 JSON
        for source in [result.get('output', ''), self._read_file(log_file)]:
            parsed = self._extract_eval_json(source)
            if parsed is not None:
                return parsed

        # 兜底：无法解析则视为失败
        self.logger.warn("无法解析 Evaluator 输出，默认为失败")
        return {
            'passed': False,
            'score': 0,
            'feedback': '无法解析评估结果，请检查 Evaluator 输出日志',
            'checks': []
        }

    def _extract_eval_json(self, text: str) -> dict:
        """从文本中提取包含 "passed" 字段的 JSON 对象"""
        if not text:
            return None
        match = re.search(r'\{[^{}]*"passed"\s*:', text, re.DOTALL)
        if not match:
            return None
        start = match.start()
        brace_count = 0
        for i in range(start, len(text)):
            if text[i] == '{':
                brace_count += 1
            elif text[i] == '}':
                brace_count -= 1
                if brace_count == 0:
                    try:
                        return json.loads(text[start:i + 1])
                    except json.JSONDecodeError:
                        return None
        return None

    def _read_file(self, path: str) -> str:
        if os.path.exists(path):
            with open(path, 'r', encoding='utf-8') as f:
                return f.read()
        return ''

    # ---- 底层调用 ----

    def _run_model(self, prompt: str, max_turns: int, log_file: str, model_override: str = None) -> dict:
        """调用 AI 模型并返回结果"""
        return self.model_provider.invoke(prompt, max_turns, log_file, model_override=model_override)
