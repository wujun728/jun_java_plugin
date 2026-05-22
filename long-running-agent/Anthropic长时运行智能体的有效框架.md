# Anthropic 长时运行智能体的有效框架 — 设计方案与实现

> 基于 Anthropic 官方研究，为 Claude Code 构建的长时运行任务编排框架。
> 支持 Java SpringBoot / Vue.js / 全栈项目，跨多个上下文窗口持续推进复杂任务。

---

## 一、问题背景

### 1.1 长时运行智能体面临的核心挑战

AI 智能体在处理需要数小时甚至数天才能完成的复杂任务时，面临以下挑战：

| 挑战 | 描述 |
|------|------|
| **上下文断裂** | 每个新会话对之前发生的事情一无所知，就像轮班制工程师没有交接记录 |
| **贪多嚼不烂** | 智能体倾向于一次做太多事情，导致耗尽上下文后留下半成品 |
| **过早宣布完成** | 看到已有一些进展就宣布项目完成，忽略未实现的功能 |
| **环境损坏** | 遗留带有错误或无文档记录的进展，下一个会话需要大量时间修复 |

### 1.2 Anthropic 的解决思路

Anthropic 借鉴人类高效工程师的工作方式，提出了**双智能体架构**：

```
┌─────────────────────────────────────────────────────────────┐
│                      主控制循环 (while true)                  │
│                                                               │
│  ┌──────────────┐     ┌──────────────────────────────────┐  │
│  │  Init Agent   │ ──→ │        Worker Agent (循环)         │  │
│  │  (首次运行)    │     │  Session#2 → #3 → #4 → ... → #N  │  │
│  └──────────────┘     └──────────────────────────────────┘  │
│         │                            │                        │
│         ▼                            ▼                        │
│  ┌──────────────┐     ┌──────────────────────────────────┐  │
│  │ 创建脚手架:    │     │ 每次会话:                          │  │
│  │ • feature_list│     │ 1. 读取进度+Git日志                │  │
│  │ • progress.txt│     │ 2. 验证环境健康                    │  │
│  │ • init.sh     │     │ 3. 选择一个功能                    │  │
│  │ • Git初始提交  │     │ 4. 实现 → 测试 → 提交              │  │
│  └──────────────┘     │ 5. 更新进度文件                    │  │
│                        └──────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

---

## 二、框架架构设计

### 2.1 总体架构

```
long-running-agent/
├── run.sh                          # 主控制循环 (Linux/Mac)
├── run.bat                         # 主控制循环 (Windows)
├── config.json                     # 项目配置文件
│
├── prompts/
│   ├── init-agent.md               # 初始化智能体提示词模板
│   └── worker-agent.md             # 工作智能体提示词模板
│
├── templates/
│   └── feature_list_template.json  # 功能列表模板
│
├── utils/
│   ├── render_prompt.py            # 提示词模板渲染工具
│   └── check_progress.py          # 进度检查工具
│
└── examples/
    ├── java-springboot/config.json # Java SpringBoot 项目示例
    ├── vue-frontend/config.json    # Vue.js 前端项目示例
    └── fullstack/config.json       # 全栈项目示例
