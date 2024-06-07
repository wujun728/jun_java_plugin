package io.github.wujun728.common;

import io.github.wujun728.common.exception.code.BaseResponseCode;

import java.util.HashMap;

/**
 * 返回对象
 *
 * @author wujun
 */
public class Result extends HashMap<String, Object>
{
    private static final long serialVersionUID = 1L;

    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    public static final String MSG_TAG = "msg";

    /** 数据对象 */
    public static final String DATA_TAG = "data";

    private Boolean success = true;

    public Boolean getSuccess() {
        return success;
    }

    public Result setSuccess(Boolean success) {
        this.success = success;
        return this;
    }



    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public Result()
    {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     * 
     * @param code 状态码
     * @param msg 返回内容
     */
    public Result(int code, String msg)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     * 
     * @param code 状态码
     * @param msg 返回内容
     * @param data 数据对象
     */
    public Result(int code, String msg, Object data)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        super.put(DATA_TAG, data);
    }

    public static Result getResult(int i, String s) {
        return Result.error(i,s);
    }

    public static Result getResult(BaseResponseCode systemRedisBusy) {
        return Result.error(systemRedisBusy.getCode(),systemRedisBusy.getMsg());
    }

    /**
     * 方便链式调用
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public Result put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }

    /**
     * 返回成功消息
     * 
     * @return 成功消息
     */
    public static Result success()
    {
        return Result.success("success");
    }
    public static Result ok()
    {
        return Result.success("success");
    }

    /**
     * 返回成功数据
     * 
     * @return 成功消息
     */
    public static Result ok(Object data)
    {
        return Result.success("success", data);
    }
    public static Result success(Object data)
    {
        return Result.success("success", data);
    }

    /**
     * 返回成功消息
     * 
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static Result success(String msg, Object data)
    {
        return new Result(0, msg, data);
    }
    public static Result success(int code, String msg, Object data)
    {
        return new Result(code, msg, data);
    }

    /**
     * 返回错误消息
     * 
     * @return
     */
    public static Result fail()
    {
        return Result.error("fail").setSuccess(false);
    }
    public static Result error()
    {
        return Result.error("fail").setSuccess(false);
    }

    /**
     * 返回错误消息
     * 
     * @param msg 返回内容
     * @return 警告消息
     */
    public static Result fail(String msg)
    {
        return Result.error(msg, null).setSuccess(false);
    }
    public static Result error(String msg)
    {
        return Result.error(msg, null).setSuccess(false);
    }

    /**
     * 返回错误消息
     * 
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static Result error(String msg, Object data)
    {
        return new Result(500, msg, data).setSuccess(false);
    }

    /**
     * 返回错误消息
     * 
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static Result error(int code, String msg)
    {
        return new Result(code, msg, null).setSuccess(false);
    }
}
