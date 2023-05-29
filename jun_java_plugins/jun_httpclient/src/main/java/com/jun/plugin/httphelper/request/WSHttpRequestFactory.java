package com.jun.plugin.httphelper.request;

import com.jun.plugin.httphelper.WSHttpHelperXmlConfig;
import com.jun.plugin.httphelper.exception.WSException;
import com.jun.plugin.httphelper.model.WSRequestContext;
import com.jun.plugin.httphelper.model.config.RequestConfigData;

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