package com.jun.plugin.httphelper.request.handler;

import com.jun.plugin.httphelper.exception.WSException;
import com.jun.plugin.httphelper.model.ResponseResult;

public interface CallbackHandler {
    public ResponseResult execute(ResponseResult result) throws WSException;
}
