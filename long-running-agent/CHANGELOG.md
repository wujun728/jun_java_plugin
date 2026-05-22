# 变更日志

## [功能] 2026-04-02 - 新增 DeepSeek、GLM、豆包模型支持

### 概述
在多模型提供者架构基础上，新增了三个国内主流 AI 模型的支持：DeepSeek、GLM（智谱）和豆包（火山引擎）。

### 新增模型提供者

#### 1. DeepSeek API
- **实现类：** `DeepSeekProvider`
- **调用方式：** HTTP API（requests 库）
- **可用模型：** deepseek-chat, deepseek-coder
- **配置文件：** config.deepseek.json
- **API 文档：** https://platform.deepseek.com/

#### 2. GLM（智谱）API
- **实现类：** `GLMProvider`
- **调用方式：** HTTP API（requests 库）
- **可用模型：** glm-4-plus, glm-4-air, glm-4-flash
- **配置文件：** config.glm.json
- **API 文档：** https://open.bigmodel.cn/

#### 3. 豆包（火山引擎）API
- **实现类：** `DoubaoProvider`
- **调用方式：** HTTP API（requests 库）
- **可用模型：** doubao-pro-32k, doubao-pro-4k, doubao-lite-4k
- **配置文件：** config.doubao.json
- **API 文档：** https://console.volcengine.com/ark

### 修改的文件

1. **model_provider.py**（+200 行）
   - 新增 `DeepSeekProvider` 类
   - 新增 `GLMProvider` 类
   - 新增 `DoubaoProvider` 类
   - 工厂函数中注册新提供者

2. **config.py**（+3 行）
   - 新增 `deepseek_config` 配置
   - 新增 `glm_config` 配置
   - 新增 `doubao_config` 配置

3. **config.json**（+16 行）
   - 添加三个模型的配置模板

4. **test_model_provider.py**（+40 行）
   - 新增三个模型的测试用例

### 新增文件

1. **config.deepseek.json** - DeepSeek 配置示例
2. **config.glm.json** - GLM 配置示例
3. **config.doubao.json** - 豆包配置示例

### 配置示例

#### DeepSeek
```json
{
  "agent": {
    "model_provider": "deepseek",
    "model": "deepseek-chat",
    "deepseek_config": {
      "api_key": "YOUR_DEEPSEEK_API_KEY",
      "base_url": "https://api.deepseek.com/v1",
      "temperature": 0.7,
      "max_tokens": 8000
    }
  }
}
```

#### GLM（智谱）
```json
{
  "agent": {
    "model_provider": "glm",
    "model": "glm-4-plus",
    "glm_config": {
      "api_key": "YOUR_GLM_API_KEY",
      "base_url": "https://open.bigmodel.cn/api/paas/v4",
      "temperature": 0.7,
      "max_tokens": 8000
    }
  }
}
```

#### 豆包
```json
{
  "agent": {
    "model_provider": "doubao",
    "model": "doubao-pro-32k",
    "doubao_config": {
      "api_key": "YOUR_DOUBAO_API_KEY",
      "base_url": "https://ark.cn-beijing.volces.com/api/v3",
      "temperature": 0.7,
      "max_tokens": 8000
    }
  }
}
```

### 依赖要求

新增的三个模型提供者都依赖 `requests` 库：

```bash
pip install requests
```

### 使用方法

```bash
# 使用 DeepSeek
python main.py --config config.deepseek.json --task "你的任务"

# 使用 GLM
python main.py --config config.glm.json --task "你的任务"

# 使用豆包
python main.py --config config.doubao.json --task "你的任务"
```

### 测试结果

运行 `python test_model_provider.py`：

```
✓ DeepSeek 配置加载成功
✓ DeepSeek 提供者创建成功
✓ GLM 配置加载成功
✓ GLM 提供者创建成功
✓ 豆包配置加载成功
✓ 豆包提供者创建成功
```

### 特性对比

