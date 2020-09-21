package org.supermy.rocksdb;

/**
 * Created by moyong on 17/5/20.
 */
import java.io.Serializable;


public class CommonJson<T> implements Serializable {

    public CommonJson(Boolean success,String msg, T data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    /**
     *
     */
    private static final long serialVersionUID = -3440061414071692254L;

    /**
     * 是否成功
     */
    private Boolean success;

    private String msg;

    /**
     * 数据
     */
    private T data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}