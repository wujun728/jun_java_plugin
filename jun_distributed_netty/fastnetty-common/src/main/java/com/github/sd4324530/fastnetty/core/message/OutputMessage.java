package com.github.sd4324530.fastnetty.core.message;

/**
 * @author peiyu
 */
public interface OutputMessage extends FastNettyMessage {
    byte[] toBytes();
}
