# 长时运行智能体框架 v2

基于 Anthropic 三智能体架构（Planner + Worker + Evaluator），让 AI 智能体能够自动拆分、执行和验证大型任务。

**✨ v2.1 新特性：多模型提供者支持**
- 支持 Claude Code CLI（默认）
- 支持 Qoder CLI（新增）
- 可插拔架构，易于扩展
- 配置文件切换，无需修改代码

## 架构

```
用户输入任务 → Planner(拆分) → Worker(逐个执行) → Evaluator(独立验证) → 完成
                                    ↑                       │
                                    └───── 失败时重试 ──────┘
```

**三个智能体角色：**

| 角色 | 职责 | 提示词 |
|------|------|--------|
| **Planner** | 分析任务，生成细粒度子任务清单 | `prompts/planner.md` |
| **Worker** | 每次完成一个子任务，提交代码 | `prompts/worker.md` |
| **Evaluator** | 独立验证 Worker 的成果质量 | `prompts/evaluator.md` |

## 前置条件

- Python 3.8+
- Claude Code CLI（`claude` 命令可用）
- Git

## 快速开始

### 1. 新任务

```bash
cd /path/to/your/project
python /path/to/long-running-agent/main.py --task "构建一个用户管理系统，包含JWT认证和CRUD接口"
```

### 2. 查看进度

```bash
python main.py --status
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

### 3. 中断后恢复

```bash
# Ctrl+C 中断后，随时恢复
python main.py --resume
```

### 4. 生成报告

```bash
python main.py --report
```

## 配置

编辑 `config.json`：

```json
{
  "project_name": "my-project",
  "project_type": "java-springboot",
  "user_task": "你的任务描述...",
  "agent": {
    "model_provider": "claude",
    "model": "opus",
    "max_sessions": 50,
    "qoder_config": {
      "api_key": "",
      "base_url": "",
      "temperature": 0.7
    }
  },
  "retry": {
    "max_attempts": 3
  },
  "build": {
    "compile_command": "mvn clean compile -DskipTests",
    "test_command": "mvn test"
  }
}
```

### 配置说明

| 字段 | 说明 | 默认值 |
|------|------|--------|
| `project_name` | 项目名称 | `my-project` |
| `project_type` | 项目类型 (java-springboot, vue-frontend, fullstack) | `java-springboot` |
| `user_task` | 任务描述（也可通过 --task 参数指定） | - |
| `agent.model_provider` | 模型提供者 (claude, qodercli) | `claude` |
| `agent.model` | 模型名称（claude: opus/sonnet/haiku; qoder: qwen-plus等） | `opus` |
| `agent.planner_max_turns` | 规划器最大轮数 | `200` |
| `agent.worker_max_turns` | 执行器最大轮数 | `150` |
| `agent.evaluator_max_turns` | 评估器最大轮数 | `50` |
| `agent.max_sessions` | 最大会话数 | `50` |
| `agent.cooldown_seconds` | 会话间冷却时间 | `5` |
| `agent.task_timeout_seconds` | 单次 CLI 调用超时 | `600` |
| `agent.qoder_config.api_key` | Qoder API 密钥（仅 qodercli） | - |
| `agent.qoder_config.base_url` | Qoder API 基础 URL（仅 qodercli） | - |
| `agent.qoder_config.temperature` | 温度参数（仅 qodercli） | `0.7` |
| `retry.max_attempts` | 失败任务最大重试次数 | `3` |
| `build.compile_command` | 编译命令 | - |
| `build.test_command` | 测试命令 | - |

## 多模型支持

框架支持多种 AI 模型提供者，通过 `model_provider` 配置切换：

### 支持的模型提供者

#### 1. Claude (默认)
```json
{
  "agent": {
    "model_provider": "claude",
    "model": "opus"
  }
}
```

**可用模型：**
- `opus` - Claude Opus（最强性能）
- `sonnet` - Claude Sonnet（平衡性能和成本）
- `haiku` - Claude Haiku（快速响应）

**前置条件：** 安装 Claude Code CLI (`claude` 命令可用)

#### 2. Qoder CLI
```json
{
  "agent": {
    "model_provider": "qodercli",
    "model": "qwen-plus",
    "qoder_config": {
      "api_key": "YOUR_API_KEY",
      "base_url": "https://api.qoder.example.com/v1",
      "temperature": 0.7
    }
  }
}
```

**可用模型：** 取决于 Qoder CLI 支持的模型（如 qwen-plus, qwen-turbo 等）

**前置条件：** 安装 Qoder CLI (`qoder` 命令可用)

### 切换模型提供者

方式 1：修改配置文件
```bash
# 使用 Claude
python main.py --config config.json --task "你的任务"

