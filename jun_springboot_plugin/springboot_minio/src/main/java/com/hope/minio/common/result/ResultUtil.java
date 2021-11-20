package com.hope.minio.common.result;

/**
 * @PackageName: com.hope.minio.common.result
 * @ClassName: ResultUtil
 * @Author Hope
 * @Date 2020/7/27 11:15
 * @Description: ResultUtil
 */
public class ResultUtil {
    /**
     * 数据交互成功返回
     *
     * @param object json返回的数据
     */
    public static Result success(Object object) {
        if (object == null) {
            object = "";
        }
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), object);
    }

    /**
     * 数据交互成功返回
     *
     * @param object json返回的数据
     */
    public static Result success(String message, Object object) {
        if (object == null) {
            object = "";
        }
        return new Result(ResultEnum.SUCCESS.getCode(), message, object);
    }


    /**
     * 数据交互成功返回
     */
    public static Result success() {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg());
    }

    /**
     * 数据交互成功返回
     *
     * @param message json返回的message
     */
    public static Result sendSuccessMessage(String message) {
        return new Result(ResultEnum.SUCCESS.getCode(), message);
    }

    /**
     * 数据交互失败返回
     *
     * @param message json返回的message
     */
    public static Result sendErrorMessage(String message) {
        return new Result(ResultEnum.ERROR.getCode(), message);
    }

    /**
     * 数据交互
     */
    public static Result notFound() {
        return new Result(ResultEnum.NOT_FOUND.getCode(), ResultEnum.NOT_FOUND.getMsg());
    }

    /**
     * 参数异常
     */
    public static Result parameterError() {
        return new Result(ResultEnum.PARAMETER_ERROR.getCode(), ResultEnum.PARAMETER_ERROR.getMsg());
    }

    /**
     * 权限异常
     */
    public static Result permissionsError() {
        return new Result(ResultEnum.PERMISSIONS_ERROR.getCode(), ResultEnum.PERMISSIONS_ERROR.getMsg());
    }

    /**
     * 参数异常
     */
    public static Result parameterError(String errorMsg) {
        return new Result(ResultEnum.PARAMETER_ERROR.getCode(), errorMsg);
    }

    /**
     * 系统异常
     */
    public static Result systemError() {
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
    }


    /**
     * 权限异常
     */
    public static Result pwdError() {
        return new Result(ResultEnum.USERPWD_NOT_EXIST.getCode(), ResultEnum.USERPWD_NOT_EXIST.getMsg());
    }
}