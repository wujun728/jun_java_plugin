#!/usr/bin/env python3
"""模型提供者抽象接口 — 支持多种 AI 模型"""
import os
import json
import subprocess
from abc import ABC, abstractmethod
from typing import Dict

from logger import ExecutionLogger


class ModelProvider(ABC):
    """AI 模型提供者的抽象基类"""

    def __init__(self, config, logger: ExecutionLogger):
        self.config = config
        self.logger = logger
        self.work_directory = config.work_directory
        self.task_timeout_seconds = config.task_timeout_seconds

    @abstractmethod
    def invoke(self, prompt: str, max_turns: int, log_file: str, model_override: str = None) -> dict:
        """
        调用 AI 模型

        Args:
            prompt: 输入提示词
            max_turns: 最大对话轮数
            log_file: 日志文件路径

        Returns:
            dict: {
                'success': bool,
                'output': str,
                'exit_code': int,
                'error': str (可选)
            }
        """
        pass

    @abstractmethod
    def get_provider_name(self) -> str:
        """返回提供者名称"""
        pass


class ClaudeProvider(ModelProvider):
    """Claude Code CLI 提供者"""

    def __init__(self, config, logger: ExecutionLogger):
        super().__init__(config, logger)
        self.model = config.model

    def get_provider_name(self) -> str:
        return "claude"

    def invoke(self, prompt: str, max_turns: int, log_file: str, model_override: str = None) -> dict:
        """调用 claude CLI 并返回结果"""
        use_model = model_override or self.model
        cmd = [
            'claude', '-p',
            '--model', use_model,
            '--output-format', 'json',
            '--dangerously-skip-permissions',
        ]
        try:
            proc = subprocess.run(
                cmd,
                input=prompt,
                capture_output=True,
                text=True,
                encoding='utf-8',
                errors='replace',
                timeout=self.task_timeout_seconds,
                cwd=self.work_directory,
                shell=(os.name == 'nt'),  # Windows 需要 shell=True 来找到 .cmd
            )
            raw_output = proc.stdout or ''

            # 保存完整输出
            with open(log_file, 'w', encoding='utf-8') as f:
                f.write(raw_output)
                if proc.stderr:
                    f.write('\n--- STDERR ---\n')
                    f.write(proc.stderr)

            # 尝试从 JSON 输出中提取 result 字段和 usage 信息
            output_text = raw_output
            usage = {}
            try:
                json_resp = json.loads(raw_output)
                output_text = json_resp.get('result', raw_output)
                # Claude CLI JSON 输出包含 usage 字段
                if 'usage' in json_resp:
                    usage = json_resp['usage']
                # 也可能在顶层
                if 'input_tokens' in json_resp:
                    usage = {
                        'input_tokens': json_resp.get('input_tokens', 0),
                        'output_tokens': json_resp.get('output_tokens', 0),
                    }
            except (json.JSONDecodeError, TypeError):
                pass

            return {
                'success': proc.returncode == 0,
                'output': output_text,
                'exit_code': proc.returncode,
                'usage': usage,
            }
        except subprocess.TimeoutExpired:
            self.logger.error(f"Claude CLI 超时 ({self.task_timeout_seconds}s)")
            return {'success': False, 'output': '', 'error': 'timeout'}
        except FileNotFoundError:
            self.logger.error("claude 命令未找到，请确认 Claude Code CLI 已安装")
            return {'success': False, 'output': '', 'error': 'claude_not_found'}
        except Exception as e:
            self.logger.error(f"调用 Claude CLI 异常: {e}")
            return {'success': False, 'output': '', 'error': str(e)}


