package com.jun.plugin.httphelper;

import junit.framework.TestCase;

import java.util.List;

import com.jun.plugin.httphelper.WSHttpHelperXmlConfig;
import com.jun.plugin.httphelper.model.config.HandlerData;
import com.jun.plugin.httphelper.model.config.HttpClientConfig;
import com.jun.plugin.httphelper.model.config.RequestConfigData;
import com.jun.plugin.httphelper.model.config.RequestHandlers;
import com.jun.plugin.httphelper.request.handler.RequestPreHandler;
import com.jun.plugin.httphelper.request.handler.ResponseProHandler;

/**
 * Created by Administrator on 15-12-31.
 */
public class TestWSHttpHelperXmlConfig extends TestCase{
    public void testInit() throws Exception {
        HttpClientConfig httpClientConfig=WSHttpHelperXmlConfig.getInstance().getHttpClientConfig();
        TestCase.assertEquals("UTF-8",httpClientConfig.getHttpCharset());
        TestCase.assertEquals(50,httpClientConfig.getCorePoolSize());
        //
        List<RequestPreHandler> requestPreHandlers=WSHttpHelperXmlConfig.getInstance().getDefaultPreHandlers();
        if(requestPreHandlers!=null){
            for(RequestPreHandler requestPreHandler:requestPreHandlers){
                System.out.println(requestPreHandler.level());
                System.out.println(requestPreHandler.getClass().getName());
            }
        }
        //
        List<ResponseProHandler> responseProHandlers=WSHttpHelperXmlConfig.getInstance().getDefaultProHandlers();
        if(responseProHandlers!=null){
            for(ResponseProHandler responseProHandler:responseProHandlers){
                System.out.println(responseProHandler.level());
                System.out.println(responseProHandler.getClass().getName());
            }
        }
        //
        List<RequestConfigData> requestConfigDatas=WSHttpHelperXmlConfig.getInstance().getRequestConfigDataList();
        if(requestConfigDatas!=null){
            for(RequestConfigData data:requestConfigDatas){
                System.out.println(data.getContext());
            }
        }
    }
}
