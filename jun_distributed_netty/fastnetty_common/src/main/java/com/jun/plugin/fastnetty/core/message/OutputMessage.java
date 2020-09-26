package com.jun.plugin.fastnetty.core.message;

/**
 * @author peiyu
 */
public interface OutputMessage extends FastNettyMessage {
    byte[] toBytes();
}