class QoderCliProvider(ModelProvider):
    """Qoder CLI 提供者"""

    def __init__(self, config, logger: ExecutionLogger):
        super().__init__(config, logger)
        self.model = config.model
        # qoder 特定配置
        self.qoder_config = config.qoder_config if hasattr(config, 'qoder_config') else {}

    def get_provider_name(self) -> str:
        return "qodercli"

    def invoke(self, prompt: str, max_turns: int, log_file: str) -> dict:
        """调用 qoder CLI 并返回结果"""
        # 构建 qoder 命令
        cmd = ['qoder']

        # 添加模型参数
        if self.model:
            cmd.extend(['--model', self.model])

        # 添加其他配置参数
        if self.qoder_config.get('api_key'):
            cmd.extend(['--api-key', self.qoder_config['api_key']])

        if self.qoder_config.get('base_url'):
            cmd.extend(['--base-url', self.qoder_config['base_url']])

        if self.qoder_config.get('temperature'):
            cmd.extend(['--temperature', str(self.qoder_config['temperature'])])

        # 添加输出格式
        cmd.extend(['--output-format', 'json'])

        # 添加最大轮数
        cmd.extend(['--max-turns', str(max_turns)])

        try:
            proc = subprocess.run(
                cmd,
                input=prompt,
                capture_output=True,
                text=True,
                encoding='utf-8',
                errors='replace',
                timeout=self.task_timeout_seconds,
                cwd=self.work_directory,
                shell=(os.name == 'nt'),  # Windows 需要 shell=True
            )
            raw_output = proc.stdout or ''

            # 保存完整输出
            with open(log_file, 'w', encoding='utf-8') as f:
                f.write(raw_output)
                if proc.stderr:
                    f.write('\n--- STDERR ---\n')
                    f.write(proc.stderr)

            # 尝试从 JSON 输出中提取结果
            output_text = raw_output
            try:
                json_resp = json.loads(raw_output)
                # qoder 可能使用不同的字段名
                output_text = (
                    json_resp.get('result') or
                    json_resp.get('output') or
                    json_resp.get('response') or
                    raw_output
                )
            except (json.JSONDecodeError, TypeError):
                pass

            return {
                'success': proc.returncode == 0,
                'output': output_text,
                'exit_code': proc.returncode,
            }
        except subprocess.TimeoutExpired:
            self.logger.error(f"Qoder CLI 超时 ({self.task_timeout_seconds}s)")
            return {'success': False, 'output': '', 'error': 'timeout'}
        except FileNotFoundError:
            self.logger.error("qoder 命令未找到，请确认 Qoder CLI 已安装")
            return {'success': False, 'output': '', 'error': 'qoder_not_found'}
        except Exception as e:
            self.logger.error(f"调用 Qoder CLI 异常: {e}")
            return {'success': False, 'output': '', 'error': str(e)}


class DeepSeekProvider(ModelProvider):
    """DeepSeek API 提供者"""

    def __init__(self, config, logger: ExecutionLogger):
        super().__init__(config, logger)
        self.model = config.model
        self.deepseek_config = config.deepseek_config if hasattr(config, 'deepseek_config') else {}

    def get_provider_name(self) -> str:
        return "deepseek"

    def invoke(self, prompt: str, max_turns: int, log_file: str) -> dict:
        """调用 DeepSeek API 并返回结果"""
        try:
            import requests
        except ImportError:
            self.logger.error("requests 库未安装，请运行: pip install requests")
            return {'success': False, 'output': '', 'error': 'requests_not_installed'}

        api_key = self.deepseek_config.get('api_key')
        if not api_key:
            self.logger.error("DeepSeek API Key 未配置")
            return {'success': False, 'output': '', 'error': 'api_key_missing'}

        base_url = self.deepseek_config.get('base_url', 'https://api.deepseek.com/v1')
        temperature = self.deepseek_config.get('temperature', 0.7)
        max_tokens = self.deepseek_config.get('max_tokens', 8000)

        try:
            response = requests.post(
                f"{base_url}/chat/completions",
                headers={
                    'Authorization': f'Bearer {api_key}',
                    'Content-Type': 'application/json',
                },
                json={
                    'model': self.model,
                    'messages': [{'role': 'user', 'content': prompt}],
                    'temperature': temperature,
                    'max_tokens': max_tokens,
                },
                timeout=self.task_timeout_seconds,
            )

            raw_output = response.text
            # 保存完整输出
            with open(log_file, 'w', encoding='utf-8') as f:
                f.write(raw_output)

            if response.status_code == 200:
                json_resp = response.json()
                output_text = json_resp.get('choices', [{}])[0].get('message', {}).get('content', '')
                return {
                    'success': True,
                    'output': output_text,
                    'exit_code': 0,
                }
            else:
                self.logger.error(f"DeepSeek API 调用失败: {response.status_code}")
                return {
                    'success': False,
                    'output': '',
                    'error': f'api_error_{response.status_code}',
                    'exit_code': response.status_code,
                }
        except requests.exceptions.Timeout:
            self.logger.error(f"DeepSeek API 超时 ({self.task_timeout_seconds}s)")
            return {'success': False, 'output': '', 'error': 'timeout'}
        except Exception as e:
            self.logger.error(f"调用 DeepSeek API 异常: {e}")
            return {'success': False, 'output': '', 'error': str(e)}


