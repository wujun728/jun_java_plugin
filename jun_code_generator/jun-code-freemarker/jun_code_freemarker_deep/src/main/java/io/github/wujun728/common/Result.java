package io.github.wujun728.common;

public class Result {
    private int code;
    private String message;
    private Object data;

    public Result() {
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result ok() {
        return new Result(200, "success");
    }

    public static Result ok(Object data) {
        return new Result(200, "success", data);
    }

    public static Result error() {
        return new Result(500, "error");
    }

    public static Result error(String message) {
        return new Result(500, message);
    }

    public static Result error(int code, String message) {
        return new Result(code, message);
    }

    // getter and setter methods
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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