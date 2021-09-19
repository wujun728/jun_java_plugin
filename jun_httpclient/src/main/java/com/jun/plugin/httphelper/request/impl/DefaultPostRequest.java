package com.jun.plugin.httphelper.request.impl;

import com.jun.plugin.httphelper.annotation.WSRequest;
import com.jun.plugin.httphelper.exception.WSException;
import com.jun.plugin.httphelper.model.WSRequestContext;
import com.jun.plugin.httphelper.request.WSAnnotationHttpRequest;

@WSRequest(
        name = "默认POST请求", url = "{url}", method = WSRequest.MethodType.POST
)
public class DefaultPostRequest extends WSAnnotationHttpRequest {
    @Override
    public void init(WSRequestContext context) throws WSException {

    }
}
