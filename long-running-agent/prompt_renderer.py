#!/usr/bin/env python3
"""提示词模板渲染 — 将 {{VAR}} 替换为配置值"""


def render_prompt(template_path: str, variables: dict) -> str:
    """读取模板文件并替换所有 {{KEY}} 占位符"""
    with open(template_path, 'r', encoding='utf-8') as f:
        template = f.read()
    for key, value in variables.items():
        template = template.replace('{{' + key + '}}', str(value))
    return template


def build_variables(config, extra: dict = None) -> dict:
    """从 Config 对象构建模板变量字典"""
    var_map = {
        'PROJECT_NAME': config.project_name,
        'PROJECT_TYPE': config.project_type,
        'PROJECT_DESCRIPTION': config.project_description,
        'WORK_DIRECTORY': config.work_directory,
        'USER_TASK': config.user_task,
        'TASK_LIST_FILE': config.task_list_file,
        'PROGRESS_FILE': config.progress_file,
        'COMPILE_COMMAND': config.compile_command,
        'TEST_COMMAND': config.test_command,
        'RUN_COMMAND': config.run_command,
    }
    if extra:
        var_map.update(extra)
    return var_map
