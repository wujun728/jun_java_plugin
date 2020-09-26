package com.jun.plugin.fastnetty.core.message;

/**
 * @author peiyu
 */
public interface InputMessage extends FastNettyMessage {
    InputMessage fromBytes(byte[] bytes);
}
