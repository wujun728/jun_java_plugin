package com.jun.base.io.netty.rpc;

public class MessageOutput {
    private String requestId;
    private String type;
    private Object payload;

    public MessageOutput(String requestId, String type, Object payload) {
        this.requestId = requestId;
        this.type = type;
        this.payload = payload;
    }

    public String getType() {
        return this.type;
    }

    public String getRequestId() {
        return requestId;
    }

    public Object getPayload() {
        return payload;
    }
}