```

### 2.2 三层架构

```
┌─────────────────────────────────────────────┐
│           编排层 (run.sh / run.bat)           │  ← 主循环控制
├─────────────────────────────────────────────┤
│       智能体层 (init-agent / worker-agent)    │  ← Claude Code 执行
├─────────────────────────────────────────────┤
│         数据层 (JSON + Git + Progress)        │  ← 状态持久化
└─────────────────────────────────────────────┘
```

**编排层**：Shell 脚本中的 `while true` 循环，负责反复调用 Claude Code CLI，检查退出条件。

**智能体层**：通过精心设计的提示词驱动 Claude 的行为。Init Agent 创建脚手架，Worker Agent 每次完成一个功能。

**数据层**：`feature_list.json` 跟踪功能状态，`claude-progress.txt` 记录会话历史，`Git` 保持代码版本和回滚能力。

### 2.3 四大失败模式及对应解决方案

| # | 失败模式 | Init Agent 行为 | Worker Agent 行为 | 关键机制 |
|---|---------|----------------|-------------------|---------|
| 1 | 过早宣布项目完成 | 创建细粒度 `feature_list.json`，所有功能标记 `passes:false` | 读取功能列表，只要有 `passes:false` 就继续工作 | **功能列表驱动** |
| 2 | 遗留损坏环境 | 创建 Git 仓库和进度文件 | 会话开始时验证环境健康，结束时 Git 提交 | **Git + 进度文件** |
| 3 | 过早标记功能完成 | 设置详细验证步骤 | 逐步验证后才改 `passes:true`，禁止修改其他字段 | **严格的更新规则** |
| 4 | 不知如何运行项目 | 编写 `init.sh` 脚本 | 读取 `init.sh` 快速了解运行方式 | **初始化脚本** |

---

## 三、核心组件详解

### 3.1 config.json — 项目配置文件

每个项目一份配置，告诉框架项目的基本信息和工具链：

```json
{
  "project_name": "my-project",
  "project_type": "java-springboot",
  "project_description": "项目描述",
  "work_directory": ".",
  "user_requirements": "完整的需求描述...",

  "agent": {
    "model": "opus",
    "max_turns": 200,
    "max_sessions": 50,
    "allowed_tools": "Bash,Read,Write,Edit,Glob,Grep"
  },

  "paths": {
    "feature_list": "feature_list.json",
    "progress_file": "claude-progress.txt",
    "init_script": "init.sh"
  },

  "java": {
    "build_command": "mvn clean compile -DskipTests",
    "test_command": "mvn test",
    "run_command": "mvn spring-boot:run"
  },

  "frontend": {
    "build_command": "npm run build",
    "test_command": "npm run test:unit",
    "dev_command": "npm run serve"
  }
}
```

### 3.2 feature_list.json — 功能列表（核心数据结构）

这是整个框架最核心的数据结构，也是 Anthropic 方案的关键创新：

```json
[
  {
    "id": "F001",
    "category": "setup",
    "description": "项目基础结构搭建",
    "steps": [
      "验证项目目录结构正确",
      "验证 pom.xml 存在且可解析",
      "运行 mvn compile 确认无错误"
    ],
    "passes": false,
    "priority": 1,
    "dependencies": []
  },
  {
    "id": "F002",
    "category": "database",
    "description": "用户表设计与 Entity 类",
    "steps": [
      "验证建表SQL可执行",
      "验证 UserEntity 类字段与表对应",
      "验证 UserMapper 基础 CRUD 可工作"
    ],
    "passes": false,
    "priority": 2,
    "dependencies": ["F001"]
  }
]
```

**设计要点：**

- **JSON 格式**：比 Markdown 更不容易被模型误修改
- **passes 字段**：唯一允许 Worker Agent 修改的字段
- **steps 字段**：提供明确的验证标准，防止过早标记完成
- **dependencies 字段**：确保功能按正确顺序实现
- **priority 字段**：控制实现顺序（1=最高优先级）

### 3.3 run.sh — 主控制循环

主控制循环是一个 `while true`，负责：

```
开始
 │
 ▼
检查 feature_list.json 是否存在？
 │
 ├── 否 → 运行 Init Agent → 创建脚手架
 │
 ├── 是 → 检查是否所有 passes == true？
 │          │
 │          ├── 是 → 结束，所有功能已完成
 │          │
 │          └── 否 → 检查是否达到最大会话数？
 │                    │
 │                    ├── 是 → 结束，报告剩余功能数
 │                    │
 │                    └── 否 → 运行 Worker Agent
 │                              │
 │                              └── sleep 5s → 回到循环开始
 ▼
