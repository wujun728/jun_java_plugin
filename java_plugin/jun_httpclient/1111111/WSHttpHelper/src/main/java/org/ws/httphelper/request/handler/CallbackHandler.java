package org.ws.httphelper.request.handler;

import org.ws.httphelper.exception.WSException;
import org.ws.httphelper.model.ResponseResult;

public interface CallbackHandler {
    public ResponseResult execute(ResponseResult result) throws WSException;
}
