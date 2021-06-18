package org.itcast.demo.util;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @description
 * @auther: CDHong
 * @date: 2019/5/2-13:52
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData implements Serializable{

    private Integer status;
    private Long total;
    private String msg;
    private Object data;

    private ResponseData(int status) {
        this.status = status;
    }
    private ResponseData(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
    private ResponseData(int status, Object data) {
        this.status = status;
        this.data = data;
    }
    private ResponseData(int status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    private ResponseData(int status, Long total, Object data) {
        this.status = status;
        this.total = total;
        this.data = data;
    }

    public static ResponseData ok(){
        return new ResponseData(ResponseCode.SUCCESS.getCode());
    }

    public static ResponseData ok(String msg){
        return new ResponseData(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static ResponseData ok(Object data){
        return new ResponseData(ResponseCode.SUCCESS.getCode(),data);
    }

    public static ResponseData okPage(Long total, Object data){
        return new ResponseData(ResponseCode.SUCCESS.getCode(),total,data);
    }

    public static ResponseData fail(String msg){
        return new ResponseData(ResponseCode.ERROR.getCode(),msg);
    }

    public static ResponseData error(ResponseCode responseCode){
        return new ResponseData(responseCode.getCode(),responseCode.getDesc());
    }
    public static ResponseData exception(String msg){
        return new ResponseData(ResponseCode.ERROR.getCode(),msg);
    }

    public static ResponseData ok(String msg, Object data) {
        return new ResponseData(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

}
