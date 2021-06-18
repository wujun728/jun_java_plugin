package cn.classg.common.utils;

import cn.classg.common.interfaces.ResultCodeInterface;
import lombok.Data;

@Data
public class R<T> {

    // 是否成功
    private Boolean success;

    // 状态码
    private Integer code;

    // 返回消息
    private String msg;

    // 总条数
    private Long count;

    // 返回数据
    private Object data;

    private R() {};

    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCodeInterface.SUCCESS);
        r.setMsg("成功");
        return r;
    }

    public static R error() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCodeInterface.ERROR);
        r.setMsg("失败");
        return r;
    }

    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public R msg(String msg) {
        this.setMsg(msg);
        return this;
    }

    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    public R count(Long count) {
        this.setCount(count);
        return this;
    }


    public R data(Object data) {
        this.setData(data);
        return this;
    }


}
