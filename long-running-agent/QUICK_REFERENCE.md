# 快速参考卡片

## 使用 Claude（默认）

```bash
# config.json
{
  "agent": {
    "model_provider": "claude",
    "model": "opus"
  }
}

# 运行
python main.py --task "你的任务"
```

## 使用 Qoder

```bash
# config.qoder.json
python main.py --config config.qoder.json --task "你的任务"
```

## 使用 DeepSeek

```bash
# config.deepseek.json
{
  "agent": {
    "model_provider": "deepseek",
    "model": "deepseek-chat",
    "deepseek_config": {
      "api_key": "YOUR_DEEPSEEK_API_KEY"
    }
  }
}

# 运行
python main.py --config config.deepseek.json --task "你的任务"
```

## 使用 GLM（智谱）

```bash
# config.glm.json
python main.py --config config.glm.json --task "你的任务"
```

## 使用豆包

```bash
# config.doubao.json
python main.py --config config.doubao.json --task "你的任务"
```

## 切换模型

| 场景 | 配置 |
|------|------|
| 使用 Claude Opus | `"model_provider": "claude"`, `"model": "opus"` |
| 使用 Claude Sonnet | `"model_provider": "claude"`, `"model": "sonnet"` |
| 使用 Qwen Plus | `"model_provider": "qodercli"`, `"model": "qwen-plus"` |
| 使用 DeepSeek | `"model_provider": "deepseek"`, `"model": "deepseek-chat"` |
| 使用 GLM-4 Plus | `"model_provider": "glm"`, `"model": "glm-4-plus"` |
| 使用豆包 | `"model_provider": "doubao"`, `"model": "doubao-pro-32k"` |

## 常用命令

```bash
# 新任务
python main.py --task "任务描述"

# 查看进度
python main.py --status

# 生成报告
python main.py --report

# 从断点恢复
python main.py --resume

# 使用指定配置
python main.py --config config.qoder.json --task "任务"
```

## 测试命令

```bash
# 测试模型提供者
python test_model_provider.py

# 测试日志格式
python test_log_format.py
```

## 文件说明

| 文件 | 说明 |
|------|------|
| `model_provider.py` | 模型提供者抽象层（核心） |
| `config.json` | Claude 默认配置 |
| `config.qoder.json` | Qoder 配置示例 |
| `MODEL_PROVIDER_GUIDE.md` | 详细使用指南 |
| `REFACTORING_SUMMARY.md` | 重构总结 |

## 故障排除

| 问题 | 解决方案 |
|------|---------|
| `claude 命令未找到` | 安装 Claude Code CLI |
| `qoder 命令未找到` | 安装 Qoder CLI |
| `API Key 无效` | 检查 `qoder_config.api_key` |
| `连接超时` | 检查 `qoder_config.base_url` |
| `模型不支持` | 查看模型提供者文档 |

## 支持的模型

### Claude
- `opus` - 最强性能
- `sonnet` - 平衡
- `haiku` - 快速

### Qoder
- `qwen-plus` - 平衡
- `qwen-turbo` - 快速
- `qwen-max` - 最强

### DeepSeek
- `deepseek-chat` - 通用对话
- `deepseek-coder` - 代码专用

### GLM（智谱）
- `glm-4-plus` - 最强
- `glm-4-air` - 快速
- `glm-4-flash` - 超快

### 豆包
- `doubao-pro-32k` - 长文本
- `doubao-pro-4k` - 快速
- `doubao-lite-4k` - 轻量

## 扩展模型提供者

```python
# 1. 创建提供者类
class MyProvider(ModelProvider):
    def get_provider_name(self):
        return "my-provider"

    def invoke(self, prompt, max_turns, log_file):
        # 实现调用逻辑
        return {'success': True, 'output': '...', 'exit_code': 0}

# 2. 注册
providers = {
    'claude': ClaudeProvider,
    'qodercli': QoderCliProvider,
    'my-provider': MyProvider,  # 添加
}

# 3. 使用
{
  "agent": {
    "model_provider": "my-provider",
    "model": "model-name"
  }
}
```
