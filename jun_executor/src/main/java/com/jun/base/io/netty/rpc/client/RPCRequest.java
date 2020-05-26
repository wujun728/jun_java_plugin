package com.jun.base.io.netty.rpc.client;

public class RPCRequest {
    private String requestId;
    private String type;
    private Object payload;

    public RPCRequest(String requestId, String type, Object payload) {
        this.requestId = requestId;
        this.type = type;
        this.payload = payload;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getType() {
        return type;
    }

    public Object getPayload() {
        return payload;
    }
}