```

核心代码逻辑（简化）：

```bash
# 阶段一：初始化
if [ ! -f "feature_list.json" ]; then
    claude -p "$INIT_PROMPT" --model opus --max-turns 200
fi

# 阶段二：工作循环
while true; do
    pending=$(count_pending_features)

    if [ "$pending" -eq 0 ]; then
        echo "所有功能已完成！"
        break
    fi

    if [ "$session" -gt "$MAX_SESSIONS" ]; then
        echo "达到最大会话数限制"
        break
    fi

    claude -p "$WORKER_PROMPT" --model opus --max-turns 200

    session=$((session + 1))
    sleep 5
done
```

每次循环创建一个**全新的 Claude 会话**。这是 Anthropic 方案的关键——不是尝试在一个超长会话中完成所有工作，而是通过**外部持久化状态**（feature_list.json, claude-progress.txt, Git）在多个短会话间桥接上下文。

### 3.4 Init Agent 提示词设计

Init Agent 只运行一次，负责创建三件事：

**1) feature_list.json**
- 将用户的高层需求拆解为 30-80 个细粒度功能
- 每个功能独立可验证
- 所有 passes 设为 false

**2) init.sh**
- 检查前置条件（Java、Maven、Node 等）
- 安装依赖、编译项目
- 运行冒烟测试
- 输出环境摘要

**3) claude-progress.txt + Git 初始提交**
- 创建进度跟踪文件
- 初始提交记录项目起点

### 3.5 Worker Agent 提示词设计

Worker Agent 每个会话遵循严格的 10 步流程：

```
STEP 1: 了解状态 → 读 progress.txt + git log
STEP 2: 验证环境 → 编译检查，先修后建
STEP 3: 阅读功能列表 → 了解已完成/待实现
STEP 4: 选择一个功能 → 优先级最高 + 依赖已满足
STEP 5: 实现功能 → 编写代码
STEP 6: 验证功能 → 按 steps 逐条验证
STEP 7: 更新 feature_list.json → 仅修改 passes 字段
STEP 8: Git 提交 → 描述性提交信息
STEP 9: 更新进度文件 → 追加会话记录
STEP 10: 最终验证 → 确认环境干净
```

**关键约束（直接写在提示词中）：**

> "你只能修改一个字段: passes"
> "删除或编辑测试是不可接受的，因为这可能导致功能缺失或存在缺陷"
> "一次只实现一个功能。完美地完成一个功能，远比潦草地处理多个功能更有价值。"

---

## 四、使用指南

### 4.1 快速开始

**前提条件：**
- 已安装 Claude Code CLI（`claude` 命令可用）
- 已安装 Python 3（用于 JSON 处理和模板渲染）
- 已安装 Git
- 已安装项目对应的构建工具（Java: Maven/Gradle, 前端: Node/npm）

**Step 1: 复制框架到项目目录**

```bash
cp -r long-running-agent/ /path/to/your/project/.agent/
cd /path/to/your/project/
```

**Step 2: 编辑配置文件**

```bash
# 编辑 .agent/config.json
# 修改以下字段：
#   project_name: 你的项目名称
#   project_type: java-springboot / vue-frontend / fullstack
#   user_requirements: 你的完整需求描述
#   work_directory: 项目根目录路径
```

**Step 3: 启动框架**

```bash
# Linux / Mac
chmod +x .agent/run.sh
.agent/run.sh

# Windows
.agent\run.bat
```

**Step 4: 观察执行**

框架会自动：
1. 首先运行 Init Agent 创建 `feature_list.json`
2. 然后循环运行 Worker Agent，每次完成一个功能
3. 直到所有功能完成或达到最大会话数

**Step 5: 查看进度**

```bash
# 查看进度报告
python .agent/utils/check_progress.py feature_list.json

# 查看进度日志
cat claude-progress.txt

