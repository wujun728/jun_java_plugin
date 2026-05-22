# 多模型提供者使用指南

本指南介绍如何在长时运行智能体框架中使用不同的 AI 模型提供者。

## 支持的模型提供者

### 1. Claude Code CLI（默认）

**优势：**
- 官方 Anthropic 模型，质量最高
- 代码理解和生成能力强
- 工具使用（Tool Use）能力强大

**前置条件：**
```bash
# 确认 claude 命令可用
claude --version
```

**配置示例：**
```json
{
  "agent": {
    "model_provider": "claude",
    "model": "opus"
  }
}
```

**可用模型：**
- `opus` - Claude Opus（最强性能，适合复杂任务）
- `sonnet` - Claude Sonnet（平衡性能和成本）
- `haiku` - Claude Haiku（快速响应）

### 2. Qoder CLI

**优势：**
- 支持国内 AI 模型（如通义千问等）
- 可配置 API Key 和 Base URL
- 支持自定义模型参数

**前置条件：**
```bash
# 确认 qoder 命令可用
qoder --version
```

**配置示例：**
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

**可用模型：**
取决于 Qoder CLI 支持的模型，常见的有：
- `qwen-plus` - 通义千问 Plus
- `qwen-turbo` - 通义千问 Turbo
- `qwen-max` - 通义千问 Max

### 3. DeepSeek API

**优势：**
- 国内访问速度快
- 代码能力强
- 性价比高

**前置条件：**
```bash
# 安装 requests 库
pip install requests
```

**配置示例：**
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

**可用模型：**
- `deepseek-chat` - DeepSeek Chat（通用对话）
- `deepseek-coder` - DeepSeek Coder（代码专用）

**获取 API Key：** https://platform.deepseek.com/

### 4. GLM（智谱）API

**优势：**
- 国内访问稳定
- 中文理解能力强
- 支持多种场景

**前置条件：**
```bash
# 安装 requests 库
pip install requests
```

**配置示例：**
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

**可用模型：**
- `glm-4-plus` - GLM-4 Plus（最强性能）
- `glm-4-air` - GLM-4 Air（快速响应）
- `glm-4-flash` - GLM-4 Flash（超快速）

**获取 API Key：** https://open.bigmodel.cn/

### 5. 豆包（火山引擎）API

**优势：**
- 字节跳动出品
- 多模态能力
- 企业级服务

**前置条件：**
```bash
# 安装 requests 库
pip install requests
```

**配置示例：**
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

**可用模型：**
- `doubao-pro-32k` - 豆包 Pro 32K（长文本）
- `doubao-pro-4k` - 豆包 Pro 4K（快速）
- `doubao-lite-4k` - 豆包 Lite（轻量）

**获取 API Key：** https://console.volcengine.com/ark

## 快速开始

### 方式 1：使用不同的配置文件

```bash
# 使用 Claude
python main.py --config config.json --task "你的任务"

# 使用 Qoder
python main.py --config config.qoder.json --task "你的任务"
```

### 方式 2：修改默认配置

编辑 `config.json`：
```json
{
  "agent": {
    "model_provider": "qodercli",  // 改为你想用的提供者
    "model": "qwen-plus"            // 改为对应的模型
  }
}
```

然后运行：
```bash
python main.py --task "你的任务"
```

## 配置详解

### Claude 配置

```json
{
  "agent": {
    "model_provider": "claude",
    "model": "opus",
    "planner_max_turns": 200,
    "worker_max_turns": 150,
    "evaluator_max_turns": 50,
    "task_timeout_seconds": 600
  }
}
```

**参数说明：**
- `model_provider`: 固定为 "claude"
- `model`: claude 支持的模型名称
- 其他参数与模型提供者无关

### Qoder 配置

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

**参数说明：**
- `model_provider`: 固定为 "qodercli" 或 "qoder"
- `model`: qoder 支持的模型名称
- `qoder_config.api_key`: API 密钥（必需）
- `qoder_config.base_url`: API 基础 URL（可选）
- `qoder_config.temperature`: 温度参数，控制输出随机性（0-1，默认 0.7）

## 模型选择建议

### 任务复杂度

| 任务类型 | 推荐模型 | 原因 |
|---------|---------|------|
| 大型重构 | Claude Opus | 需要深度理解代码架构 |
| Bug 修复 | Claude Sonnet / Qwen Plus | 平衡性能和成本 |
| 简单任务 | Claude Haiku / Qwen Turbo | 快速响应 |
| 测试编写 | Claude Sonnet | 代码生成质量好 |

### 成本考虑

1. **预算充足：** 全程使用 Claude Opus
2. **成本优化：**
   - Planner: Claude Opus（需要全局理解）
   - Worker: Claude Sonnet（实际编码）
   - Evaluator: Claude Haiku（简单验证）

**注意：** 目前框架的三个智能体（Planner/Worker/Evaluator）使用相同的模型配置。如需不同智能体使用不同模型，可扩展配置系统。

## 故障排除

