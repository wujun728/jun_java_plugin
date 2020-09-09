package org.ws.httphelper;

import junit.framework.TestCase;
import org.ws.httphelper.model.config.HttpClientConfig;
import org.ws.httphelper.model.config.RequestConfigData;
import org.ws.httphelper.model.config.RequestHandlers;
import org.ws.httphelper.model.config.HandlerData;
import org.ws.httphelper.request.handler.RequestPreHandler;
import org.ws.httphelper.request.handler.ResponseProHandler;

import java.util.List;

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
