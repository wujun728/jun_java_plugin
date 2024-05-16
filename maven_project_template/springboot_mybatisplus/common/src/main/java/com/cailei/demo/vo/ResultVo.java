package com.cailei.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author ：蔡磊
 * @date ：2022/10/20 18:52
 * @description：TODO
 */
@ApiModel(value = "返回的数据对象", description = "封装接口返回给前端的数据")
public class ResultVo implements Serializable {

    @ApiModelProperty("响应状态码")
    private Integer code;

    @ApiModelProperty("响应提示信息")
    private String message;

    @ApiModelProperty("响应的数据")
    private Object data;

    public ResultVo() {
    }

    public ResultVo(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultVo(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
