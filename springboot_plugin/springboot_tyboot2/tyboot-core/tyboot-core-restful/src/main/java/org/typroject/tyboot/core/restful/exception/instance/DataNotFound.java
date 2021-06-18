package org.typroject.tyboot.core.restful.exception.instance;

import org.typroject.tyboot.core.foundation.exception.BaseException;

/**
 * Created by magintursh on 2017-07-05.
 */
public class DataNotFound extends BaseException {

    public DataNotFound(String message)
    {
        super(message,DataNotFound.class.getSimpleName(),"数据不存在.");
        this.httpStatus = 404;
    }

}
