package io.github.wujun728.snakerflow.module;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.MDC;
import org.springframework.context.annotation.DependsOn;

/**
 * @author longli
 */
@Data
@AllArgsConstructor
@ApiModel
@DependsOn
public class Response<T> {
    @ApiModelProperty(notes = "响应码，非0 即为异常", example = "0")
    private final String code;
    @ApiModelProperty(notes = "响应消息", example = "提交成功")
    private final String msg;
    @ApiModelProperty(notes = "响应数据")
    private final T data;
    @ApiModelProperty(notes = "请求id")
    private final String requestId;

    @ApiModelProperty(notes = "请求id")
    private final Boolean success;

    public Response(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = StrUtil.equals("0", code);
        this.requestId = MDC.get("requestId");
    }

    public static <T> Response<T> ok(T data) {
        return new Response<>("0", "操作成功", data);
    }

    public static <Void> Response<Void> ok() {
        return new Response<Void>("0", "操作成功", null);
    }

    public static <T> Response<T> error(T data) {
        return new Response<>("400", "", data);
    }

    public static <T> Response<T> error(String code, String msg, T data) {
        return new Response<>(code, msg, data);
    }

    public static <T> Response<T> error(String code, String msg) {
        return new Response<>(code, msg, null);
    }
}
