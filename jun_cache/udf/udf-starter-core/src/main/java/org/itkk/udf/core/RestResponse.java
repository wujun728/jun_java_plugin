package org.itkk.udf.core;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.itkk.udf.core.exception.ErrorResult;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.UUID;

/**
 * 描述 : rest响应对象
 *
 * @author wangkang
 */
@ApiModel(description = "响应消息体")
@Data
@ToString
public class RestResponse<T> implements Serializable {

    /**
     * 描述 : id
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : 响应ID
     */
    @ApiModelProperty(value = "响应ID", required = true, dataType = "string")
    private String id = UUID.randomUUID().toString();

    /**
     * 描述 : 状态码(业务定义)
     */
    @ApiModelProperty(value = "状态码(业务定义)", required = true, dataType = "string")
    private String code = Integer.toString(HttpStatus.OK.value());

    /**
     * 描述 : 状态码描述(业务定义)
     */
    @ApiModelProperty(value = "状态码描述(业务定义)", required = true, dataType = "string")
    private String message = HttpStatus.OK.getReasonPhrase();

    /**
     * 描述 : 结果集(泛型)
     */
    @ApiModelProperty(value = "结果集(泛型)", required = true, dataType = "object")
    private T result = null; //NOSONAR

    /**
     * 描述 : 错误
     */
    @ApiModelProperty(value = "错误", required = true, dataType = "object")
    private ErrorResult error = null;

    /**
     * 描述 : 构造函数
     */
    public RestResponse() {
        super();
    }

    /**
     * 描述 : 构造函数
     *
     * @param result 结果集(泛型)
     */
    public RestResponse(T result) {
        super();
        this.result = result;
    }

    /**
     * 描述 : 构造函数
     *
     * @param httpStatus http状态
     * @param error      错误
     */
    public RestResponse(HttpStatus httpStatus, ErrorResult error) {
        super();
        this.code = Integer.toString(httpStatus.value());
        this.message = httpStatus.getReasonPhrase();
        this.error = error;
    }

    /**
     * 描述 : 构造函数
     *
     * @param code    状态码(业务定义)
     * @param message 状态码描述(业务定义)
     * @param error   错误
     */
    public RestResponse(String code, String message, ErrorResult error) {
        super();
        this.code = code;
        this.message = message;
        this.error = error;
    }

    /**
     * 描述 : 构造函数
     *
     * @param code    状态码(业务定义)
     * @param message 状态码描述(业务定义)
     */
    public RestResponse(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    /**
     * 描述 : 构造函数
     *
     * @param code    状态码(业务定义)
     * @param message 状态码描述(业务定义)
     * @param result  结果集(泛型)
     */
    public RestResponse(String code, String message, T result) {
        super();
        this.code = code;
        this.message = message;
        this.result = result;
    }

}
