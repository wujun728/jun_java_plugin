# 长时运行智能体框架 — 操作手册

> 版本: v2 | 更新日期: 2026-04-02

---

## 一、系统概述

本框架是一个基于 **Anthropic 三智能体架构** 的自动化任务编排系统。用户只需输入一个大任务描述，框架会自动将其拆分为数十个子任务，逐个执行并独立验证，直到所有任务完成。

**核心理念：** 小步快跑、每步干净、独立验证、失败重试。

### 架构图

```
用户输入任务
    │
    ▼
┌─────────────────┐
│   Planner 规划器  │  ← 分析任务，生成 30-80 个子任务清单
│   (claude -p)    │  → 输出 task_list.json
└────────┬────────┘
         │
         ▼
┌─────────────────┐     ┌──────────────────┐
│   Worker 执行器   │────▶│  Evaluator 评估器  │
│   (claude -p)    │     │   (claude -p)     │
│   编写代码+提交   │     │   只读验证+打分    │
└────────┬────────┘     └────────┬─────────┘
         │                       │
         │    ┌──────────────────┘
         │    │
         ▼    ▼
    ┌──────────────┐
    │ Python 编排器  │  ← main.py 控制整个循环
    │ 状态判断+重试  │
    └──────────────┘
         │
    通过 → 下一个任务
    失败 → 重试（最多 N 次）→ 超限则跳过
    全部完成 → 输出最终报告
```

### 三个智能体的分工

| 智能体 | 角色 | 能力范围 | 提示词文件 |
|--------|------|---------|-----------|
| **Planner** | 任务规划师 | 读代码、分析结构、生成 task_list.json、创建脚手架 | `prompts/planner.md` |
| **Worker** | 编码工程师 | 读写代码、编译、测试、Git 提交 | `prompts/worker.md` |
| **Evaluator** | 质量检查员 | **只读**：检查文件、运行编译、验证结果、输出评分 JSON | `prompts/evaluator.md` |

---

## 二、前置条件

### 必需环境

| 依赖 | 最低版本 | 验证命令 |
|------|---------|---------|
| Python | 3.8+ | `python --version` |
| Claude Code CLI | 2.x | `claude --version` |
| Git | 任意 | `git --version` |

### 安装 Claude Code CLI

```bash
npm install -g @anthropic-ai/claude-code
```

安装后确认 `claude` 命令在 PATH 中可用。

---

## 三、快速开始

### 3.1 启动新任务

