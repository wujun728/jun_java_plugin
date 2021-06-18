package org.ws.httphelper.request.handler.impl.pro;

import org.ws.httphelper.WSHttpHelperConstant;
import org.ws.httphelper.annotation.WSRequest;
import org.ws.httphelper.common.JsonUtil;
import org.ws.httphelper.exception.WSException;
import org.ws.httphelper.model.ResponseResult;
import org.ws.httphelper.model.WSRequestContext;
import org.ws.httphelper.request.handler.ResponseProHandler;

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
