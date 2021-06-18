package org.ws.httphelper.request.handler.impl.pre;

import org.apache.commons.lang.StringUtils;
import org.ws.httphelper.WSHttpHelperConstant;
import org.ws.httphelper.annotation.Parameter;
import org.ws.httphelper.exception.WSException;
import org.ws.httphelper.model.ErrorMessage;
import org.ws.httphelper.model.ParameterDefine;
import org.ws.httphelper.model.WSRequestContext;
import org.ws.httphelper.request.handler.RequestPreHandler;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 默认验证
 * Created by gz on 15/12/4.
 */
public class DefaultValidationHandlerImpl implements RequestPreHandler {
    @Override
    public boolean handler(WSRequestContext context) throws WSException {
        List<ParameterDefine> parameterDefineList = context.getParameterDefineList();
        if (parameterDefineList != null) {
            for (ParameterDefine parameterDefine : parameterDefineList) {
                boolean required = parameterDefine.isRequired();
                String name = parameterDefine.getName();
                String value = String.valueOf(context.getInputDataMap().get(name));
                // 验证非空
                if (required && StringUtils.isEmpty(value)) {
                    context.addValidationResult(
                            new ErrorMessage("参数[{0}({1})]，不能为空。",
                                    parameterDefine.getName(), parameterDefine.getDescription()));
                }
                // 验证数字类型
                if (parameterDefine.getType() == Parameter.Type.INT && !StringUtils.isBlank(value)) {
                    try {
                        Integer.valueOf(value);
                    } catch (NumberFormatException e) {
                        context.addValidationResult(
                                new ErrorMessage("参数[{0}]类型为INT，输入为[{1}]，转换失败。",
                                        parameterDefine.getName(), value));
                    }
                }
                // 验证正则
                String regex = parameterDefine.getValidateRegex();
                if (!StringUtils.isBlank(value) && !StringUtils.isBlank(regex) && !Pattern.matches(regex, value)) {
                    context.addValidationResult(
                            new ErrorMessage("参数[{0}]，输入为[{1}]，正则表达式[{2}]验证失败。",
                                    parameterDefine.getName(), value, parameterDefine.getValidateRegex()));
                }
            }
        }
        return context.getErrorMessageList().isEmpty();
    }

    @Override
    public int level() {
        return WSHttpHelperConstant.PRE_HANDLER_VALIDATION;
    }
}