```bash
# 进入你的项目目录（框架会在这里工作）
cd /path/to/your/project

python ./long-running-agent/main.py --task "根据当前项目下面的 D:\workspace_github_v2\10技术基础层\jun_java_plugin\ 文件夹，完成下面的任务。任务1，项目模块很多但是不全面，你需要作为一个java专家，这个是你的代码仓库，你需要补充并完善整个仓库，包括技术栈覆盖，代码实现，示例实现等等。你需要遍历整个仓库，输出完整的md文档。你需要编译及测试所有的模块并保障功能正常。仓库里面的功能每个作为一块功能点，有冗余的就干掉，或者补充进来。我希望整个仓库能是一个完整的java技术栈，完整的java基础示例、完整的功能参考示例仓库，可以共享给其他人或者其他的大模型，后面可以直接参考仓库的代码或者文档，使用仓库的组件及功能等，并能帮助到所有的java开发人员。"

python ./long-running-agent/main.py --task " 分析目录 D:\workspace_github_v2\20平台能力层\jun_api_service\jun_dbapi ，下面的代码，找打有问题的bug。"

任务：升级前端页面，对着原前端页面升级到新前端页面中，其中先清理一波新前端无效的页面，原前端的vue文件直接copy过来小改使用loader方式加载。我需要在新的前端体验到所有的原前端的功能。
新前端：D:\workspace_github_v2\30业务产品层\jun_product_center_2026\jun_oa\jun_ui_sa_admin\
后端：D:\workspace_github_v2\30业务产品层\jun_product_center_2026\jun_oa\ruoyi-vue-oa\
原前端：D:\workspace_github_v2\30业务产品层\jun_product_center_2026\jun_oa\ruoyi-vue-oa-ui\
D:\workspace_github_v2\30业务产品层\jun_product_center_2026\jun_oa\yudao-ui-admin-vue3



python ./long-running-agent/main.py --task " 任务：升级前端页面，对着原前端页面升级到新前端页面中，其中先清理一波新前端无效的页面，原前端的vue文件直接copy过来小改使用loader方式加载。我需要在新的前端体验到所有的原前端的功能。
新前端：D:\workspace_github_v2\30业务产品层\jun_product_center_2026\jun_oa\yudao-ui-admin-vue3
后端：D:\workspace_github_v2\30业务产品层\jun_product_center_2026\jun_oa\ruoyi-vue-oa\
原前端：D:\workspace_github_v2\30业务产品层\jun_product_center_2026\jun_oa\ruoyi-vue-oa-ui\"


python ./long-running-agent/main.py --task " 任务：升级前端页面，对着原前端页面升级到新前端页面中，其中先清理一波新前端无效的页面，原前端的vue文件直接copy过来小改使用loader方式加载。我需要在新的前端体验到所有的原前端的功能。
新前端：D:\workspace_github_v2\30业务产品层\jun_product_center_2026\jun_oa\jun_ui_sa_admin\
后端：D:\workspace_github_v2\30业务产品层\jun_product_center_2026\jun_oa\ruoyi-vue-oa\
原前端：D:\workspace_github_v2\30业务产品层\jun_product_center_2026\jun_oa\ruoyi-vue-oa-ui\"


# 启动（使用默认配置）
python /path/to/long-running-agent/main.py --task "分析目录 D:\workspace_github_v2\20平台能力层\jun_api_service\jun_dbapi ，下面的代码，找打有问题的bug。"

# 启动（指定配置文件）
python /path/to/long-running-agent/main.py --task "你的任务描述" --config /path/to/config.json

# 启动（限制最大会话数）
python /path/to/long-running-agent/main.py --task "你的任务描述" --max-sessions 10
```

**执行流程：**
1. 框架启动 Planner 智能体，分析任务并生成 `task_list.json`
2. 进入执行循环：逐个取出待处理任务
3. 每个任务：Worker 执行 → Evaluator 验证 → 通过/失败/重试
4. 全部完成后输出最终报告

### 3.2 查看进度

```bash
python ./long-running-agent/main.py --status
```

输出示例：
```
进度: 12/35 (34.3%)
  已完成: 12  失败/跳过: 1  待处理: 22
  setup              3/3    [####################]
  database           4/5    [################....]
  functional         3/10   [######..............]
  api                2/8    [#####...............]
```

### 3.3 中断与恢复

**中断：** 随时按 `Ctrl+C`，框架会保存当前进度。

**恢复：**
```bash
python /path/to/long-running-agent/main.py --resume
```

恢复时框架从 `task_list.json` 读取状态，跳过已完成的任务，从断点继续执行。

### 3.4 生成报告

```bash
python /path/to/long-running-agent/main.py --report
```

输出示例：
```
============================================================
  最终报告
============================================================
  总任务数:  35
  已完成:    32
  已跳过:    2
  待处理:    1

  已完成的任务:
    + [T001] 项目结构初始化
    + [T002] 数据库表设计
    ...
  已跳过的任务 (失败超过重试上限):
    x [T015] OAuth2 集成 (尝试 3 次)
============================================================
```

---

## 四、命令行参数一览

| 参数 | 说明 | 示例 |
|------|------|------|
| `--task "描述"` | 指定任务描述，启动新任务 | `--task "构建用户管理系统"` |
| `--resume` | 从断点恢复执行 | `--resume` |
| `--config path` | 指定配置文件路径 | `--config myproject.json` |
| `--status` | 查看当前进度（不执行） | `--status` |
| `--report` | 生成最终报告（不执行） | `--report` |
| `--max-sessions N` | 覆盖最大会话数 | `--max-sessions 20` |

**参数可组合使用：**
```bash
# 用自定义配置恢复执行
python main.py --resume --config custom.json

# 用自定义配置恢复，限制会话数
python main.py --resume --config custom.json --max-sessions 10
```

