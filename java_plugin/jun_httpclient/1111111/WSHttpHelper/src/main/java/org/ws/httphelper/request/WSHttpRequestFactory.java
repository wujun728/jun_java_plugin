package org.ws.httphelper.request;

import org.ws.httphelper.WSHttpHelperXmlConfig;
import org.ws.httphelper.exception.WSException;
import org.ws.httphelper.model.WSRequestContext;
import org.ws.httphelper.model.config.RequestConfigData;

/**
 * Created by Administrator on 15-12-25.
 */
public class WSHttpRequestFactory {
    public static WSHttpRequest getHttpRequest(String name) throws WSException {
        RequestConfigData requestConfigData = WSHttpHelperXmlConfig.getInstance().getRequestConfigData(name);
        WSXmlConfigHttpRequest request = new WSXmlConfigHttpRequest(requestConfigData);
        return request;
    }
}