### Claude 相关问题

**问题 1：`claude 命令未找到`**
```bash
# 解决方案：安装 Claude Code CLI
# 参考：https://claude.ai/code
```

**问题 2：`Claude CLI 超时`**
```json
// 增加超时时间
{
  "agent": {
    "task_timeout_seconds": 1200  // 从 600 增加到 1200
  }
}
```

### Qoder 相关问题

**问题 1：`qoder 命令未找到`**
```bash
# 解决方案：安装 Qoder CLI
pip install qoder-cli  # 或者其他安装方式
```

**问题 2：`API Key 无效`**
```json
// 检查配置
{
  "agent": {
    "qoder_config": {
      "api_key": "YOUR_ACTUAL_API_KEY"  // 确保是有效的 API Key
    }
  }
}
```

**问题 3：`连接超时`**
```json
// 检查 base_url 是否正确
{
  "agent": {
    "qoder_config": {
      "base_url": "https://api.qoder.example.com/v1"  // 确保 URL 正确
    }
  }
}
```

### 通用问题

**问题：输出格式不兼容**

不同的模型提供者可能返回不同格式的 JSON。框架会尝试多种字段名：
- `result`
- `output`
- `response`

如果仍然无法解析，检查日志文件（`logs/` 目录）查看原始输出。

## 日志和调试

### 查看日志

每次模型调用都会生成日志文件：
```
logs/
├── planner.log              # 规划器日志
├── worker-T001-s1.log       # Worker 日志（任务 ID + 会话号）
└── eval-T001.log            # Evaluator 日志
```

### 启用详细日志

框架会自动记录模型提供者的选择：
```
[2026-04-02T18:49:16] info {"message": "使用模型提供者: claude"}
```

### 调试模型调用

如果模型调用失败，检查：
1. 命令行工具是否安装（`claude --version` 或 `qoder --version`）
2. 配置文件是否正确
3. 日志文件中的错误信息
4. API Key 是否有效（仅 Qoder）

## 性能比较

### Claude 各模型对比

| 模型 | 速度 | 质量 | 成本 | 适用场景 |
|------|------|------|------|---------|
| Opus | 中等 | 最高 | 最高 | 复杂任务、大型项目 |
| Sonnet | 快 | 高 | 中等 | 日常开发、中等任务 |
| Haiku | 最快 | 中等 | 最低 | 简单任务、快速验证 |

### Qoder 模型对比

| 模型 | 特点 |
|------|------|
| qwen-plus | 平衡性能和成本 |
| qwen-turbo | 快速响应 |
| qwen-max | 最高质量 |

## 扩展：添加新的模型提供者

### 步骤 1：创建提供者类

在 `model_provider.py` 中：
```python
class MyModelProvider(ModelProvider):
    def get_provider_name(self) -> str:
        return "my-model"

    def invoke(self, prompt: str, max_turns: int, log_file: str) -> dict:
        # 实现模型调用逻辑
        cmd = ['my-model-cli', '--prompt', prompt]
        # ... subprocess 调用
        return {
            'success': True,
            'output': output_text,
            'exit_code': 0,
        }
```

### 步骤 2：注册提供者

在 `create_model_provider()` 函数中：
```python
providers = {
    'claude': ClaudeProvider,
    'qodercli': QoderCliProvider,
    'my-model': MyModelProvider,  # 添加这行
}
```

### 步骤 3：创建配置

创建 `config.mymodel.json`：
```json
{
  "agent": {
    "model_provider": "my-model",
    "model": "my-model-v1"
  }
}
```

### 步骤 4：测试

```bash
python main.py --config config.mymodel.json --task "测试任务"
```

## 最佳实践

1. **配置管理：** 为不同的模型提供者创建单独的配置文件
2. **成本控制：** 根据任务复杂度选择合适的模型
3. **日志检查：** 定期检查日志文件，确保模型正常工作
4. **备用方案：** 如果某个模型不可用，快速切换到备用模型
5. **测试验证：** 在正式使用前，用小任务测试模型效果

## 常见问题

**Q: 可以为不同的智能体（Planner/Worker/Evaluator）使用不同的模型吗？**

A: 目前不支持，但可以通过扩展配置系统实现。需要修改 `config.py` 和 `agent_runner.py`。

**Q: 如何知道某个模型是否适合我的任务？**

A: 建议先用小任务测试，观察输出质量和速度，然后再决定。

**Q: Qoder CLI 支持哪些模型？**

A: 取决于 Qoder CLI 的实现和配置的 API 服务。查看 Qoder CLI 文档获取支持的模型列表。

**Q: 可以使用本地模型吗？**

A: 可以，需要创建新的模型提供者。参考"扩展：添加新的模型提供者"部分。

## 参考资源

- [Claude Code CLI 文档](https://claude.ai/code)
- [Anthropic API 文档](https://docs.anthropic.com)
- [Qoder CLI 文档](https://github.com/qoder-cli)（示例链接）
- [框架源码](https://github.com/your-repo/long-running-agent)
