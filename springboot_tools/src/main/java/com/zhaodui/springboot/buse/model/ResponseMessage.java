package com.zhaodui.springboot.buse.model;


/**
 * restful 接口响应返回结构
 * @param <T>
 */
public class ResponseMessage<T> {

    private String respCode;
    private String respMsg;
    private T data;
    private boolean ok;
    private Long total;

    public ResponseMessage() {
    }

    public ResponseMessage(ResponseMessageCodeEnum codeEnum, String message) {
        this.respCode = codeEnum.getCode();
        this.respMsg = message;
    }
    
    public ResponseMessage(ResponseMessageCodeEnum codeEnum, String message, boolean ok, T data,long total) {
        this.respCode = codeEnum.getCode();
        this.respMsg = message;
        this.ok = ok;
        this.data = data;
        this.total = total;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getMessage() {
        return respMsg;
    }

    public void setMessage(String message) {
        this.respMsg = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isOk() {
		return ok;
	}


    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