# 查看 Git 历史
git log --oneline
```

### 4.2 Java SpringBoot 项目示例

```json
{
  "project_name": "user-management-system",
  "project_type": "java-springboot",
  "user_requirements": "构建一个用户管理系统：\n1. 用户注册和登录（JWT认证）\n2. 用户信息CRUD\n3. 角色权限管理\n4. 操作日志记录\n5. RESTful API\n6. Swagger 文档",

  "java": {
    "build_command": "mvn clean compile -DskipTests",
    "test_command": "mvn test",
    "run_command": "mvn spring-boot:run"
  }
}
```

Init Agent 可能生成的功能列表（节选）：

| ID | 类别 | 描述 | 优先级 |
|----|------|------|--------|
| F001 | setup | Maven 项目结构和 pom.xml 依赖 | 1 |
| F002 | setup | application.yml 基础配置 | 1 |
| F003 | database | 用户表设计与 UserEntity | 2 |
| F004 | database | 角色表设计与 RoleEntity | 2 |
| F005 | functional | UserService 基础 CRUD | 2 |
| F006 | functional | RoleService 基础 CRUD | 2 |
| F007 | security | JWT Token 生成与验证 | 3 |
| F008 | security | 登录认证接口 | 3 |
| F009 | security | 权限拦截器 | 3 |
| F010 | api | UserController REST 接口 | 3 |
| F011 | api | RoleController REST 接口 | 3 |
| F012 | api | 统一响应封装 | 2 |
| F013 | api | 全局异常处理 | 2 |
| F014 | functional | 操作日志 AOP 实现 | 4 |
| F015 | api | Swagger/Knife4j 配置 | 4 |
| F016 | testing | UserService 单元测试 | 5 |
| F017 | testing | API 集成测试 | 5 |

### 4.3 中途恢复

如果框架运行中被中断（断网、关机等），可以安全恢复：

```bash
# 使用 --resume 跳过初始化，直接继续工作循环
.agent/run.sh --resume

# Windows
.agent\run.bat --resume
```

框架会自动从 `feature_list.json` 中找到下一个待实现的功能继续工作。

### 4.4 调整最大会话数

```bash
.agent/run.sh --max-sessions 100
```

---

## 五、设计原理深入解析

### 5.1 为什么是 JSON 而不是 Markdown？

Anthropic 实验发现，使用 Markdown 格式的功能列表时，模型经常会不恰当地修改或覆盖内容。JSON 格式的结构化特性使得：
- 模型更不容易意外删除或重写条目
- `passes` 字段的改动在 diff 中清晰可见
- 程序化检查更方便（`jq` / Python 直接解析）

### 5.2 为什么一次只做一个功能？

这是 Anthropic 方案中"渐进式进展的小步快跑"策略：

```
❌ 不好的方式:
Session 1: 尝试一次实现所有功能 → 上下文耗尽 → 半成品

✅ 好的方式:
Session 1: 完成 F001 (项目搭建) → 提交 → 干净状态
Session 2: 完成 F002 (配置) → 提交 → 干净状态
Session 3: 完成 F003 (数据库) → 提交 → 干净状态
...
```

每个会话结束时，代码库处于**可编译、已提交、有文档**的干净状态。即使下一个会话完全不知道之前发生了什么，也能通过 Git 历史和进度文件快速了解。

### 5.3 为什么需要"先修后建"？

Worker Agent 提示词中强调：

> "如果环境有问题，先修复问题，再考虑新功能。在损坏的基础上添加新功能只会让情况更糟。"

这模拟了资深工程师的习惯——上班第一件事不是写新代码，而是 `git pull && mvn compile` 确认环境正常。

### 5.4 Git 的双重作用

Git 在框架中同时扮演两个角色：

1. **状态恢复**：如果某次会话搞坏了代码，可以 `git reset --hard` 回到上次的干净状态
2. **上下文传递**：`git log --oneline -10` 让新会话快速了解最近做了什么

### 5.5 为什么每次创建新会话而非复用？

这是一个反直觉的设计。直觉上，保持一个长会话更高效。但实际上：

- 长会话的上下文压缩会丢失关键信息
- 新会话从外部文件重建上下文更可靠
- 避免了上下文污染和幻觉累积
- `feature_list.json` + `claude-progress.txt` + `git log` 比压缩后的内存更准确

---

## 六、高级用法

### 6.1 自定义提示词

修改 `prompts/worker-agent.md`，可以针对特定项目添加约束：

```markdown
### 项目特定约束
- 所有 REST API 必须使用 /api/v1 前缀
- 数据库操作使用 MyBatis-Plus 而非原生 SQL
- 前端组件使用 Element-UI
- 禁止使用 Lombok
```

### 6.2 多智能体扩展

当前框架使用两类智能体（Init + Worker）。可以扩展为更多专门的智能体：

```
long-running-agent/prompts/
├── init-agent.md            # 初始化
├── worker-agent.md          # 功能实现
├── test-agent.md            # 专门的测试智能体
├── review-agent.md          # 代码审查智能体
└── cleanup-agent.md         # 代码清理智能体
```

在 `run.sh` 中添加对应的调度逻辑：

```bash
# 每实现 3 个功能后，运行一次代码审查
if [ $((features_done % 3)) -eq 0 ]; then
    claude -p "$REVIEW_PROMPT" --model opus --max-turns 50
