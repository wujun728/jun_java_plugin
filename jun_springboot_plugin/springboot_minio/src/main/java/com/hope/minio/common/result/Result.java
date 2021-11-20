package com.hope.minio.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @PackageName: com.hope.minio.bean
 * @ClassName: Result
 * @Author Hope
 * @Date 2020/7/27 10:21
 * @Description: Result
 */
@Data
public class Result<T> {

    @ApiModelProperty(value = "返回码", name = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回信息提示", name = "返回信息提示")
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "数据对象", name = "数据对象")
    private T data;

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
