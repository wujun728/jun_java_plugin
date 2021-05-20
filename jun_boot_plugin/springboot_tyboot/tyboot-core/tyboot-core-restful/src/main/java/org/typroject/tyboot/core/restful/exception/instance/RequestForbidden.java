package org.typroject.tyboot.core.restful.exception.instance;

import org.typroject.tyboot.core.foundation.exception.BaseException;

/**
 * Created by yaohelang on 2018/5/28.
 */
public class RequestForbidden extends BaseException {
    public RequestForbidden(String message)
    {
        super(message,RequestForbidden.class.getSimpleName(),"请求被拒绝执行.");
        this.httpStatus = 403;
    }
}
