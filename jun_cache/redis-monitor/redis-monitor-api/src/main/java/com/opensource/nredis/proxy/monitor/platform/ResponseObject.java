package com.opensource.nredis.proxy.monitor.platform;

import java.io.Serializable;


/**
 * Created by songs on 21.01.2016.
 */
public class ResponseObject implements Serializable {

    private static final long serialVersionUID = -7057101792135081848L;

    private String tcost;

    private int status;

    private String message;

    private Object data;

    public ResponseObject() {

    }

    public ResponseObject(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getTcost() {
        return tcost;
    }

    public void setTcost(String tcost) {
        this.tcost = tcost;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
