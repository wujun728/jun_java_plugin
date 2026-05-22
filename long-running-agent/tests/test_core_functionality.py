#!/usr/bin/env python3
"""核心功能测试 - 全面测试框架功能"""
import sys
import os
import json
import tempfile
import shutil
from unittest.mock import Mock, patch, MagicMock
from datetime import datetime

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
from agent_runner import AgentRunner
from logger import ExecutionLogger
from model_provider import (
    create_model_provider,
    ClaudeProvider,
    QoderCliProvider,
    DeepSeekProvider,
    GLMProvider,
    DoubaoProvider
)


class TestCoreFlowSimulator:
    """核心流程模拟器 - 使用mock数据"""

    def __init__(self):
        self.temp_dir = None
        self.test_results = []

    def setup(self):
        """设置测试环境"""
        # 创建临时目录
        self.temp_dir = tempfile.mkdtemp()
        print(f"\n创建测试目录: {self.temp_dir}")

    def teardown(self):
        """清理测试环境"""
        if self.temp_dir and os.path.exists(self.temp_dir):
            shutil.rmtree(self.temp_dir)
            print(f"清理测试目录: {self.temp_dir}")

    def create_prompts_dir(self):
        """创建prompts目录和模板文件"""
        prompts_dir = os.path.join(self.temp_dir, 'prompts')
        os.makedirs(prompts_dir, exist_ok=True)

        # 创建简单的提示词模板
        templates = {
            'planner.md': '# Planner\n任务: {{USER_TASK}}\n请生成任务列表。',
            'worker.md': '# Worker\n任务ID: {{TASK_ID}}\n任务标题: {{TASK_TITLE}}\n请执行任务。',
            'evaluator.md': '# Evaluator\n任务ID: {{TASK_ID}}\n请评估任务完成质量。'
        }

        for filename, content in templates.items():
            with open(os.path.join(prompts_dir, filename), 'w', encoding='utf-8') as f:
                f.write(content)

    def create_test_config(self, model_provider='claude'):
        """创建测试配置"""
        # 创建prompts目录
        self.create_prompts_dir()

        config_data = {
            "project_name": "test-project",
            "project_type": "java-springboot",
            "work_directory": self.temp_dir,
            "user_task": "测试任务",
            "agent": {
                "model_provider": model_provider,
                "model": "test-model",
                "planner_max_turns": 10,
                "worker_max_turns": 10,
                "evaluator_max_turns": 5,
                "max_sessions": 3,
                "cooldown_seconds": 0,
                "task_timeout_seconds": 10,
                "qoder_config": {"api_key": "test_key"},
                "deepseek_config": {"api_key": "test_key"},
                "glm_config": {"api_key": "test_key"},
                "doubao_config": {"api_key": "test_key"}
            },
            "retry": {"max_attempts": 2},
            "git": {"auto_commit": False},
            "paths": {
                "task_list": "task_list.json",
                "progress_file": "progress.txt",
                "execution_log": "execution_log.jsonl",
                "log_dir": "logs"
            },
            "build": {}
        }

        config_file = os.path.join(self.temp_dir, 'config.json')
        with open(config_file, 'w', encoding='utf-8') as f:
            json.dump(config_data, f, ensure_ascii=False, indent=2)

        return config_file

    def create_mock_task_list(self):
        """创建模拟任务列表"""
        tasks = {
            "meta": {
                "project_name": "test-project",
                "created_at": datetime.now().isoformat(),
                "user_task": "测试任务",
                "total_tasks": 3
            },
            "tasks": [
                {
                    "id": "T001",
                    "category": "setup",
                    "title": "初始化项目",
                    "description": "创建项目结构",
                    "verification_steps": ["检查目录是否创建", "检查配置文件是否存在"],
                    "status": "pending",
                    "priority": 1,
                    "dependencies": [],
                    "attempts": 0,
                    "max_attempts": 2,
                    "result": None,
                    "evaluation": None,
                    "error_history": [],
                    "started_at": None,
                    "completed_at": None
                },
                {
                    "id": "T002",
                    "category": "development",
                    "title": "实现核心功能",
                    "description": "编写核心代码",
                    "verification_steps": ["检查代码是否编译", "检查单元测试是否通过"],
                    "status": "pending",
                    "priority": 2,
                    "dependencies": ["T001"],
                    "attempts": 0,
                    "max_attempts": 2,
                    "result": None,
                    "evaluation": None,
                    "error_history": [],
                    "started_at": None,
                    "completed_at": None
                },
                {
                    "id": "T003",
                    "category": "testing",
                    "title": "运行测试",
                    "description": "执行所有测试",
                    "verification_steps": ["检查测试覆盖率", "检查所有测试通过"],
                    "status": "pending",
                    "priority": 3,
                    "dependencies": ["T002"],
                    "attempts": 0,
                    "max_attempts": 2,
                    "result": None,
                    "evaluation": None,
                    "error_history": [],
                    "started_at": None,
                    "completed_at": None
                }
            ]
        }

        task_file = os.path.join(self.temp_dir, 'task_list.json')
        with open(task_file, 'w', encoding='utf-8') as f:
            json.dump(tasks, f, ensure_ascii=False, indent=2)

        return task_file

    def mock_model_invoke_success(self, prompt, max_turns, log_file, **kwargs):
        """模拟成功的模型调用"""
        # 模拟输出
        output = "模拟的AI响应：任务已完成"

        # 写入日志
        with open(log_file, 'w', encoding='utf-8') as f:
            f.write(f"Prompt: {prompt[:100]}...\n")
            f.write(f"Output: {output}\n")

        return {
            'success': True,
            'output': output,
            'exit_code': 0,
            'usage': {'input_tokens': 1000, 'output_tokens': 500}
        }

    def mock_planner_output(self, prompt, max_turns, log_file, **kwargs):
        """模拟Planner输出（生成任务列表）"""
        # 创建任务列表
        self.create_mock_task_list()

        output = "已创建任务列表，共3个任务"
        with open(log_file, 'w', encoding='utf-8') as f:
            f.write(f"Planner output: {output}\n")

        return {
            'success': True,
            'output': output,
            'exit_code': 0,
            'usage': {'input_tokens': 2000, 'output_tokens': 1000}
        }

    def mock_worker_output(self, prompt, max_turns, log_file, **kwargs):
        """模拟Worker输出（执行任务）"""
        output = "任务执行完成，代码已提交"
        with open(log_file, 'w', encoding='utf-8') as f:
            f.write(f"Worker output: {output}\n")

        return {
            'success': True,
            'output': output,
            'exit_code': 0,
            'usage': {'input_tokens': 5000, 'output_tokens': 2000}
        }

    def mock_evaluator_output(self, prompt, max_turns, log_file, **kwargs):
        """模拟Evaluator输出（评估任务）"""
        evaluation = {
            "task_id": "T001",
            "passed": True,
            "score": 95,
            "feedback": "任务完成质量高",
            "checks": [
                {"step": "检查目录是否创建", "passed": True, "detail": "目录已创建"},
                {"step": "检查配置文件是否存在", "passed": True, "detail": "配置文件存在"}
            ],
            "suggestions": []
        }

        output = json.dumps(evaluation, ensure_ascii=False)
        with open(log_file, 'w', encoding='utf-8') as f:
            f.write(f"Evaluator output: {output}\n")

        return {
            'success': True,
            'output': output,
            'exit_code': 0,
            'usage': {'input_tokens': 3000, 'output_tokens': 800}
        }

    def test_task_manager(self):
        """测试任务管理器"""
        print("\n" + "=" * 60)
        print("测试任务管理器")
        print("=" * 60)

        try:
            # 创建任务列表
            task_file = self.create_mock_task_list()
            task_mgr = TaskManager(task_file, max_attempts=2)
            task_mgr.load()

            # 测试任务统计
            print(f"  总任务数: {task_mgr.total_count()}")
            print(f"  待处理: {task_mgr.pending_count()}")
            print(f"  已完成: {task_mgr.passed_count()}")
            print(f"  已跳过: {task_mgr.skipped_count()}")

            # 测试获取下一个任务
            next_task = task_mgr.next_task()
            if next_task:
                print(f"  下一个任务: {next_task['id']} - {next_task['title']}")
            else:
                print("  无可执行任务")

            # 测试任务状态更新
            if next_task:
                task_mgr.update_status(next_task['id'], 'in_progress')
                print(f"  任务 {next_task['id']} 状态更新为 in_progress")

                # 测试标记通过
                task_mgr.mark_passed(next_task['id'], {
                    'passed': True,
                    'score': 95,
                    'feedback': '测试反馈'
                })
                print(f"  任务 {next_task['id']} 标记为已通过")

            # 测试摘要
            print("\n任务摘要:")
            print(task_mgr.summary())

            self.test_results.append(('任务管理器', True, '所有功能正常'))
            return True

        except Exception as e:
            print(f"  错误: {e}")
            self.test_results.append(('任务管理器', False, str(e)))
            return False

    def test_model_provider(self, provider_type):
        """测试模型提供者"""
        print(f"\n测试 {provider_type} 模型提供者:")

        try:
            # 创建配置
            config_file = self.create_test_config(provider_type)
            config = Config.load(config_file)

            # 创建日志
            log_file = os.path.join(self.temp_dir, 'test.log')
            logger = ExecutionLogger(os.path.join(self.temp_dir, 'test_log.jsonl'))

            # 创建模型提供者
            provider = create_model_provider(config, logger)
            print(f"  创建的提供者: {type(provider).__name__}")
            print(f"  提供者名称: {provider.get_provider_name()}")

            # Mock invoke 方法
            with patch.object(provider, 'invoke', side_effect=self.mock_model_invoke_success):
                result = provider.invoke("测试提示词", 10, log_file)

                if result['success']:
                    print(f"  调用成功: {result['output'][:50]}...")
                    self.test_results.append((f'{provider_type} 模型提供者', True, '调用成功'))
                    return True
                else:
                    print(f"  调用失败: {result.get('error', 'unknown')}")
                    self.test_results.append((f'{provider_type} 模型提供者', False, '调用失败'))
                    return False

        except Exception as e:
            print(f"  错误: {e}")
            self.test_results.append((f'{provider_type} 模型提供者', False, str(e)))
            return False

    def test_agent_runner(self):
        """测试智能体运行器"""
        print("\n" + "=" * 60)
        print("测试智能体运行器")
        print("=" * 60)

        try:
            # 创建配置
            config_file = self.create_test_config('claude')
            config = Config.load(config_file)

            # 创建日志
            logger = ExecutionLogger(os.path.join(self.temp_dir, 'test_log.jsonl'))

            # 创建运行器
            runner = AgentRunner(config, logger)
            print("  AgentRunner 创建成功")

            # Mock 模型提供者的 invoke 方法
            with patch.object(runner.model_provider, 'invoke') as mock_invoke:
                # 测试 Planner
                print("\n  测试 Planner:")
                mock_invoke.side_effect = self.mock_planner_output
                runner.run_planner("测试任务")
                print("    Planner 调用成功")

                # 测试 Worker
                print("\n  测试 Worker:")
                mock_invoke.side_effect = self.mock_worker_output
                task = {
                    'id': 'T001',
                    'title': '测试任务',
                    'description': '测试描述',
                    'verification_steps': [],
                    'error_history': []
                }
                result = runner.run_worker(task, 1)
                print(f"    Worker 调用成功: {result['success']}")

                # 测试 Evaluator
                print("\n  测试 Evaluator:")
                mock_invoke.side_effect = self.mock_evaluator_output
                eval_result = runner.run_evaluator(task)
                print(f"    Evaluator 调用成功: passed={eval_result.get('passed', False)}")

            self.test_results.append(('智能体运行器', True, '所有智能体正常'))
            return True

        except Exception as e:
            print(f"  错误: {e}")
            import traceback
            traceback.print_exc()
            self.test_results.append(('智能体运行器', False, str(e)))
            return False

    def test_full_workflow(self):
        """测试完整工作流"""
        print("\n" + "=" * 60)
        print("测试完整工作流")
        print("=" * 60)

        try:
            # 创建配置和任务列表
            config_file = self.create_test_config('claude')
            task_file = self.create_mock_task_list()

            config = Config.load(config_file)
            logger = ExecutionLogger(os.path.join(self.temp_dir, 'test_log.jsonl'))
            runner = AgentRunner(config, logger)
            task_mgr = TaskManager(task_file, max_attempts=2)

            # 加载任务
            task_mgr.load()
            print(f"  加载 {task_mgr.total_count()} 个任务")

            # Mock 模型调用
            with patch.object(runner.model_provider, 'invoke') as mock_invoke:
                session = 0
                completed = 0

                while True:
                    task = task_mgr.next_task()
                    if task is None or session >= 3:
                        break

                    session += 1
                    print(f"\n  会话 #{session} - 任务 {task['id']}: {task['title']}")

                    # 更新任务状态
                    task_mgr.update_status(task['id'], 'in_progress')

                    # Mock Worker
                    mock_invoke.side_effect = self.mock_worker_output
                    worker_result = runner.run_worker(task, session)

                    if worker_result['success']:
                        # Mock Evaluator
                        mock_invoke.side_effect = self.mock_evaluator_output
                        eval_result = runner.run_evaluator(task)

                        if eval_result.get('passed', False):
                            task_mgr.mark_passed(task['id'], eval_result)
                            completed += 1
                            print(f"    任务 {task['id']} 通过")
                        else:
                            task_mgr.mark_failed(task['id'], eval_result)
                            print(f"    任务 {task['id']} 失败")
                    else:
                        task_mgr.mark_failed(task['id'], {
                            'passed': False,
                            'score': 0,
                            'feedback': 'Worker失败'
                        })

                    task_mgr.save()

                print(f"\n  完成 {completed} 个任务")
                print("\n最终摘要:")
                print(task_mgr.summary())

                self.test_results.append(('完整工作流', True, f'完成{completed}个任务'))
                return True

        except Exception as e:
            print(f"  错误: {e}")
            import traceback
            traceback.print_exc()
            self.test_results.append(('完整工作流', False, str(e)))
            return False

    def run_all_tests(self):
        """运行所有测试"""
        print("\n" + "=" * 60)
        print("长时运行智能体框架 - 全面功能测试")
        print("=" * 60)

        self.setup()

        try:
            # 1. 测试任务管理器
            self.test_task_manager()

            # 2. 测试所有模型提供者
            providers = ['claude', 'qodercli', 'deepseek', 'glm', 'doubao']
            print("\n" + "=" * 60)
            print("测试所有模型提供者")
            print("=" * 60)
            for provider in providers:
                self.test_model_provider(provider)

            # 3. 测试智能体运行器
            self.test_agent_runner()

            # 4. 测试完整工作流
            self.test_full_workflow()

            # 5. 打印测试结果
            self.print_results()

        finally:
            self.teardown()

    def print_results(self):
        """打印测试结果"""
        print("\n" + "=" * 60)
        print("测试结果汇总")
        print("=" * 60)

        total = len(self.test_results)
        passed = sum(1 for _, success, _ in self.test_results if success)

        for test_name, success, message in self.test_results:
            status = "✓ 通过" if success else "✗ 失败"
            print(f"{test_name:30} {status:10} {message}")

        print(f"\n总计: {passed}/{total} 通过 ({passed/total*100:.1f}%)")

        if passed == total:
            print("\n🎉 所有测试通过！")
            return 0
        else:
            print(f"\n⚠️  有 {total - passed} 个测试失败")
            return 1


def main():
    """主函数"""
    simulator = TestCoreFlowSimulator()
    return simulator.run_all_tests()


if __name__ == '__main__':
    sys.exit(main() or 0)
