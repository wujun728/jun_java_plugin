package com.jun.plugin.httphelper.request.handler.impl.pre;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.jun.plugin.httphelper.WSHttpHelperConstant;
import com.jun.plugin.httphelper.annotation.Parameter;
import com.jun.plugin.httphelper.exception.WSException;
import com.jun.plugin.httphelper.model.ParameterDefine;
import com.jun.plugin.httphelper.model.WSRequestContext;
import com.jun.plugin.httphelper.request.handler.RequestPreHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认参数生成器
 * Created by gz on 15/12/4.
 */
public class DefaultParameterBuliderHandlerImpl implements RequestPreHandler {
    protected static Log log = LogFactory.getLog(DefaultParameterBuliderHandlerImpl.class);

    @Override
    public boolean handler(WSRequestContext context) throws WSException {
        List<ParameterDefine> parameterDefineList = context.getParameterDefineList();
        List<NameValuePair> nameValuePairList = context.getNameValuePairList();
        if (nameValuePairList == null) {
            nameValuePairList = new ArrayList<NameValuePair>();
        }
        Map<String, Object> multipartDataMap = context.getMultipartDataMap();
        if (multipartDataMap == null) {
            multipartDataMap = new HashMap<String, Object>();
        }
        if (parameterDefineList != null) {
            for (ParameterDefine parameterDefine : parameterDefineList) {
                String name = parameterDefine.getName();
                Object value = context.getInputDataMap().get(name);
                if (value instanceof List) {
                    List list = (List) value;
                    for (Object o : list) {
                        if (o instanceof String) {
                            nameValuePairList.add(new BasicNameValuePair(name, String.valueOf(o)));
                        }
                    }
                } else if (value instanceof File) {
                    log.debug(name);
                    multipartDataMap.put(name, value);
                } else {
                    String s = String.valueOf(value);
                    if (!StringUtils.isEmpty(s) && !"null".equals(s.toLowerCase())) {
                        if (parameterDefine.getType() == Parameter.Type.LIST) {
                            String[] list = s.split(",");
                            for (String v : list) {
                                nameValuePairList.add(new BasicNameValuePair(name, v));
                            }
                        } else if (parameterDefine.getType() == Parameter.Type.FILE) {
                            File file = new File(s);
                            if (file.isFile()) {
                                multipartDataMap.put(name, value);
                            }
                        } else {
                            nameValuePairList.add(new BasicNameValuePair(name, s));
                        }
                    }
                }
            }
        }
        context.setNameValuePairList(nameValuePairList);
        context.setMultipartDataMap(multipartDataMap);
        return true;
    }

    @Override
    public int level() {
        return WSHttpHelperConstant.PRE_HANDLER_BUILD_PARAM;
    }
}
