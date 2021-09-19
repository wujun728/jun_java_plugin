package com.jun.plugin.httphelper.http;

import java.util.concurrent.Callable;

import com.jun.plugin.httphelper.model.ResponseResult;
import com.jun.plugin.httphelper.model.WSRequestContext;

/**
 * Created by gz on 15/12/6.
 */
public class WSHttpClient extends WSHttpAbstractClient implements Callable<Object> {

    public WSHttpClient(WSRequestContext context) {
        super(context);
    }

    @Override
    public ResponseResult call() throws Exception {
        return super.doRequest();
    }
}
