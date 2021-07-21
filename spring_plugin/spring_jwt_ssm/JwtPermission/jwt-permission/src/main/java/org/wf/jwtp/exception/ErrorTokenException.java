package org.wf.jwtp.exception;

/**
 * token验证失败异常
 * Created by wangfan on 2018-1-23 上午11:37:16
 */
public class ErrorTokenException extends TokenException {
    private static final long serialVersionUID = -2283411683871567063L;

    public ErrorTokenException() {
        super(401, "身份验证失败");
    }

    public ErrorTokenException(String message) {
        super(401, message);
    }

}
