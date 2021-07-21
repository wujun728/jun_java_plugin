package org.wf.jwtp.exception;

/**
 * 没有权限的异常
 * Created by wangfan on 2018-1-23 上午11:23:38
 */
public class UnauthorizedException extends TokenException {
    private static final long serialVersionUID = 8109117719383003891L;

    public UnauthorizedException() {
        super(403, "没有访问权限");
    }

}
