package cn.kiwipeach.response;

import cn.kiwipeach.enums.ResponseCodeEnum;

/**
 * Create Date: 2017/11/06
 * Description: 控制器统一返回实体类
 *
 * @author Wujun
 */
public class DataResponse<T> {
    private boolean isSuccess;
    private T data;
    private String code;
    private String message;

    /**
     * 成功构造函数
     * @param data 返回数据
     */
    public DataResponse(T data) {
        this(true,data);
    }

    /**
     * 成功构造
     * @param isSuccess 成功标志
     * @param data 返回数据
     */
    public DataResponse(boolean isSuccess, T data) {
        this.isSuccess = isSuccess;
        this.data = data;
        this.code = "10000";
        this.message = "请求成功";
    }

    public DataResponse(boolean isSuccess, ResponseCodeEnum codeEnum) {
        this.isSuccess = isSuccess;
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
