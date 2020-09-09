package com.github.sd4324530.fastrpc.core.message;

/**
 * RPC响应对象
 *
 * @author peiyu
 */
public class ResponseMessage implements IMessage {

    private String seq;

    private ResultCode resultCode;

    private String errorMessage;

    private Object responseObject;

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }

    @Override
    public void setSeq(String seq) {
        this.seq = seq;
    }

    @Override
    public String getSeq() {
        return this.seq;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "seq='" + seq + '\'' +
                ", resultCode=" + resultCode +
                ", errorMessage='" + errorMessage + '\'' +
                ", responseObject=" + responseObject +
                '}';
    }
}
