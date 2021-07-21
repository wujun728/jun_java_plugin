package com.wf.ew.common;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.wf.jwtp.provider.Token;
import org.wf.jwtp.util.SubjectUtil;

/**
 * Controller基类
 * Created by wangfan on 2018-02-22 上午 11:29.
 */
public class BaseController {

    /**
     * 获取当前登录的userId
     */
    public Integer getLoginUserId(HttpServletRequest request) {
        Token token = getLoginToken(request);
        return token == null ? null : Integer.parseInt(token.getUserId());
    }

    public Token getLoginToken(HttpServletRequest request) {
        return SubjectUtil.getToken(request);
    }

}
