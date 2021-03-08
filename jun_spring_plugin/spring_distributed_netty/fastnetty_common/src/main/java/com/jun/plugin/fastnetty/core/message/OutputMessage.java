package com.jun.plugin.fastnetty.core.message;

/**
 * @author Wujun
 */
public interface OutputMessage extends FastNettyMessage {
    byte[] toBytes();
}