| 特性 | Claude | Qoder | DeepSeek | GLM | 豆包 |
|------|--------|-------|----------|-----|------|
| 调用方式 | CLI | CLI | API | API | API |
| 代码能力 | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ |
| 中文支持 | ⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ |
| 访问速度（国内） | ⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ |
| 性价比 | ⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ |
| 依赖 | CLI 安装 | CLI 安装 | requests | requests | requests |

### 模型推荐

- **代码密集型任务：** DeepSeek Coder, Claude Opus
- **中文任务：** GLM-4 Plus, 豆包 Pro
- **快速响应：** GLM-4 Flash, Claude Haiku
- **性价比优先：** DeepSeek Chat, GLM-4 Air

### 未来计划

1. 支持流式输出（Streaming）
2. 支持多模态输入（图片、文件）
3. 增加更多国内外模型
4. 优化 API 调用重试机制
5. 增加 token 消耗统计

## [功能] 2026-04-02 - 多模型提供者支持

### 概述
重构了模型调用架构，将 Claude CLI 调用抽象为可插拔的模型提供者接口，支持多种 AI 模型的无缝切换。新增了对 Qoder CLI 的支持。

### 架构变更

#### 新增文件

1. **`model_provider.py`** - 模型提供者抽象层
   - `ModelProvider` 抽象基类：定义统一的模型调用接口
   - `ClaudeProvider` 类：Claude Code CLI 实现
   - `QoderCliProvider` 类：Qoder CLI 实现
   - `create_model_provider()` 工厂函数：根据配置创建提供者实例

2. **`config.qoder.json`** - Qoder 配置示例
   - 完整的 Qoder CLI 配置模板
   - 包含 API Key、Base URL、Temperature 等参数

3. **`test_model_provider.py`** - 模型提供者测试脚本
   - 测试配置加载
   - 测试提供者创建
   - 测试提供者接口
   - 测试未知提供者降级

#### 修改文件

1. **`config.py`**
   - 新增 `model_provider` 字段（默认值：'claude'）
   - 新增 `qoder_config` 字段（Qoder 特定配置）
   - 从 `agent` 配置块中读取模型提供者设置

2. **`agent_runner.py`**
   - 重命名：从 "Claude CLI 调用封装" 改为 "AI 模型调用封装"
   - 移除原有的 `_run_claude()` 方法中的 subprocess 调用逻辑
   - 新增 `self.model_provider` 成员变量
   - `_run_claude()` 重命名为 `_run_model()`，委托给模型提供者
   - 所有三个智能体（Planner、Worker、Evaluator）统一使用 `_run_model()`

3. **`config.json`**
   - 新增 `model_provider` 字段
   - 新增 `qoder_config` 配置块（可选）

### 设计模式

#### 1. 抽象工厂模式
```python
# 工厂函数根据配置创建不同的提供者
provider = create_model_provider(config, logger)
```

#### 2. 策略模式
```python
# 不同的提供者实现相同的接口
class ModelProvider(ABC):
    @abstractmethod
    def invoke(self, prompt: str, max_turns: int, log_file: str) -> dict:
        pass
```

#### 3. 依赖注入
```python
# AgentRunner 不直接依赖具体的模型提供者
class AgentRunner:
    def __init__(self, config: Config, logger: ExecutionLogger):
        self.model_provider = create_model_provider(config, logger)
```

### 接口规范

#### ModelProvider 接口

```python
class ModelProvider(ABC):
    def __init__(self, config, logger: ExecutionLogger):
        self.config = config
        self.logger = logger
        self.work_directory = config.work_directory
        self.task_timeout_seconds = config.task_timeout_seconds

    @abstractmethod
    def invoke(self, prompt: str, max_turns: int, log_file: str) -> dict:
        """
        调用 AI 模型

        Returns:
            dict: {
                'success': bool,      # 是否成功
                'output': str,        # 模型输出
                'exit_code': int,     # 退出码
                'error': str          # 错误信息（可选）
            }
        """
        pass

    @abstractmethod
    def get_provider_name(self) -> str:
        """返回提供者名称"""
        pass
```

### ClaudeProvider 实现

从 `agent_runner.py` 中提取的原有 Claude CLI 调用逻辑：