# 使用 Qoder
python main.py --config config.qoder.json --task "你的任务"
```

方式 2：直接编辑 config.json
```json
{
  "agent": {
    "model_provider": "qodercli",  // 改为 qodercli
    "model": "qwen-plus"            // 改为对应的模型
  }
}
```

### 添加新的模型提供者

1. 在 `model_provider.py` 中创建新的提供者类：
```python
class YourModelProvider(ModelProvider):
    def get_provider_name(self) -> str:
        return "your-provider"

    def invoke(self, prompt: str, max_turns: int, log_file: str) -> dict:
        # 实现你的模型调用逻辑
        pass
```

2. 在 `create_model_provider()` 函数中注册：
```python
providers = {
    'claude': ClaudeProvider,
    'qodercli': QoderCliProvider,
    'your-provider': YourModelProvider,  # 添加这行
}
```

## 执行流程

```
1. Planner 将任务拆分为 30-80 个子任务 → task_list.json
2. 主循环：
   a. 选下一个 pending 任务（按优先级，依赖已满足）
   b. Worker 执行任务，Git 提交
   c. Evaluator 独立验证质量
   d. 通过 → 标记 passed
   e. 失败 → 记录原因，Worker 重试（最多 N 次）
   f. 超过重试上限 → 标记 skipped，继续下一个
3. 所有任务完成或达到会话上限 → 输出最终报告
```

## 任务状态

```
pending → in_progress → evaluating → passed
                                   → failed → in_progress (重试)
                                            → skipped (超过重试上限)
