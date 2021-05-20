package org.ws.httphelper.model.config;

import org.apache.commons.lang.StringUtils;
import org.ws.httphelper.WSHttpHelperXmlConfig;
import org.ws.httphelper.annotation.Parameter;
import org.ws.httphelper.annotation.WSRequest;
import org.ws.httphelper.exception.WSException;
import org.ws.httphelper.model.ParameterDefine;
import org.ws.httphelper.model.WSRequestContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 15-12-30.
 */
public class RequestConfigData extends WSHttpHelperConfigData {
    public RequestConfigData(Map<String, Object> data) {
        super(data);
    }

    public WSRequestContext getContext() throws WSException {
        WSRequestContext context = new WSRequestContext();
        if (!StringUtils.isEmpty(getValue("name"))) {
            context.setName(getValue("name"));
        }
        if (!StringUtils.isEmpty(getValue("url"))) {
            context.setUrl(getValue("url"));
        }
        if (StringUtils.isEmpty(getValue("charset"))) {
            context.setCharset(WSHttpHelperXmlConfig.getInstance().getHttpClientConfig().getHttpCharset());
        } else {
            context.setCharset(getValue("charset"));
        }
        if (!StringUtils.isEmpty(getValue("description"))) {
            context.setDescription(getValue("description"));
        }
        String responseType = getValue("response-type");
        if (!StringUtils.isEmpty(responseType)) {
            for (WSRequest.ResponseType type : WSRequest.ResponseType.values()) {
                if (type.name().equals(responseType)) {
                    context.setResponseType(type);
                }
            }
        } else {
            context.setResponseType(WSRequest.ResponseType.HTML);
        }
        String method = getValue("method");
        if (!StringUtils.isEmpty(method)) {
            for (WSRequest.MethodType type : WSRequest.MethodType.values()) {
                if (type.name().equals(method)) {
                    context.setMethod(type);
                }
            }
        } else {
            context.setMethod(WSRequest.MethodType.GET);
        }
        if(this.data.containsKey("parameters")){
            Object parameters=  getObject("parameters:parameter");
            if (parameters != null) {
                if(parameters instanceof List){
                    for (Map<String, Object> parameter : (List<Map>)parameters) {
                        ParameterDefine parameterDefine = createParameterDefine(parameter);
                        context.addParameterDefine(parameterDefine);
                    }
                }
                else if(parameters instanceof Map){
                    ParameterDefine parameterDefine = createParameterDefine((Map)parameters);
                    context.addParameterDefine(parameterDefine);
                }
            }
        }
        if(data.containsKey("headers")){
            Object headers =  getObject("headers:header");
            if (headers != null) {
                if(headers instanceof List){
                    for (Map<String, Object> header : (List<Map>)headers) {
                        context.addHeader(getValue(header, "name"), getValue(header, "value"));
                    }
                }
                else if(headers instanceof Map){
                    context.addHeader(getValue((Map)headers, "name"), getValue((Map)headers, "value"));
                }

            }
        }
        return context;
    }

    public RequestHandlers getRequestHandlers(){
        Map<String, Object> handlers = (Map<String, Object>) getObject("handlers");
        if (handlers != null) {
            RequestHandlers requestHandlers = new RequestHandlers(handlers);
            return requestHandlers;
        }
        return new RequestHandlers(new HashMap<String, Object>());
    }


    private ParameterDefine createParameterDefine(Map parameter){
        ParameterDefine parameterDefine = new ParameterDefine();
        parameterDefine.setName(getValue(parameter, "name"));
        parameterDefine.setDescription(getValue(parameter, "description"));
        parameterDefine.setDefaultValue(getValue(parameter, "defaultValue"));
        parameterDefine.setExample(getValue(parameter, "example"));
        parameterDefine.setRequired("true".equals(getValue(parameter, "required")));
        parameterDefine.setValidateRegex(getValue(parameter, "validateRegex"));
        String type = getValue(parameter, "type");
        if (!StringUtils.isEmpty(type)) {
            for (Parameter.Type t : Parameter.Type.values()) {
                if (t.name().equals(type)) {
                    parameterDefine.setType(t);
                }
            }
        } else {
            parameterDefine.setType(Parameter.Type.STRING);
        }
        return parameterDefine;
    }
}
