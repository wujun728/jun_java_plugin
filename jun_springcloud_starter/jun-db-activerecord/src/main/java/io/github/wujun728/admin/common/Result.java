package io.github.wujun728.admin.common;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result <T>{
    private int status = Status.SUCCESS;
    private String msg = "操作成功";
    private T data;

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public Result(int status, String msg, T data) {
        this.status = status;
        this.msg = StrUtil.isNotBlank(msg) ? msg: Status.SUCCESS == status ? "操作成功" : "系统异常";
        this.data = data;
    }

    public static <T> Result<T> error(T data,String msg){
        return new Result<>(Status.ERROR,msg,data);
    }
    public static <T> Result<T> error(String msg){
        return error(null,msg);
    }

    public static <T> Result<T> success(T data,String msg){
        return new Result<>(Status.SUCCESS,msg,data);
    }

    public static <T> Result<T> success(T data){
        return success(data,null);
    }
    public static <T> Result<Map<String,T>> successForKey(String key,T data){
        Map<String,T> map = new HashMap<>();
        map.put(key,data);
        return success(map,null);
    }

    public static <T> Result<T> success(String msg){
        return success(null,msg);
    }
    public static <T> Result<T> success(){
        return success(null,null);
    }
    public boolean isSuccess(){
        return this.status == Status.SUCCESS;
    }
}
