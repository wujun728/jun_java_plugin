package com.github.sd4324530.fastnetty.core.message;

/**
 * @author peiyu
 */
public interface InputMessage extends FastNettyMessage {
    InputMessage fromBytes(byte[] bytes);
}