---

## 五、配置文件详解

配置文件为 JSON 格式，默认位于 `long-running-agent/config.json`。

### 5.1 完整配置模板

```json
{
  "project_name": "my-project",
  "project_type": "java-springboot",
  "project_description": "项目描述，帮助智能体理解上下文",
  "work_directory": ".",
  "user_task": "在此填写你的完整任务描述...",

  "agent": {
    "model": "opus",
    "planner_max_turns": 200,
    "worker_max_turns": 150,
    "evaluator_max_turns": 50,
    "max_sessions": 50,
    "cooldown_seconds": 5,
    "task_timeout_seconds": 600
  },

  "retry": {
    "max_attempts": 3
  },

  "git": {
    "auto_commit": true,
    "branch_prefix": "agent/"
  },

  "paths": {
    "task_list": "task_list.json",
    "progress_file": "claude-progress.txt",
    "execution_log": "execution_log.jsonl",
    "log_dir": "logs"
  },

  "build": {
    "compile_command": "mvn clean compile -DskipTests",
    "test_command": "mvn test",
    "run_command": "mvn spring-boot:run"
  }
}
```

### 5.2 配置项说明

#### 基础信息

| 字段 | 类型 | 说明 | 默认值 |
|------|------|------|--------|
| `project_name` | string | 项目名称，显示在日志和提示词中 | `my-project` |
| `project_type` | string | 项目类型，影响 Planner 的拆分策略 | `java-springboot` |
| `project_description` | string | 项目描述，帮助智能体理解上下文 | `""` |
| `work_directory` | string | 工作目录，`.` 表示当前目录 | `.` |
| `user_task` | string | 任务描述（也可通过 `--task` 参数覆盖） | `""` |

#### 智能体参数 (`agent`)

| 字段 | 类型 | 说明 | 默认值 |
|------|------|------|--------|
| `model` | string | Claude 模型（opus/sonnet/haiku） | `opus` |
| `planner_max_turns` | int | 规划器最大轮数（保留字段，CLI 暂不支持） | `200` |
| `worker_max_turns` | int | 执行器最大轮数（保留字段） | `150` |
| `evaluator_max_turns` | int | 评估器最大轮数（保留字段） | `50` |
| `max_sessions` | int | 最大会话数，防止无限循环 | `50` |
| `cooldown_seconds` | int | 每个会话之间的冷却时间（秒） | `5` |
| `task_timeout_seconds` | int | 单次 Claude CLI 调用的超时时间（秒） | `600` |

#### 重试策略 (`retry`)

| 字段 | 类型 | 说明 | 默认值 |
|------|------|------|--------|
| `max_attempts` | int | 单个任务最大重试次数 | `3` |

#### 构建命令 (`build`)

| 字段 | 类型 | 说明 | 默认值 |
|------|------|------|--------|
| `compile_command` | string | 编译命令，Worker 用于验证代码可编译 | `""` |
| `test_command` | string | 测试命令 | `""` |
| `run_command` | string | 运行命令 | `""` |

### 5.3 不同项目类型的配置示例

**Java SpringBoot 项目：**
```json
{
  "project_type": "java-springboot",
  "build": {
    "compile_command": "mvn clean compile -DskipTests",
    "test_command": "mvn test",
    "run_command": "mvn spring-boot:run"
  }
}
```

**Vue.js 前端项目：**
```json
{
  "project_type": "vue-frontend",
  "build": {
    "compile_command": "npm run build",
    "test_command": "npm run test:unit",
    "run_command": "npm run serve"
  }
}
```

**全栈项目：**
```json
{
  "project_type": "fullstack",
  "build": {
    "compile_command": "mvn clean compile -DskipTests && cd frontend && npm run build",
    "test_command": "mvn test && cd frontend && npm run test:unit",
    "run_command": "mvn spring-boot:run"
  }
}
```

更多完整配置参见 `examples/` 目录。

---

## 六、任务状态机

### 状态流转

