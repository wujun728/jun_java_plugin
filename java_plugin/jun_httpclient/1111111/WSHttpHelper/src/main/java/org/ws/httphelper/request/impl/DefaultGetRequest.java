package org.ws.httphelper.request.impl;

import org.ws.httphelper.annotation.WSRequest;
import org.ws.httphelper.annotation.WSRequest.MethodType;
import org.ws.httphelper.exception.WSException;
import org.ws.httphelper.model.WSRequestContext;
import org.ws.httphelper.request.WSAnnotationHttpRequest;

@WSRequest(
        name = "默认GET请求", url = "{url}", method = MethodType.GET
)
public class DefaultGetRequest extends WSAnnotationHttpRequest {

    @Override
    public void init(WSRequestContext context) throws WSException {

    }
}
