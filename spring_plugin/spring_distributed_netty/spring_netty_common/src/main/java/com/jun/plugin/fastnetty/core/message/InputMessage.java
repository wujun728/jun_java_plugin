package com.jun.plugin.fastnetty.core.message;

/**
 * @author Wujun
 */
public interface InputMessage extends FastNettyMessage {
    InputMessage fromBytes(byte[] bytes);
}