fi
```

### 6.3 与 CI/CD 集成

框架的 Git 提交天然适合与 CI/CD 流水线集成：

```yaml
# .github/workflows/agent-ci.yml
on:
  push:
    branches: ['agent/*']
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Build
        run: mvn clean package
      - name: Test
        run: mvn test
```

---

## 七、完整代码清单

### 7.1 run.sh — 主控制循环（Linux/Mac）

```bash
#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
CONFIG_FILE="${SCRIPT_DIR}/config.json"
RESUME=false
MAX_SESSIONS=50
SESSION_COUNT=0

# 参数解析
while [[ $# -gt 0 ]]; do
    case $1 in
        --config)       CONFIG_FILE="$2";  shift 2 ;;
        --resume)       RESUME=true;       shift   ;;
        --max-sessions) MAX_SESSIONS="$2"; shift 2 ;;
        *) shift ;;
    esac
done

log() { echo "[$(date '+%Y-%m-%d %H:%M:%S')] $*"; }

# 从 config.json 读取字段
read_config() {
    python3 -c "
import json
with open('$CONFIG_FILE','r',encoding='utf-8') as f:
    d = json.load(f)
keys = '$1'.strip('.').split('.')
v = d
for k in keys:
    v = v[k]
print(v)
"
}

# 统计未完成功能数
count_pending() {
    python3 -c "
import json
with open('$WORK_DIR/$FEATURE_LIST','r',encoding='utf-8') as f:
    data = json.load(f)
print(len([x for x in data if not x.get('passes', False)]))
" 2>/dev/null || echo "-1"
}

# 读取配置
PROJECT_NAME=$(read_config '.project_name')
WORK_DIR=$(read_config '.work_directory')
FEATURE_LIST=$(read_config '.paths.feature_list')
MODEL=$(read_config '.agent.model')
MAX_TURNS=$(read_config '.agent.max_turns')
TOOLS=$(read_config '.agent.allowed_tools')
[[ "$WORK_DIR" == "." ]] && WORK_DIR="$(pwd)"

log "=========================================="
log " 项目: $PROJECT_NAME | 模型: $MODEL"
log "=========================================="

# 阶段一: 初始化
if [[ "$RESUME" == false && ! -f "$WORK_DIR/$FEATURE_LIST" ]]; then
    log "--- 运行初始化智能体 ---"
    PROMPT=$(python3 "$SCRIPT_DIR/utils/render_prompt.py" \
        "$SCRIPT_DIR/prompts/init-agent.md" "$CONFIG_FILE")
    cd "$WORK_DIR"
    claude -p "$PROMPT" --model "$MODEL" --max-turns "$MAX_TURNS" \
        --allowedTools "$TOOLS" 2>&1 | tee init-agent.log
    SESSION_COUNT=1