```
pending ──────▶ in_progress ──────▶ evaluating ──────▶ passed ✓
                    │                    │
                    │               失败且未超限
                    │                    │
                    │                    ▼
                    │                 failed
                    │                    │
                    │               重新进入
                    │                    │
                    ◄────────────────────┘
                    │
               失败且超过 max_attempts
                    │
                    ▼
                 skipped ✗
```

| 状态 | 含义 |
|------|------|
| `pending` | 等待执行 |
| `in_progress` | Worker 正在执行 |
| `evaluating` | Evaluator 正在验证 |
| `passed` | 验证通过，任务完成 |
| `failed` | 验证未通过，等待重试 |
| `skipped` | 超过最大重试次数，跳过 |

### 依赖处理

- 任务可通过 `dependencies` 字段声明依赖其他任务
- 只有所有依赖的任务状态为 `passed`，当前任务才会被选中执行
- 如果依赖的任务被 `skipped`，当前任务也会自动被 `skipped`

---

## 七、生成的文件说明

框架运行过程中会在 **工作目录**（`work_directory`）下生成以下文件：

| 文件/目录 | 生成者 | 说明 |
|----------|--------|------|
| `task_list.json` | Planner | 核心持久化文件，包含所有任务定义和状态 |
| `claude-progress.txt` | Planner + Worker | 人可读的进度记录，每个 Worker 会话追加记录 |
| `execution_log.jsonl` | Python 编排器 | 机器可读的结构化日志，每行一条 JSON 事件 |
| `logs/` | Python 编排器 | 每次智能体调用的完整输出日志 |
| `logs/planner.log` | Planner 调用 | 规划器的完整 Claude CLI 输出 |
| `logs/worker-{id}-s{n}.log` | Worker 调用 | 第 n 个会话执行任务 id 的完整输出 |
| `logs/eval-{id}.log` | Evaluator 调用 | 评估任务 id 的完整输出 |
| `init.sh` / `init.bat` | Planner | 环境初始化脚本 |

### task_list.json 结构

```json
{
  "meta": {
    "project_name": "my-project",
    "created_at": "2026-04-02T10:00:00",
    "user_task": "原始任务描述",
    "total_tasks": 35
  },
  "tasks": [
    {
      "id": "T001",
      "category": "setup",
      "title": "简短标题",
      "description": "详细描述",
      "verification_steps": ["验证步骤1", "验证步骤2"],
      "status": "pending",
      "priority": 1,
      "dependencies": [],
      "attempts": 0,
      "max_attempts": 3,
      "result": null,
      "evaluation": null,
      "error_history": [],
      "started_at": null,
      "completed_at": null
    }
  ]
}
```

### execution_log.jsonl 事件类型

| 事件 | 含义 |
|------|------|
| `planner_start` | 开始规划 |
| `planner_done` | 规划完成 |
| `session_resume` | 从断点恢复 |
| `worker_start` | Worker 开始执行任务 |
| `worker_done` | Worker 执行完毕 |
| `worker_failed` | Worker 本身失败（超时等） |
| `eval_start` | Evaluator 开始评估 |
| `eval_done` | Evaluator 评估完毕 |
| `all_done` | 所有任务处理完毕 |
| `interrupted` | 用户中断 (Ctrl+C) |
| `session_end` | 会话结束，输出报告 |

---

## 八、评估机制

### Evaluator 评分标准

| 分数范围 | 含义 | 是否通过 |
|---------|------|---------|
| 80-100 | 所有验证步骤都通过 | 通过 (score >= 70) |
| 60-79 | 大部分通过但有小问题 | 通过 (score >= 70) |
| 0-59 | 核心功能未实现或严重问题 | 不通过 |

### Evaluator 输出格式

Evaluator 必须在回复中输出以下 JSON，Python 编排器会自动提取解析：

```json
{
  "task_id": "T001",
  "passed": true,
  "score": 95,
  "checks": [
    {
      "step": "验证步骤的原文",
      "passed": true,
      "detail": "实际检查结果的说明"
    }
  ],
  "feedback": "失败时的修复建议",
  "suggestions": ["改进建议"]
}
```

### 重试机制

