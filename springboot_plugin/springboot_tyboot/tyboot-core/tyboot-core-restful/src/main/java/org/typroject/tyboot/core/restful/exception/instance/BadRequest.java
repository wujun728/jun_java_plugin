package org.typroject.tyboot.core.restful.exception.instance;

import org.typroject.tyboot.core.foundation.exception.BaseException;

/**
 * Created by magintursh on 2017-07-05.
 */
public class BadRequest extends BaseException {

    public BadRequest(String message)
    {
        super(message,BadRequest.class.getSimpleName(),"错误的请求.");
        this.httpStatus = 400;
    }

}