class GLMProvider(ModelProvider):
    """智谱 GLM API 提供者"""

    def __init__(self, config, logger: ExecutionLogger):
        super().__init__(config, logger)
        self.model = config.model
        self.glm_config = config.glm_config if hasattr(config, 'glm_config') else {}

    def get_provider_name(self) -> str:
        return "glm"

    def invoke(self, prompt: str, max_turns: int, log_file: str) -> dict:
        """调用智谱 GLM API 并返回结果"""
        try:
            import requests
        except ImportError:
            self.logger.error("requests 库未安装，请运行: pip install requests")
            return {'success': False, 'output': '', 'error': 'requests_not_installed'}

        api_key = self.glm_config.get('api_key')
        if not api_key:
            self.logger.error("GLM API Key 未配置")
            return {'success': False, 'output': '', 'error': 'api_key_missing'}

        base_url = self.glm_config.get('base_url', 'https://open.bigmodel.cn/api/paas/v4')
        temperature = self.glm_config.get('temperature', 0.7)
        max_tokens = self.glm_config.get('max_tokens', 8000)

        try:
            response = requests.post(
                f"{base_url}/chat/completions",
                headers={
                    'Authorization': f'Bearer {api_key}',
                    'Content-Type': 'application/json',
                },
                json={
                    'model': self.model,
                    'messages': [{'role': 'user', 'content': prompt}],
                    'temperature': temperature,
                    'max_tokens': max_tokens,
                },
                timeout=self.task_timeout_seconds,
            )

            raw_output = response.text
            # 保存完整输出
            with open(log_file, 'w', encoding='utf-8') as f:
                f.write(raw_output)

            if response.status_code == 200:
                json_resp = response.json()
                output_text = json_resp.get('choices', [{}])[0].get('message', {}).get('content', '')
                return {
                    'success': True,
                    'output': output_text,
                    'exit_code': 0,
                }
            else:
                self.logger.error(f"GLM API 调用失败: {response.status_code}")
                return {
                    'success': False,
                    'output': '',
                    'error': f'api_error_{response.status_code}',
                    'exit_code': response.status_code,
                }
        except requests.exceptions.Timeout:
            self.logger.error(f"GLM API 超时 ({self.task_timeout_seconds}s)")
            return {'success': False, 'output': '', 'error': 'timeout'}
        except Exception as e:
            self.logger.error(f"调用 GLM API 异常: {e}")
            return {'success': False, 'output': '', 'error': str(e)}


class DoubaoProvider(ModelProvider):
    """豆包（火山引擎）API 提供者"""

    def __init__(self, config, logger: ExecutionLogger):
        super().__init__(config, logger)
        self.model = config.model
        self.doubao_config = config.doubao_config if hasattr(config, 'doubao_config') else {}

    def get_provider_name(self) -> str:
        return "doubao"

    def invoke(self, prompt: str, max_turns: int, log_file: str) -> dict:
        """调用豆包 API 并返回结果"""
        try:
            import requests
        except ImportError:
            self.logger.error("requests 库未安装，请运行: pip install requests")
            return {'success': False, 'output': '', 'error': 'requests_not_installed'}

        api_key = self.doubao_config.get('api_key')
        if not api_key:
            self.logger.error("豆包 API Key 未配置")
            return {'success': False, 'output': '', 'error': 'api_key_missing'}

        # 豆包使用火山引擎的 API
        base_url = self.doubao_config.get('base_url', 'https://ark.cn-beijing.volces.com/api/v3')
        temperature = self.doubao_config.get('temperature', 0.7)
        max_tokens = self.doubao_config.get('max_tokens', 8000)

        try:
            response = requests.post(
                f"{base_url}/chat/completions",
                headers={
                    'Authorization': f'Bearer {api_key}',
                    'Content-Type': 'application/json',
                },
                json={
                    'model': self.model,
                    'messages': [{'role': 'user', 'content': prompt}],
                    'temperature': temperature,
                    'max_tokens': max_tokens,
                },
                timeout=self.task_timeout_seconds,
            )

            raw_output = response.text
            # 保存完整输出
            with open(log_file, 'w', encoding='utf-8') as f:
                f.write(raw_output)

            if response.status_code == 200:
                json_resp = response.json()
                output_text = json_resp.get('choices', [{}])[0].get('message', {}).get('content', '')
                return {
                    'success': True,
                    'output': output_text,
                    'exit_code': 0,
                }
            else:
                self.logger.error(f"豆包 API 调用失败: {response.status_code}")
                return {
                    'success': False,
                    'output': '',
                    'error': f'api_error_{response.status_code}',
                    'exit_code': response.status_code,
                }
        except requests.exceptions.Timeout:
            self.logger.error(f"豆包 API 超时 ({self.task_timeout_seconds}s)")
            return {'success': False, 'output': '', 'error': 'timeout'}
        except Exception as e:
            self.logger.error(f"调用豆包 API 异常: {e}")
            return {'success': False, 'output': '', 'error': str(e)}


def create_model_provider(config, logger: ExecutionLogger) -> ModelProvider:
    """
    工厂函数：根据配置创建模型提供者

    Args:
        config: 配置对象
        logger: 日志记录器

    Returns:
        ModelProvider: 模型提供者实例
    """
    provider_type = config.model_provider.lower() if hasattr(config, 'model_provider') else 'claude'

    providers = {
        'claude': ClaudeProvider,
        'qodercli': QoderCliProvider,
        'qoder': QoderCliProvider,  # 别名
        'deepseek': DeepSeekProvider,
        'glm': GLMProvider,
        'zhipu': GLMProvider,  # 别名
        'doubao': DoubaoProvider,
    }

    provider_class = providers.get(provider_type)
    if not provider_class:
        logger.warn(f"未知的模型提供者: {provider_type}，使用默认的 Claude")
        provider_class = ClaudeProvider

    logger.info(f"使用模型提供者: {provider_type}")
    return provider_class(config, logger)