当任务失败时：
1. `error_history` 记录失败原因和 Evaluator 的反馈
2. 下次 Worker 执行时会收到 `ERROR_CONTEXT`，包含上次失败的详细反馈
3. Worker 根据反馈进行针对性修复
4. 超过 `max_attempts` 次后标记为 `skipped`

---

## 九、框架文件结构

```
long-running-agent/
├── main.py                     # 入口 + 主编排循环
├── config.py                   # Config 类，加载 config.json
├── task_manager.py             # TaskManager 类，任务状态机 + 持久化
├── agent_runner.py             # AgentRunner 类，封装 claude CLI 调用
├── logger.py                   # ExecutionLogger 类，JSONL + 控制台日志
├── prompt_renderer.py          # 模板渲染，替换 {{VAR}} 占位符
├── config.json                 # 默认配置文件
├── prompt.md                   # 本操作手册
├── README.md                   # 框架简介文档
├── prompts/
│   ├── planner.md              # 规划器提示词模板
│   ├── worker.md               # 执行器提示词模板
│   └── evaluator.md            # 评估器提示词模板
└── examples/
    ├── java-springboot/        # SpringBoot 项目配置示例
    │   └── config.json
    ├── vue-frontend/           # Vue 前端项目配置示例
    │   └── config.json
    └── fullstack/              # 全栈项目配置示例
        └── config.json
```

---

## 十、模块详解

### 10.1 main.py — 主编排器

**职责：** 命令行入口，控制整个三阶段流程。

**三个阶段：**

1. **规划阶段** — 调用 Planner 生成 task_list.json（新任务），或从文件恢复（`--resume`）
2. **执行循环** — 循环取下一个待处理任务 → Worker 执行 → Evaluator 验证 → 更新状态
3. **报告阶段** — 输出最终统计

**关键逻辑：**
- `Ctrl+C` 时捕获 `KeyboardInterrupt`，保存状态后安全退出
- 每个会话间有 `cooldown_seconds` 冷却期
- 达到 `max_sessions` 上限时自动停止

### 10.2 task_manager.py — 任务管理器

**职责：** 管理 task_list.json 的读写、任务状态流转、依赖解析。

**关键方法：**

| 方法 | 说明 |
|------|------|
| `load()` | 加载 task_list.json，自动兼容 v1/v2 格式 |
| `save()` | 保存当前状态到 task_list.json |
| `next_task()` | 返回下一个可执行的任务（按优先级排序，依赖已满足） |
| `update_status(id, status)` | 更新任务状态 |
| `mark_passed(id, eval)` | 标记任务通过 |
| `mark_failed(id, eval)` | 标记任务失败，记录 error_history |
| `summary()` | 返回进度摘要文本 |
| `generate_report()` | 生成最终报告数据 |

**任务选择算法（`next_task`）：**
1. 过滤掉 `passed` 和 `skipped` 的任务
2. 检查失败次数是否超限，超限则标记 `skipped`
3. 检查依赖是否全部 `passed`，依赖被 `skipped` 则自动跳过
4. 按 `(priority, id)` 排序，取第一个

### 10.3 agent_runner.py — 智能体调用器

**职责：** 封装 `claude -p` CLI 调用，处理 prompt 渲染、日志保存、结果解析。

**CLI 调用方式：**
```bash
claude -p --model opus --output-format json --dangerously-skip-permissions
# prompt 通过 stdin 传入
```

**Windows 兼容处理：**
- `shell=True` — Windows 需要通过 shell 找到 `claude.cmd`
- `encoding='utf-8'` — 避免 GBK 编码错误
- `errors='replace'` — 遇到无法解码的字符时替换而非报错

**评估结果解析（`_parse_evaluation`）：**
1. 先从 CLI 的 JSON 输出中提取 `result` 字段
2. 在 result 文本中用正则查找包含 `"passed"` 的 JSON 对象
3. 通过大括号计数提取完整 JSON
4. 如果解析失败，兜底返回 `passed=False`

### 10.4 prompt_renderer.py — 提示词渲染

**职责：** 读取提示词模板文件，将 `{{VAR}}` 占位符替换为实际值。

**可用变量：**

