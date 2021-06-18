package com.wangxin.common.util;

import com.alibaba.fastjson.JSON;

/**
 * @Description: 客户端响应jsonModel
 * @author Wujun
 * @date 2017年4月26日 下午3:24:28
 * @version
 */
public class JsonResponseModel {

    /**
     * 成功
     */
    public static final String SUCCESS = "1";

    /**
     * 失败
     */
    public static final String FAIL = "0";

    /**
     * 给客户端响应成功还是失败 success fail
     */
    private String status;

    /**
     * 消息体,操作成功提示文本,或者操作失败提示文本
     */
    private String msg;

    /***
     * 数据
     */
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String Success(String msg, Object data) {
        this.setMsg(msg);
        this.setStatus(SUCCESS);
        this.setData(data);
        return JSON.toJSONString(this);
    }

    public String Fail(String msg) {
        this.setMsg(msg);
        this.setStatus(FAIL);
        return JSON.toJSONString(this);
    }

}