```

## 生成的文件

| 文件 | 说明 |
|------|------|
| `task_list.json` | 任务清单和状态（核心持久化文件） |
| `claude-progress.txt` | 人可读的进度记录 |
| `execution_log.jsonl` | 结构化执行日志（每行一条 JSON） |
| `logs/` | 每次智能体调用的完整输出 |
| `init.sh` | 环境初始化脚本 |

## 命令行参数

| 参数 | 说明 |
|------|------|
| `--task "描述"` | 指定任务描述 |
| `--resume` | 从断点恢复执行 |
| `--config file.json` | 指定配置文件 |
| `--status` | 查看当前进度 |
| `--report` | 生成最终报告 |
| `--max-sessions N` | 覆盖最大会话数 |

## 框架文件结构

```
long-running-agent/
├── main.py                    # 入口 + 主编排循环
├── config.py                  # 配置加载
├── task_manager.py            # 任务生命周期管理
├── agent_runner.py            # AI 模型调用封装
├── model_provider.py          # 模型提供者接口（支持多模型）
├── logger.py                  # 结构化日志
├── prompt_renderer.py         # 提示词模板渲染
├── config.json                # 默认配置（Claude）
├── config.qoder.json          # Qoder 配置示例
├── prompts/
│   ├── planner.md             # 规划器提示词
│   ├── worker.md              # 执行器提示词
│   └── evaluator.md           # 评估器提示词
├── examples/
│   ├── java-springboot.json
│   ├── vue-frontend.json
│   └── fullstack.json
└── test_*.py                  # 测试脚本
```

## 设计原理

### 为什么三个智能体？

- **Planner** 解决"贪多嚼不烂"：将大任务拆分为小步骤
- **Worker** 解决"上下文断裂"：每次会话从持久化文件重建上下文
- **Evaluator** 解决"自我放水"：独立验证避免 Worker 对自己的工作过于宽容

### 为什么每次创建新会话？

长会话的上下文压缩会丢失关键信息。新会话从外部文件（task_list.json + claude-progress.txt + git log）重建上下文更可靠。

### 为什么用 JSON 格式的任务清单？

Anthropic 实验发现 JSON 比 Markdown 更不容易被模型误修改或覆盖。结构化数据也更方便程序化检查。

### 为什么一次只做一个任务？

"小步快跑"策略。每个会话结束时代码处于干净、可编译、已提交的状态。即使下个会话完全丢失上下文，也能通过 Git 历史快速恢复。

## 参考

- [Anthropic: Effective frameworks for long-running agents](https://www.anthropic.com/engineering/effective-frameworks-for-long-running-agents)
- [Anthropic: Harness design for long-running apps](https://www.anthropic.com/engineering/harness-design-long-running-apps)

## 修改记录

### 2026-04-02: 多模型提供者支持

**修改内容：**

1. **抽取模型调用接口** (新建 model_provider.py)
   - 创建 `ModelProvider` 抽象基类
   - 定义统一的 `invoke()` 接口
   - 支持可插拔的模型提供者架构

2. **实现 Claude 提供者** (ClaudeProvider)
   - 从 agent_runner.py 中提取 Claude CLI 调用逻辑
   - 支持 opus、sonnet、haiku 等模型
   - 保持与原有功能完全兼容

3. **实现 Qoder CLI 提供者** (QoderCliProvider)
   - 新增对 Qoder CLI 的支持
   - 支持配置 API Key、Base URL、Temperature
   - 支持 qwen-plus、qwen-turbo 等模型

4. **配置文件增强** (config.py, config.json)
   - 新增 `model_provider` 配置项（默认为 'claude'）
   - 新增 `qoder_config` 配置项（用于 Qoder 特定配置）
   - 创建 config.qoder.json 示例配置

5. **重构 agent_runner.py**
   - 使用工厂模式创建模型提供者
   - 将 `_run_claude()` 改为 `_run_model()`
   - 通过统一接口调用不同的模型提供者

**测试验证：**

创建了测试脚本 `test_model_provider.py`，验证了：
- 配置文件正确加载模型提供者设置
- 工厂函数正确创建对应的提供者实例
- 提供者接口完整可用
- 未知提供者正确降级为 Claude

**使用方法：**

使用 Claude（默认）：
```bash
python main.py --task "你的任务"
```

使用 Qoder：
```bash
python main.py --config config.qoder.json --task "你的任务"
```

或修改 config.json：
```json
{
  "agent": {
    "model_provider": "qodercli",
    "model": "qwen-plus"
  }
}
```

**扩展性：**

添加新的模型提供者只需：
1. 继承 `ModelProvider` 基类
2. 实现 `invoke()` 和 `get_provider_name()` 方法
3. 在工厂函数中注册

### 2026-04-02: 增强任务执行日志输出

**修改内容：**

1. **任务执行时增加时间信息** (main.py)
   - 在任务开始时记录并输出开始时间
   - 在任务结束时计算并输出结束时间和任务耗时
   - 时间格式：`%Y-%m-%d %H:%M:%S`
   - 耗时单位：秒（保留两位小数）

2. **已完成任务列表增强** (main.py - format_report 函数)
   - 显示任务状态（通过/未通过）
   - 显示任务得分（从评估结果中获取）
   - 显示任务耗时（基于 started_at 和 completed_at 字段计算）
   - 格式示例：
     ```
     + [T001] 修复 JdbcUtil SQL 注入漏洞
       状态: 通过 | 得分: 90 | 耗时: 169.00秒
     ```

3. **任务摘要信息增强** (task_manager.py - summary 函数)
   - 在进度摘要后增加"已完成任务详情"部分
   - 为每个已完成的任务显示：
     - 任务标题（截取前 50 个字符）
     - 得分（0-100）
     - 耗时（秒）
   - 使用 ✓ 符号标记已通过的任务

4. **报告数据结构增强** (task_manager.py - generate_report 函数)
   - 在返回的任务数据中增加：
     - `started_at`: 任务开始时间
     - `completed_at`: 任务完成时间
     - `evaluation`: 完整的评估结果对象

**测试验证：**

创建了测试脚本 `test_log_format.py`，验证了：
- TaskManager.summary() 正确显示已完成任务的详细信息
- format_report() 正确显示任务状态、得分和耗时
- 时间计算逻辑正确（支持 ISO 格式的时间字符串）
- Windows 环境下的 UTF-8 编码支持

**输出示例：**

执行任务时的输出：
```
============================================================
  会话 #1 | 任务 T001: 修复 JdbcUtil SQL 注入漏洞
  尝试: 1/3
  剩余: 32 个任务