**命令构建：**
```bash
claude -p --model opus --output-format json --dangerously-skip-permissions
```

**输出处理：**
1. 捕获 stdout 和 stderr
2. 保存到日志文件
3. 尝试解析 JSON 格式
4. 提取 `result` 字段

**错误处理：**
- `TimeoutExpired`: 超时
- `FileNotFoundError`: claude 命令未找到
- 其他异常：记录错误信息

### QoderCliProvider 实现

新增的 Qoder CLI 调用逻辑：

**命令构建：**
```bash
qoder --model qwen-plus --api-key KEY --base-url URL --temperature 0.7 --output-format json --max-turns 150
```

**配置参数：**
- `api_key`: API 密钥（必需）
- `base_url`: API 基础 URL（可选）
- `temperature`: 温度参数（默认 0.7）

**输出处理：**
1. 捕获 stdout 和 stderr
2. 保存到日志文件
3. 尝试解析 JSON 格式
4. 尝试多个字段名：`result`, `output`, `response`

**错误处理：**
- `TimeoutExpired`: 超时
- `FileNotFoundError`: qoder 命令未找到
- 其他异常：记录错误信息

### 配置示例

#### Claude 配置（默认）
```json
{
  "agent": {
    "model_provider": "claude",
    "model": "opus"
  }
}
```

#### Qoder 配置
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

### 扩展性

#### 添加新的模型提供者

1. **创建提供者类**（继承 `ModelProvider`）：
```python
class MyModelProvider(ModelProvider):
    def get_provider_name(self) -> str:
        return "my-model"

    def invoke(self, prompt: str, max_turns: int, log_file: str) -> dict:
        # 实现你的模型调用逻辑
        pass
```

2. **注册到工厂函数**（在 `model_provider.py` 中）：
```python
providers = {
    'claude': ClaudeProvider,
    'qodercli': QoderCliProvider,
    'my-model': MyModelProvider,  # 添加这行
}
```

3. **更新配置文件**：
```json
{
  "agent": {
    "model_provider": "my-model",
    "model": "your-model-name"
  }
}
```

### 向后兼容性

- 如果配置文件中没有 `model_provider` 字段，默认使用 `claude`
- 如果指定了未知的 `model_provider`，自动降级为 `claude` 并记录警告
- 所有现有的 config.json 文件无需修改即可继续工作

### 测试结果

运行 `test_model_provider.py`：

```
测试配置加载
  ✓ 默认配置正确加载 model_provider: claude
  ✓ Qoder 配置正确加载 model_provider: qodercli

测试模型提供者创建
  ✓ Claude 提供者正确创建（ClaudeProvider）
  ✓ Qoder 提供者正确创建（QoderCliProvider）

测试模型提供者接口
  ✓ 提供者包含 invoke 方法
  ✓ 提供者包含 get_provider_name 方法

测试未知提供者
  ✓ 未知提供者正确降级为 ClaudeProvider
```

### 性能影响

- 抽象层的性能开销可忽略不计（纳秒级）
- 实际性能取决于具体的模型提供者实现
- 不影响任务执行的整体性能

### 未来改进建议

1. 增加对更多模型的支持（OpenAI、Azure OpenAI、本地模型等）
2. 支持流式输出（Streaming）
3. 支持模型切换策略（失败时自动切换到备用模型）
4. 增加模型调用的重试机制
5. 支持多模型并行执行（竞速模式）
6. 增加模型调用的 token 消耗统计

## [增强] 2026-04-02 - 任务执行日志输出改进

### 概述
改进了长时运行智能体框架的日志输出功能，为每个任务的执行过程增加详细的时间跟踪和统计信息。

### 修改的文件

#### 1. `main.py`
**修改位置：** 第 167-275 行（任务执行循环）

**新增功能：**
- 在任务开始时记录并输出开始时间
- 在任务结束时计算并输出结束时间和任务耗时
- 在任务执行失败时也输出时间信息

