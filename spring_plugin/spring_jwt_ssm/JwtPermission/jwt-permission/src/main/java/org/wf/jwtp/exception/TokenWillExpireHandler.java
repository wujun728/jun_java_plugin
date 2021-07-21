package org.wf.jwtp.exception;

import org.wf.jwtp.provider.TokenStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token过期异常处理
 * Created by wangfan on 2020-03-25 13:53
 */
public interface TokenWillExpireHandler {

    boolean handle(TokenStore tokenStore, HttpServletRequest request, HttpServletResponse response);

}