============================================================

  开始时间: 2026-04-02 15:48:26
  ...
  结束时间: 2026-04-02 15:51:15
  任务耗时: 169.00秒
  + 任务 T001 通过 (得分: 90)
```

进度摘要输出：
```
进度: 33/33 (100.0%)
  已完成: 33  失败/跳过: 0  待处理: 0
  ...
  已完成任务详情:
    ✓ [T001] 修复 JdbcUtil SQL 注入漏洞
      得分: 90/100 | 耗时: 169.00秒
```

### 2026-04-03: Token 消耗优化 — 降低 95% 费用

**问题：** 框架默认配置下跑一次任务动辄几十上百美金，主要原因：
1. 默认使用 Opus（最贵模型），单价是 Sonnet 的 5 倍
2. Planner 建议生成 30-80 个任务，每个都触发 Worker + Evaluator 两次调用
3. max_turns 过大（Planner 200, Worker 150, Evaluator 50）
4. Worker 和 Evaluator 每次都重复执行大量探索命令（pwd、git log、编译等）
5. 没有任何费用监控和预算控制

**修改内容：**

1. **config.json — 调整默认参数**
   - `model`: `opus` → `sonnet`（降低 80% 单价）
   - `planner_max_turns`: `200` → `50`
   - `worker_max_turns`: `150` → `30`
   - `evaluator_max_turns`: `50` → `15`
   - `max_sessions`: `50` → `20`
   - 新增 `budget_max_dollars: 10.0`（预算上限）
   - 新增 `planner_model/worker_model/evaluator_model`（分层模型支持）

2. **planner.md — 减少任务粒度**
   - "30-80 个任务" → "10-60 个任务"
   - 新增提示：合并相关的小步骤为一个任务

3. **worker.md — 精简重复操作**
   - 移除每次都执行的 pwd、git log、读 progress file 等探索命令
   - 移除重复的"更新进度文件"步骤和"最终编译验证"（编译通过后不再重复）
   - 新增提示：编译成功不要复述完整输出

4. **evaluator.md — 精简重复操作**
   - 移除 pwd、git log、git diff 等不必要的前置探索
   - 直接进入验证步骤

5. **config.py — 分层模型 + 预算参数**
   - 新增 `planner_model`、`worker_model`、`evaluator_model` 配置
   - 新增 `budget_max_dollars` 配置
   - 默认模型从 `opus` 改为 `sonnet`

6. **model_provider.py — 支持分层模型**
   - `invoke()` 方法新增 `model_override` 参数
   - 返回值新增 `usage` 字段（token 用量）

7. **agent_runner.py — 传递分层模型**
   - Planner/Worker/Evaluator 各自使用对应的模型配置

8. **main.py — 预算熔断机制**
   - 新增 `BudgetTracker` 类：追踪 token 用量和累计费用
   - 内置 Claude 各模型定价表
   - 每次调用后打印费用统计
   - 超出预算自动停止执行
   - 最终报告包含费用汇总

**预估效果：**

| 场景 | 40 个任务费用 |
|------|-------------|
| 优化前 (Opus, 40 任务) | ~$93 |
| 优化后 (Sonnet, 15 任务) | ~$4.5 |
| 降幅 | **95%** |