| 变量名 | 来源 | 说明 |
|--------|------|------|
| `{{PROJECT_NAME}}` | config | 项目名称 |
| `{{PROJECT_TYPE}}` | config | 项目类型 |
| `{{PROJECT_DESCRIPTION}}` | config | 项目描述 |
| `{{WORK_DIRECTORY}}` | config | 工作目录绝对路径 |
| `{{USER_TASK}}` | 命令行/config | 用户任务描述 |
| `{{COMPILE_COMMAND}}` | config | 编译命令 |
| `{{TEST_COMMAND}}` | config | 测试命令 |
| `{{PROGRESS_FILE}}` | config | 进度文件名 |
| `{{SESSION_NUMBER}}` | 运行时 | 当前会话编号 |
| `{{TASK_ID}}` | 运行时 | 当前任务 ID |
| `{{TASK_TITLE}}` | 运行时 | 任务标题 |
| `{{TASK_DESCRIPTION}}` | 运行时 | 任务详细描述 |
| `{{VERIFICATION_STEPS}}` | 运行时 | 验证步骤 JSON 数组 |
| `{{ERROR_CONTEXT}}` | 运行时 | 上次失败的反馈（重试时） |

### 10.5 logger.py — 结构化日志

**职责：** 双输出日志系统。

- **JSONL 文件** (`execution_log.jsonl`) — 每行一条 JSON，机器可读
- **控制台** — 实时输出 `[时间戳] 事件类型 数据`

---

## 十一、操作场景

### 场景 1：从零开始构建新项目

```bash
# 1. 创建项目目录
mkdir my-project && cd my-project
git init

# 2. 准备配置文件（可选，不准备则使用默认配置）
cp /path/to/long-running-agent/examples/java-springboot/config.json ./agent-config.json
# 编辑 agent-config.json 中的 project_name、project_description 等

# 3. 启动
python /path/to/long-running-agent/main.py \
  --task "构建一个用户管理系统，包含JWT认证、CRUD接口、角色权限" \
  --config ./agent-config.json

# 4. 等待执行... 随时 Ctrl+C 中断

# 5. 查看进度
python /path/to/long-running-agent/main.py --status --config ./agent-config.json

# 6. 恢复执行
python /path/to/long-running-agent/main.py --resume --config ./agent-config.json
```

### 场景 2：在已有项目中添加功能

```bash
cd /path/to/existing-project

# 直接启动，框架会分析现有代码结构
python /path/to/long-running-agent/main.py \
  --task "为现有项目添加操作日志记录功能，记录所有API调用"
```

### 场景 3：生成文档

```bash
cd /path/to/project

python /path/to/long-running-agent/main.py \
  --task "为项目生成完整的技术文档，包含架构说明、API文档、部署指南" \
  --max-sessions 10
```

### 场景 4：跑一部分后手动调整

```bash
# 1. 先跑 5 个会话
python main.py --task "..." --max-sessions 5

# 2. 查看进度和 task_list.json
python main.py --status
cat task_list.json | python -m json.tool

# 3. 手动编辑 task_list.json（如调整优先级、修改描述、跳过某些任务）
# 将不想执行的任务 status 改为 "skipped"
# 调整任务描述使其更精确

# 4. 继续执行
python main.py --resume --max-sessions 10
```

---

## 十二、故障排除

### 问题：规划器未生成 task_list.json

**可能原因及解决方案：**

1. **Claude CLI 未安装或不在 PATH 中**
   ```bash
   claude --version  # 应输出版本号
   ```

2. **prompt 未正确传递**（已修复，通过 stdin 传入）
   - 检查 `logs/planner.log` 查看 Claude 的实际回复

3. **工作目录权限问题**
   - 确认当前用户对工作目录有写权限

### 问题：Worker 执行超时

**解决方案：** 增大 `task_timeout_seconds`（默认 600 秒）

```json
{
  "agent": {
    "task_timeout_seconds": 1200
  }
}
```

### 问题：Evaluator 评估结果解析失败

**现象：** 日志出现 "无法解析 Evaluator 输出，默认为失败"

