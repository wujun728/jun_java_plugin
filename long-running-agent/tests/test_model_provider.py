#!/usr/bin/env python3
"""测试模型提供者 - 验证多模型支持"""
import sys
import os

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
from logger import ExecutionLogger
from model_provider import (
    create_model_provider, ClaudeProvider, QoderCliProvider,
    DeepSeekProvider, GLMProvider, DoubaoProvider
)


def test_config_loading():
    """测试配置加载"""
    print("=" * 60)
    print("测试配置加载")
    print("=" * 60)

    # 测试默认配置（Claude）
    config = Config.load('config.json')
    print(f"\n默认配置:")
    print(f"  模型提供者: {config.model_provider}")
    print(f"  模型: {config.model}")

    # 测试 Qoder 配置
    if os.path.exists('config.qoder.json'):
        config_qoder = Config.load('config.qoder.json')
        print(f"\nQoder 配置:")
        print(f"  模型提供者: {config_qoder.model_provider}")
        print(f"  模型: {config_qoder.model}")
        print(f"  Qoder API Key: {config_qoder.qoder_config.get('api_key', 'N/A')[:20]}...")
        print(f"  Qoder Base URL: {config_qoder.qoder_config.get('base_url', 'N/A')}")

    # 测试 DeepSeek 配置
    if os.path.exists('config.deepseek.json'):
        config_deepseek = Config.load('config.deepseek.json')
        print(f"\nDeepSeek 配置:")
        print(f"  模型提供者: {config_deepseek.model_provider}")
        print(f"  模型: {config_deepseek.model}")
        print(f"  DeepSeek API Key: {config_deepseek.deepseek_config.get('api_key', 'N/A')[:20]}...")
        print(f"  DeepSeek Base URL: {config_deepseek.deepseek_config.get('base_url', 'N/A')}")

    # 测试 GLM 配置
    if os.path.exists('config.glm.json'):
        config_glm = Config.load('config.glm.json')
        print(f"\nGLM 配置:")
        print(f"  模型提供者: {config_glm.model_provider}")
        print(f"  模型: {config_glm.model}")
        print(f"  GLM API Key: {config_glm.glm_config.get('api_key', 'N/A')[:20]}...")
        print(f"  GLM Base URL: {config_glm.glm_config.get('base_url', 'N/A')}")

    # 测试豆包配置
    if os.path.exists('config.doubao.json'):
        config_doubao = Config.load('config.doubao.json')
        print(f"\n豆包配置:")
        print(f"  模型提供者: {config_doubao.model_provider}")
        print(f"  模型: {config_doubao.model}")
        print(f"  豆包 API Key: {config_doubao.doubao_config.get('api_key', 'N/A')[:20]}...")
        print(f"  豆包 Base URL: {config_doubao.doubao_config.get('base_url', 'N/A')}")


def test_provider_creation():
    """测试模型提供者创建"""
    print("\n" + "=" * 60)
    print("测试模型提供者创建")
    print("=" * 60)

    logger = ExecutionLogger('test_execution_log.jsonl')

    # 测试 Claude 提供者
    config = Config.load('config.json')
    provider = create_model_provider(config, logger)
    print(f"\n默认提供者:")
    print(f"  类型: {type(provider).__name__}")
    print(f"  名称: {provider.get_provider_name()}")
    print(f"  是否为 ClaudeProvider: {isinstance(provider, ClaudeProvider)}")

    # 测试 Qoder 提供者
    if os.path.exists('config.qoder.json'):
        config_qoder = Config.load('config.qoder.json')
        provider_qoder = create_model_provider(config_qoder, logger)
        print(f"\nQoder 提供者:")
        print(f"  类型: {type(provider_qoder).__name__}")
        print(f"  名称: {provider_qoder.get_provider_name()}")
        print(f"  是否为 QoderCliProvider: {isinstance(provider_qoder, QoderCliProvider)}")

    # 测试 DeepSeek 提供者
    if os.path.exists('config.deepseek.json'):
        config_deepseek = Config.load('config.deepseek.json')
        provider_deepseek = create_model_provider(config_deepseek, logger)
        print(f"\nDeepSeek 提供者:")
        print(f"  类型: {type(provider_deepseek).__name__}")
        print(f"  名称: {provider_deepseek.get_provider_name()}")
        print(f"  是否为 DeepSeekProvider: {isinstance(provider_deepseek, DeepSeekProvider)}")

    # 测试 GLM 提供者
    if os.path.exists('config.glm.json'):
        config_glm = Config.load('config.glm.json')
        provider_glm = create_model_provider(config_glm, logger)
        print(f"\nGLM 提供者:")
        print(f"  类型: {type(provider_glm).__name__}")
        print(f"  名称: {provider_glm.get_provider_name()}")
        print(f"  是否为 GLMProvider: {isinstance(provider_glm, GLMProvider)}")

    # 测试豆包提供者
    if os.path.exists('config.doubao.json'):
        config_doubao = Config.load('config.doubao.json')
        provider_doubao = create_model_provider(config_doubao, logger)
        print(f"\n豆包提供者:")
        print(f"  类型: {type(provider_doubao).__name__}")
        print(f"  名称: {provider_doubao.get_provider_name()}")
        print(f"  是否为 DoubaoProvider: {isinstance(provider_doubao, DoubaoProvider)}")


def test_provider_interface():
    """测试模型提供者接口"""
    print("\n" + "=" * 60)
    print("测试模型提供者接口")
    print("=" * 60)

    logger = ExecutionLogger('test_execution_log.jsonl')
    config = Config.load('config.json')
    provider = create_model_provider(config, logger)

    print(f"\n提供者方法检查:")
    print(f"  是否有 invoke 方法: {hasattr(provider, 'invoke')}")
    print(f"  是否有 get_provider_name 方法: {hasattr(provider, 'get_provider_name')}")
    print(f"  invoke 是否可调用: {callable(getattr(provider, 'invoke', None))}")
    print(f"  get_provider_name 是否可调用: {callable(getattr(provider, 'get_provider_name', None))}")


def test_unknown_provider():
    """测试未知的模型提供者"""
    print("\n" + "=" * 60)
    print("测试未知的模型提供者（应降级为 Claude）")
    print("=" * 60)

    logger = ExecutionLogger('test_execution_log.jsonl')

    # 创建一个配置对象，模型提供者设为未知值
    config = Config.load('config.json')
    config.model_provider = 'unknown_provider'

    provider = create_model_provider(config, logger)
    print(f"\n未知提供者降级结果:")
    print(f"  类型: {type(provider).__name__}")
    print(f"  名称: {provider.get_provider_name()}")
    print(f"  是否降级为 ClaudeProvider: {isinstance(provider, ClaudeProvider)}")


if __name__ == '__main__':
    print("\n测试模型提供者架构\n")

    # 测试配置加载
    test_config_loading()
    print("\n")

    # 测试提供者创建
    test_provider_creation()
    print("\n")

    # 测试提供者接口
    test_provider_interface()
    print("\n")

    # 测试未知提供者
    test_unknown_provider()
    print("\n")

    print("=" * 60)
    print("所有测试完成！")
    print("=" * 60)

    # 清理测试文件
    if os.path.exists('test_execution_log.jsonl'):
        os.remove('test_execution_log.jsonl')
