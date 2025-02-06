package com.kdyzm.validation.spring.bootdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WrapperResult<T> {

    private int status;
    private T data;
    private String msg;

    private static final int STATUS_SUCCESS = 0;
    private static final int STATUS_FAILD = 1;


    public static <T> WrapperResult<T> success(T data) {
        return WrapperResult
                .<T>builder()
                .data(data)
                .msg("OK")
                .status(STATUS_SUCCESS)
                .build();
    }


    public static <T> WrapperResult<T> faild(String msg) {
        return WrapperResult
                .<T>builder()
                .data(null)
                .msg(msg)
                .status(STATUS_FAILD)
                .build();
    }

    public boolean isSuccess() {
        return this.status == STATUS_SUCCESS;
    }


}