**输出格式：**
```
============================================================
  会话 #1 | 任务 T001: 修复 JdbcUtil SQL 注入漏洞
  尝试: 1/3
  剩余: 32 个任务
============================================================

  开始时间: 2026-04-02 15:48:26
  ...（任务执行过程）...
  结束时间: 2026-04-02 15:51:15
  任务耗时: 169.00秒
  + 任务 T001 通过 (得分: 90)
```

**修改位置：** 第 59-106 行（format_report 函数）

**新增功能：**
- 在最终报告的已完成任务列表中显示：
  - 任务状态（通过/未通过）
  - 任务得分（0-100）
  - 任务耗时（秒）

**输出格式：**
```
  已完成的任务:
    + [T001] 修复 JdbcUtil SQL 注入漏洞
      状态: 通过 | 得分: 90 | 耗时: 169.00秒
```

#### 2. `task_manager.py`
**修改位置：** 第 155-204 行（summary 函数）

**新增功能：**
- 在进度摘要后增加"已完成任务详情"部分
- 为每个已完成的任务显示：
  - 任务 ID 和标题
  - 得分（0-100）
  - 耗时（秒）
- 使用 ✓ 符号标记已通过的任务

**输出格式：**
```
  已完成任务详情:
    ✓ [T001] 修复 JdbcUtil SQL 注入漏洞
      得分: 90/100 | 耗时: 169.00秒
```

**修改位置：** 第 182-198 行（generate_report 函数）

**新增功能：**
- 在返回的任务数据中增加时间和评估相关字段：
  - `started_at`: 任务开始时间（ISO 格式）
  - `completed_at`: 任务完成时间（ISO 格式）
  - `evaluation`: 完整的评估结果对象（包含得分、反馈等）

### 技术细节

#### 时间格式
- **记录格式：** ISO 8601 格式（`YYYY-MM-DDTHH:MM:SS`）
- **显示格式：** `YYYY-MM-DD HH:MM:SS`
- **耗时计算：** 使用 `datetime` 模块计算秒数差，保留两位小数

#### 兼容性处理
- 如果任务没有 `started_at` 或 `completed_at` 字段，耗时显示为"未知"
- 处理时间解析异常（ValueError、TypeError），确保不会因时间问题导致程序崩溃
- 支持 Windows 控制台的 UTF-8 编码

#### 数据来源
- **开始时间：** 在 `TaskManager.update_status()` 中记录（设置 status 为 'in_progress' 时）
- **结束时间：** 在 `TaskManager.mark_passed()` 中记录（设置 status 为 'passed' 时）
- **得分：** 从 `task['evaluation']['score']` 中获取
- **状态：** 从 `task['status']` 中获取

### 测试验证

创建了测试脚本 `test_log_format.py`，验证了：
1. **TaskManager.summary()** 正确显示已完成任务的详细信息
2. **format_report()** 正确显示任务状态、得分和耗时
3. 时间计算逻辑正确（支持 ISO 格式的时间字符串）
4. Windows 环境下的 UTF-8 编码支持

### 使用场景

#### 1. 实时监控任务执行
在任务执行过程中，可以看到每个任务的开始时间和结束时间，方便监控任务进度：
```bash
python main.py --resume
```

#### 2. 查看当前进度和已完成任务的详细信息
```bash
python main.py --status
```
输出包含所有已完成任务的得分和耗时信息。

#### 3. 生成最终报告
```bash
python main.py --report
```
生成包含详细统计信息的最终报告，包括每个任务的状态、得分和耗时。

### 性能影响
- 时间计算和格式化的性能开销极小（微秒级）
- 不影响任务执行的整体性能
- 日志输出量略有增加，但仍然在可控范围内

### 向后兼容性
- 完全向后兼容，旧的 `task_list.json` 文件仍然可以正常工作
- 如果旧任务没有 `started_at`、`completed_at` 或 `evaluation` 字段，会优雅地降级处理
- 不需要修改现有的配置文件或提示词

### 未来改进建议
1. 增加任务平均耗时统计
2. 增加最慢任务和最快任务的识别
3. 按耗时或得分对任务进行排序
4. 增加任务耗时的可视化图表（如柱状图）
5. 支持导出详细的 CSV 或 Excel 报告
