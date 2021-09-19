package com.jun.plugin.httphelper.request.handler.impl.pro;

import com.jun.plugin.httphelper.WSHttpHelperConstant;
import com.jun.plugin.httphelper.annotation.WSRequest;
import com.jun.plugin.httphelper.common.JsonUtil;
import com.jun.plugin.httphelper.exception.WSException;
import com.jun.plugin.httphelper.model.ResponseResult;
import com.jun.plugin.httphelper.model.WSRequestContext;
import com.jun.plugin.httphelper.request.handler.ResponseProHandler;

/**
 * Created by gz on 15/12/6.
 */
public class DefaultResultParseHandlerImpl implements ResponseProHandler {
    @Override
    public ResponseResult handler(WSRequestContext context, ResponseResult result) throws WSException {
        // 解析结果,只解析JSON
        if (context.getResponseType() == WSRequest.ResponseType.JSON) {
            String json = result.getBody().toString();
            Class<?> resultClass = context.getResultClass();
            if (resultClass != null && resultClass != String.class) {
                Object resultObj = JsonUtil.fromJson(json, resultClass);
                result.setBody(resultObj);
            }
        }
        return result;
    }

    @Override
    public int level() {
        return WSHttpHelperConstant.PRO_HANDLER_PARSE;
    }
}