fi

# 阶段二: 工作循环
while true; do
    SESSION_COUNT=$((SESSION_COUNT + 1))
    pending=$(count_pending)

    [[ "$pending" == "0" ]] && { log "全部完成！"; break; }
    [[ $SESSION_COUNT -gt $MAX_SESSIONS ]] && { log "达到上限"; break; }

    log "=== 会话 #$SESSION_COUNT (剩余 $pending) ==="
    PROMPT=$(python3 "$SCRIPT_DIR/utils/render_prompt.py" \
        "$SCRIPT_DIR/prompts/worker-agent.md" "$CONFIG_FILE" \
        --session "$SESSION_COUNT")
    cd "$WORK_DIR"
    claude -p "$PROMPT" --model "$MODEL" --max-turns "$MAX_TURNS" \
        --allowedTools "$TOOLS" 2>&1 | tee "worker-${SESSION_COUNT}.log"

    sleep 5
done
```

### 7.2 run.bat — 主控制循环（Windows）

```batch
@echo off
chcp 65001 >nul 2>&1
setlocal enabledelayedexpansion

set SCRIPT_DIR=%~dp0
set CONFIG_FILE=%SCRIPT_DIR%config.json
set RESUME=false
set MAX_SESSIONS=50
set SESSION_COUNT=0

REM 参数解析
:parse
if "%~1"=="" goto main
if "%~1"=="--config"       ( set CONFIG_FILE=%~2& shift& shift& goto parse )
if "%~1"=="--resume"       ( set RESUME=true& shift& goto parse )
if "%~1"=="--max-sessions" ( set MAX_SESSIONS=%~2& shift& shift& goto parse )
shift
goto parse

:main
for /f "delims=" %%i in ('python -c "import json; d=json.load(open(r'%CONFIG_FILE%',encoding='utf-8')); print(d['project_name'])"') do set PROJECT_NAME=%%i
for /f "delims=" %%i in ('python -c "import json; d=json.load(open(r'%CONFIG_FILE%',encoding='utf-8')); print(d['work_directory'])"') do set WORK_DIR=%%i
for /f "delims=" %%i in ('python -c "import json; d=json.load(open(r'%CONFIG_FILE%',encoding='utf-8')); print(d['paths']['feature_list'])"') do set FL=%%i
for /f "delims=" %%i in ('python -c "import json; d=json.load(open(r'%CONFIG_FILE%',encoding='utf-8')); print(d['agent']['model'])"') do set MODEL=%%i
for /f "delims=" %%i in ('python -c "import json; d=json.load(open(r'%CONFIG_FILE%',encoding='utf-8')); print(d['agent']['max_turns'])"') do set MT=%%i
for /f "delims=" %%i in ('python -c "import json; d=json.load(open(r'%CONFIG_FILE%',encoding='utf-8')); print(d['agent']['allowed_tools'])"') do set TOOLS=%%i

if "%WORK_DIR%"=="." set WORK_DIR=%CD%

echo [框架] 项目: %PROJECT_NAME% 模型: %MODEL%

REM 阶段一: 初始化
if "%RESUME%"=="true" goto worker
if exist "%WORK_DIR%\%FL%" goto worker

python "%SCRIPT_DIR%utils\render_prompt.py" "%SCRIPT_DIR%prompts\init-agent.md" "%CONFIG_FILE%" > "%TEMP%\init_p.txt"
cd /d "%WORK_DIR%"
claude -p "@%TEMP%\init_p.txt" --model %MODEL% --max-turns %MT% --allowedTools "%TOOLS%"
set SESSION_COUNT=1

:worker
REM 阶段二: 工作循环
set /a SESSION_COUNT+=1

for /f "delims=" %%i in ('python -c "import json; d=json.load(open(r'%WORK_DIR%\%FL%',encoding='utf-8')); print(len([x for x in d if not x.get('passes',False)]))"') do set PENDING=%%i

