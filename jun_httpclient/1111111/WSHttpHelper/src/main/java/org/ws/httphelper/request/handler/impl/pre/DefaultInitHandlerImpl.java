package org.ws.httphelper.request.handler.impl.pre;

import org.apache.commons.lang.StringUtils;
import org.ws.httphelper.WSHttpHelperConstant;
import org.ws.httphelper.exception.WSException;
import org.ws.httphelper.model.ParameterDefine;
import org.ws.httphelper.model.WSRequestContext;
import org.ws.httphelper.request.handler.RequestPreHandler;

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
