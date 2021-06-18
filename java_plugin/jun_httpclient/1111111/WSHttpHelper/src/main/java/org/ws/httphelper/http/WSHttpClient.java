package org.ws.httphelper.http;

import org.ws.httphelper.model.ResponseResult;
import org.ws.httphelper.model.WSRequestContext;

import java.util.concurrent.Callable;

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