**排查：**
1. 查看 `logs/eval-{task_id}.log`，确认 Evaluator 是否输出了 JSON
2. JSON 格式可能不规范（多余逗号、缺少引号等）
3. 框架会自动将解析失败视为任务未通过，Worker 会重试

### 问题：Windows 控制台乱码

**解决方案：**
```bash
# 方法 1：设置环境变量
set PYTHONIOENCODING=utf-8
python main.py --task "..."

# 方法 2：切换代码页
chcp 65001
python main.py --task "..."
```

### 问题：所有任务都被跳过

**可能原因：** 依赖链断裂。某个早期任务失败并被 skipped，依赖它的后续任务会级联 skipped。

**排查：**
```bash
# 查看哪些任务被 skipped
python main.py --report

# 检查具体任务的失败原因
cat task_list.json | python -c "
import json, sys
data = json.load(sys.stdin)
for t in data['tasks']:
    if t['status'] == 'skipped':
        print(f\"{t['id']}: {t['title']}\")
        for e in t.get('error_history', []):
            print(f\"  尝试 {e['attempt']}: {e['feedback'][:100]}\")
"
```

**修复：** 手动将根因任务的 status 改回 `pending`、`attempts` 清零，然后 `--resume`。

---

## 十三、高级用法

### 手动编辑 task_list.json

task_list.json 是框架的核心状态文件，你可以随时手动编辑：

- **添加新任务：** 在 `tasks` 数组中追加新条目
- **修改任务描述：** 让 Worker 更精确地理解需求
- **调整优先级：** 改变执行顺序
- **跳过任务：** 将 `status` 改为 `"skipped"`
- **重置失败任务：** 将 `status` 改为 `"pending"`，`attempts` 设为 `0`
- **调整依赖：** 修改 `dependencies` 数组

### 自定义提示词

编辑 `prompts/` 下的模板文件可定制智能体行为：

- `planner.md` — 控制任务拆分的粒度和分类策略
- `worker.md` — 控制编码风格、提交规范、自测流程
- `evaluator.md` — 控制评估标准和打分规则

**注意：** 模板中的 `{{VAR}}` 占位符会被自动替换，不要删除它们。

### 查看详细日志

```bash
# 查看规划器的完整输出
cat logs/planner.log

# 查看某个任务的 Worker 执行详情
cat logs/worker-T001-s1.log

# 查看某个任务的评估详情
cat logs/eval-T001.log

# 查看结构化事件日志
cat execution_log.jsonl | python -m json.tool --no-ensure-ascii
```

### v1 格式兼容

如果你有旧版本（v1）生成的 task_list.json（纯数组格式，使用 `passes`/`steps` 字段），框架会自动迁移为 v2 格式。无需手动转换。

---

## 十四、设计原理

### 为什么三个智能体？

| 问题 | 解决方案 |
|------|---------|
| 大任务超出单次上下文 | **Planner** 拆分为小步骤 |
| 长会话上下文压缩丢信息 | **每次新会话**，从文件重建上下文 |
| Worker 对自己的工作过于宽容 | **Evaluator** 独立验证，不放水 |

### 为什么每次创建新会话？

长时间运行的 Claude 会话会经历上下文压缩（context compression），导致关键信息丢失。通过每个任务使用新的 Claude 会话，从外部文件（task_list.json + claude-progress.txt + git log）重建上下文，确保每次都拥有完整且准确的上下文。

### 为什么用 JSON 格式的任务清单？

Anthropic 实验发现，JSON 格式比 Markdown 更不容易被模型误修改或覆盖。结构化数据也更方便程序化检查和状态管理。

### 为什么一次只做一个任务？

"小步快跑"策略。每个会话结束时代码处于干净、可编译、已提交的状态。即使下一个会话完全丢失上下文，也能通过 Git 历史快速恢复。

---

## 十五、参考资料

- [Anthropic: Effective frameworks for long-running agents](https://www.anthropic.com/engineering/effective-frameworks-for-long-running-agents)
- [Anthropic: Harness design for long-running apps](https://www.anthropic.com/engineering/harness-design-long-running-apps)
- [Claude Code CLI 文档](https://docs.anthropic.com/en/docs/claude-code)
