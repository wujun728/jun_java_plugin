package com.ruoyi.generate.pojo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 统一返回值
 *
 * @author alter
 */
@Data
@Accessors(chain = true)
public class Result {

    // 成功状态码
    public static final int SUCCESS_CODE = 200;

    // 请求失败状态码
    public static final int FAIL_CODE = 500;

    // 查无资源状态码
    public static final int NOTF_FOUNT_CODE = 404;

    // 无权访问状态码
    public static final int ACCESS_DINE_CODE = 403;

    public static Result build(int code, String msg) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static Result build(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    /**
     * 状态码
     */
    private int code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 数据信息
     */
    private Object data;

    /**
     * 请求成功
     *
     * @return
     */
    public static Result ok() {
        Result r = new Result();
        r.setCode(SUCCESS_CODE);
        r.setMsg("请求成功！");
        r.setData(null);
        return r;
    }

    /**
     * 请求失败
     *
     * @return
     */
    public static Result fail() {
        Result r = new Result();
        r.setCode(FAIL_CODE);
        r.setMsg("请求失败！");
        r.setData(null);
        return r;
    }

    /**
     * 请求成功，自定义信息
     *
     * @param msg
     * @return
     */
    public static Result ok(String msg) {
        Result r = new Result();
        r.setCode(SUCCESS_CODE);
        r.setMsg(msg);
        r.setData(null);
        return r;
    }

    /**
     * 请求失败，自定义信息
     *
     * @param msg
     * @return
     */
    public static Result fail(String msg) {
        Result r = new Result();
        r.setCode(FAIL_CODE);
        r.setMsg(msg);
        r.setData(null);
        return r;
    }

    /**
     * 请求成功，自定义信息，自定义数据
     *
     * @param msg
     * @return
     */
    public static Result ok(String msg, Object data) {
        Result r = new Result();
        r.setCode(SUCCESS_CODE);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    /**
     * 请求失败，自定义信息，自定义数据
     *
     * @param msg
     * @return
     */
    public static Result fail(String msg, Object data) {
        Result r = new Result();
        r.setCode(FAIL_CODE);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }


    public Result data(Object data) {
        this.setData(data);
        return this;
    }

    public Result msg(String msg) {
        this.setMsg(msg);
        return this;
    }

    /**
     * 自定义状态码返回给前端
     *
     * @param code 错误码
     * @param msg  错误信息
     * @return Result
     */
    public static Result fail(int code, String msg) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(null);
        return r;
    }

    public boolean isError() {
        return code != SUCCESS_CODE;
    }
}
