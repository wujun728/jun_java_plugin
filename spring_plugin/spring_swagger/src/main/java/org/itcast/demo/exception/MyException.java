package org.itcast.demo.exception;

import org.itcast.demo.util.ResponseCode;

/**
 * @description
 * @auther: CDHong
 * @date: 2019/6/25-18:01
 **/
public class MyException extends RuntimeException {

    public MyException(String message) {
        super(message);
    }
    public MyException(ResponseCode code) {
        super(code.getDesc());
    }
}
