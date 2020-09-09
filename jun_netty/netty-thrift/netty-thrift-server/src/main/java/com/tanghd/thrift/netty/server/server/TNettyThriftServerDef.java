package com.tanghd.thrift.netty.server.server;

import org.apache.thrift.server.TServerEventHandler;

import com.tanghd.thrift.netty.common.message.TNettyThriftDef;

public class TNettyThriftServerDef extends TNettyThriftDef {
    private TServerEventHandler eventHandler;
    private int maxReadBufferLength;

    public TServerEventHandler getEventHandler() {
        return eventHandler;
    }

    public int getMaxReadBufferLength() {
        return maxReadBufferLength;
    }

    public void setEventHandler(TServerEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void setMaxReadBufferLength(int maxReadBufferLength) {
        this.maxReadBufferLength = maxReadBufferLength;
    }

}
