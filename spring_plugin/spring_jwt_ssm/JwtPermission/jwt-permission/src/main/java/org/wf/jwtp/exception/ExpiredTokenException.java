package org.wf.jwtp.exception;

/**
 * token过期异常
 * Created by wangfan on 2018-1-23 上午11:26:01
 */
public class ExpiredTokenException extends TokenException {
    private static final long serialVersionUID = -8019541050781876369L;

    public ExpiredTokenException() {
        super(402, "登录已过期");
    }

}