if "%PENDING%"=="0" ( echo [框架] 全部完成！& goto done )
if %SESSION_COUNT% gtr %MAX_SESSIONS% ( echo [框架] 达到上限& goto done )

echo [框架] === 会话 #%SESSION_COUNT% (剩余 %PENDING%) ===
python "%SCRIPT_DIR%utils\render_prompt.py" "%SCRIPT_DIR%prompts\worker-agent.md" "%CONFIG_FILE%" --session %SESSION_COUNT% > "%TEMP%\w_p.txt"
cd /d "%WORK_DIR%"
claude -p "@%TEMP%\w_p.txt" --model %MODEL% --max-turns %MT% --allowedTools "%TOOLS%"

timeout /t 5 /nobreak >nul
goto worker

:done
echo [框架] 运行结束。会话: %SESSION_COUNT%, 剩余: %PENDING%
endlocal
```

### 7.3 utils/render_prompt.py — 模板渲染

```python
#!/usr/bin/env python3
"""将 prompts/*.md 中的 {{变量}} 替换为 config.json 中的值"""
import json, sys, os

def render(template_path, config_path, extra_vars=None):
    with open(template_path, 'r', encoding='utf-8') as f:
        template = f.read()
    with open(config_path, 'r', encoding='utf-8') as f:
        config = json.load(f)

    var_map = {
        'PROJECT_NAME': config.get('project_name', ''),
        'PROJECT_TYPE': config.get('project_type', ''),
        'PROJECT_DESCRIPTION': config.get('project_description', ''),
        'WORK_DIRECTORY': os.path.abspath(config.get('work_directory', '.')),
        'USER_REQUIREMENTS': config.get('user_requirements', ''),
        'FEATURE_LIST': config.get('paths', {}).get('feature_list', 'feature_list.json'),
        'PROGRESS_FILE': config.get('paths', {}).get('progress_file', 'claude-progress.txt'),
        'INIT_SCRIPT': config.get('paths', {}).get('init_script', 'init.sh'),
        'BUILD_COMMAND': config.get('java', {}).get('build_command', ''),
        'TEST_COMMAND': config.get('java', {}).get('test_command', ''),
        'RUN_COMMAND': config.get('java', {}).get('run_command', ''),
    }
    if extra_vars:
        var_map.update(extra_vars)

    for key, value in var_map.items():
        template = template.replace('{{' + key + '}}', value)
    return template

if __name__ == '__main__':
    extra = {}
    i = 3
    while i < len(sys.argv):
        if sys.argv[i] == '--session' and i + 1 < len(sys.argv):
            extra['SESSION_NUMBER'] = sys.argv[i + 1]
            i += 2
        else:
            i += 1
    print(render(sys.argv[1], sys.argv[2], extra))
```

### 7.4 utils/check_progress.py — 进度检查

```python
#!/usr/bin/env python3
"""读取 feature_list.json 并输出进度报告"""
import json, sys, os

def check(path):
    with open(path, 'r', encoding='utf-8') as f:
        features = json.load(f)

    total = len(features)
    done = len([f for f in features if f.get('passes')])
    pct = (done / total * 100) if total else 0

    cats = {}
    for f in features:
        c = f.get('category', '?')
        cats.setdefault(c, [0, 0])
        cats[c][0] += 1
        if f.get('passes'):
            cats[c][1] += 1

    print(f"进度: {done}/{total} ({pct:.1f}%)")
    for c, (t, d) in sorted(cats.items()):
        print(f"  {c:<20} {d}/{t}")

    pending = [f for f in features if not f.get('passes')]
    if pending:
        pending.sort(key=lambda x: x.get('priority', 99))
        print(f"\n下一个: [{pending[0]['id']}] {pending[0]['description']}")

if __name__ == '__main__':
    check(sys.argv[1] if len(sys.argv) > 1 else 'feature_list.json')
