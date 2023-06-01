package com.doumuxie.utils;

public class ResultUtil {

    private Boolean success;

    private String msg;

    private Object data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResultUtil success(){
        ResultUtil resultUtil = new ResultUtil();
        resultUtil.setSuccess(true);
        return resultUtil;
    }

    public static ResultUtil success(Object data){
        ResultUtil resultUtil = new ResultUtil();
        resultUtil.setSuccess(true);
        resultUtil.setData(data);
        return resultUtil;
    }

    public static ResultUtil error(String msg){
        ResultUtil resultUtil = new ResultUtil();
        resultUtil.setSuccess(false);
        resultUtil.setMsg(msg);
        return resultUtil;
    }
}
