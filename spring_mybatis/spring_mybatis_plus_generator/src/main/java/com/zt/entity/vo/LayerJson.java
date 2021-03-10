package com.zt.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

/**
 * Created by CDHong on 2018/4/7.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LayerJson {
    private Integer code;     //状态码
    private Object data;  //返回的数据
    private long count;    //总条数

    public LayerJson(Object data, long count) {
        this.code = 0; //layer table 默认正常返回状态码
        this.data = data;
        this.count = count;
    }

    public static LayerJson getInstance(Object data, long count){
        return new LayerJson(data,count);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

}