```

---

## 八、工作流时序图

```
时间线 ────────────────────────────────────────────────────────►

 run.sh                Init Agent              Worker Agent #2           Worker Agent #3
   │                      │                        │                        │
   │ ── 启动 ──────────→ │                        │                        │
   │                      │ pwd, ls               │                        │
   │                      │ 分析需求               │                        │
   │                      │ 创建 feature_list.json │                        │
   │                      │ 创建 init.sh           │                        │
   │                      │ 创建 progress.txt      │                        │
   │                      │ git commit             │                        │
   │ ←── 完成 ─────────── │                        │                        │
   │                                               │                        │
   │ 检查: pending=30                              │                        │
   │ ── 启动 ───────────────────────────────────→ │                        │
   │                                               │ 读 progress.txt       │
   │                                               │ git log               │
   │                                               │ mvn compile (检查)    │
   │                                               │ 读 feature_list.json  │
   │                                               │ 选择 F001             │
   │                                               │ 实现代码...            │
   │                                               │ mvn compile (验证)    │
   │                                               │ 更新 passes:true      │
   │                                               │ git commit            │
   │                                               │ 更新 progress.txt     │
   │ ←── 完成 ──────────────────────────────────── │                        │
   │                                                                        │
   │ 检查: pending=29                                                       │
   │ sleep 5s                                                               │
   │ ── 启动 ────────────────────────────────────────────────────────────→ │
   │                                                                        │ 读 progress.txt
   │                                                                        │ git log
   │                                                                        │ 选择 F002
   │                                                                        │ 实现...
   │                                                                        │ 提交...
   │ ←── 完成 ─────────────────────────────────────────────────────────── │
   │ ...继续循环...
```

---

## 九、已知限制与未来方向

### 9.1 当前限制

1. **需要 Claude Code CLI**：框架依赖 `claude` 命令行工具
2. **API 限流**：高频调用可能触发速率限制（`sleep 5` 缓解）
3. **功能列表静态**：Init Agent 生成后不再调整，可能遗漏需求
4. **单一 Worker**：未实现专门的测试/审查/清理智能体

### 9.2 未来方向

1. **多智能体协作**：添加 Test Agent、Review Agent、Cleanup Agent
2. **动态功能列表**：允许在执行过程中追加新功能
3. **并行执行**：对无依赖的功能启动多个 Worker 并行处理
4. **Web Dashboard**：可视化进度看板
5. **领域扩展**：从软件开发扩展到科学研究、金融建模等场景

---

## 十、交付件清单

| 文件 | 路径 | 说明 |
|------|------|------|
| 主控制循环(Linux) | `long-running-agent/run.sh` | while true 编排脚本 |
| 主控制循环(Windows) | `long-running-agent/run.bat` | Windows 版本 |
| 项目配置 | `long-running-agent/config.json` | 项目级配置模板 |
| Init Agent 提示词 | `long-running-agent/prompts/init-agent.md` | 初始化智能体指令 |
| Worker Agent 提示词 | `long-running-agent/prompts/worker-agent.md` | 工作智能体指令 |
| 功能列表模板 | `long-running-agent/templates/feature_list_template.json` | JSON 模板 |
| 模板渲染工具 | `long-running-agent/utils/render_prompt.py` | 变量替换 |
| 进度检查工具 | `long-running-agent/utils/check_progress.py` | 进度报告 |
| Java 示例配置 | `long-running-agent/examples/java-springboot/config.json` | |
| Vue 示例配置 | `long-running-agent/examples/vue-frontend/config.json` | |
| 全栈示例配置 | `long-running-agent/examples/fullstack/config.json` | |
| 本文档 | `jun_dbapi/dbapi-ui/docs/Anthropic长时运行智能体的有效框架.md` | 完整方案 |

---

## 附录 A: 参考资料

- Anthropic 原文: "Effective frameworks for long-running agents"
- Claude Agent SDK 文档
- Claude Code CLI 官方指南
