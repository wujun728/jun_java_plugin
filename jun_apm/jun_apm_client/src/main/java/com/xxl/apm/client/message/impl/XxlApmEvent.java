package com.xxl.apm.client.message.impl;

import com.xxl.apm.client.message.XxlApmMsg;

import java.util.Map;

/**
 * event msg, like "pv、uv、qps、suc rate"
 *
 * @author xuxueli 2018-12-29 16:55:14
 */
public class XxlApmEvent extends XxlApmMsg {

    public static final String SUCCESS_STATUS = "success";
    public static final String ERROR_STATUS = "error";


    private String type;                        // like "URL"
    private String name;                        // like "/user/add"

    private String status;                      // "success" means success, other error
    private Map<String, String> param;          // like "ip=xxx"


    public XxlApmEvent() {
    }
    public XxlApmEvent(String type, String name) {
        this.type = type;
        this.name = name;
    }
    public XxlApmEvent(String type, String name, String status, Map<String, String> param) {
        this.type = type;
        this.name = name;
        this.status = status;
        this.param = param;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }


    // tool
    @Override
    public void complete() {
        super.complete();

        // etc
        if (this.status == null) {
            this.status = SUCCESS_STATUS;
        }
    }

    public void setStatus(Throwable cause) {
        String causeName = (cause.getCause()!=null?cause.getCause():cause).getClass().getName();
        this.status = causeName;
    }

}