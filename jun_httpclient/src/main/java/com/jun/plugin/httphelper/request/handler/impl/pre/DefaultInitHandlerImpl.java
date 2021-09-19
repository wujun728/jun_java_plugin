package com.jun.plugin.httphelper.request.handler.impl.pre;

import org.apache.commons.lang.StringUtils;

import com.jun.plugin.httphelper.WSHttpHelperConstant;
import com.jun.plugin.httphelper.exception.WSException;
import com.jun.plugin.httphelper.model.ParameterDefine;
import com.jun.plugin.httphelper.model.WSRequestContext;
import com.jun.plugin.httphelper.request.handler.RequestPreHandler;

import java.util.List;

/**
 * 默认初始化处理
 * Created by gz on 15/12/4.
 */
public class DefaultInitHandlerImpl implements RequestPreHandler {
    @Override
    public boolean handler(WSRequestContext context) throws WSException {
        List<ParameterDefine> list = context.getParameterDefineList();
        for (ParameterDefine parameter : list) {
            String defaultValue = parameter.getDefaultValue();
            String name = parameter.getName();
            Object value = context.getInputDataMap().get(name);
            if (!StringUtils.isEmpty(defaultValue) && (value == null || StringUtils.isEmpty(value.toString()))) {
                context.addInputData(name, defaultValue);
            }
        }
        return true;
    }

    @Override
    public int level() {
        return WSHttpHelperConstant.PRE_HANDLER_INIT;
    }
}
