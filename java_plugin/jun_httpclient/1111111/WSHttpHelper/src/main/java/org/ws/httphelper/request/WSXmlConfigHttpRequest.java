/*
 * Copyright (c) 2015-2016, AlexGao
 * http://git.oschina.net/wolfsmoke/WSHttpHelper
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ws.httphelper.request;

import org.apache.commons.lang.StringUtils;
import org.ws.httphelper.WSHttpHelperConstant;
import org.ws.httphelper.exception.WSException;
import org.ws.httphelper.model.WSRequestContext;
import org.ws.httphelper.model.config.HandlerData;
import org.ws.httphelper.model.config.RequestConfigData;
import org.ws.httphelper.request.handler.HandlerFactory;
import org.ws.httphelper.request.handler.RequestPreHandler;
import org.ws.httphelper.request.handler.ResponseProHandler;

import java.util.List;

/**
 * Created by Administrator on 16-1-1.
 */
public class WSXmlConfigHttpRequest extends WSAbstractHttpRequest {
    private RequestConfigData requestConfigData;
    public WSXmlConfigHttpRequest(RequestConfigData data){
        this.requestConfigData=data;
    }
    @Override
    public void init(WSRequestContext context) throws WSException {
        // 前处理
        List<HandlerData> preHandlerList = requestConfigData.getRequestHandlers().getPreHandlers();
        if(preHandlerList!=null){
            try {
                for (HandlerData handlerData : preHandlerList) {
                    String clazz = handlerData.getClazz();
                    if (!StringUtils.isEmpty(clazz)) {
                        addRequestPreHandler(HandlerFactory.finadHandler(RequestPreHandler.class, clazz));
                    }
                }
            }
            catch (Exception e) {
                throw new WSException(e.getMessage(),e);
            }
        }
        // 后处理
        List<HandlerData> proHandlerList = requestConfigData.getRequestHandlers().getProHandlers();
        if(proHandlerList!=null){
            try{
                for(HandlerData handlerData:proHandlerList){
                    String clazz = handlerData.getClazz();
                    if (!StringUtils.isEmpty(clazz)) {
                        addResponseProHandler(HandlerFactory.finadHandler(ResponseProHandler.class, clazz));
                    }
                }
            }
            catch (Exception e){
                throw new WSException(e.getMessage(),e);
            }
        }
    }

    @Override
    protected WSRequestContext builderContext() throws WSException {
        return this.requestConfigData.getContext();
    }
}
