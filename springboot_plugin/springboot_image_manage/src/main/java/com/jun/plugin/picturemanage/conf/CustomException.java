package com.jun.plugin.picturemanage.conf;

import lombok.Getter;

/**
 * @Author YuChen
 * @Email 835033913@qq.com
 * @Create 2019/11/4 11:33
 */
@Getter
public class CustomException extends RuntimeException {

    private Integer code = 500;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Integer code) {
        super(message);
        this.code = code;
    }


}
