/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.validation.validate;

import net.ymate.platform.core.beans.annotation.CleanProxy;
import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.validation.AbstractValidator;
import net.ymate.platform.validation.ValidateContext;
import net.ymate.platform.validation.ValidateResult;
import net.ymate.platform.validation.annotation.Validator;
import org.apache.commons.lang.StringUtils;

/**
 * 邮箱地址格式验证
 *
 * @author 刘镇 (suninformation@163.com) on 2013-4-17 下午2:03:56
 * @version 1.0
 */
@Validator(VEmail.class)
@CleanProxy
public class EmailValidator extends AbstractValidator {

    public ValidateResult validate(ValidateContext context) {
        Object _paramValue = context.getParamValue();
        if (_paramValue != null) {
            if (!context.getParamValue().getClass().isArray()) {
                String _value = BlurObject.bind(_paramValue).toStringValue();
                if (StringUtils.isNotBlank(_value)) {
                    if (!_value.matches("(?:\\w[-._\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,8}$)")) {
                        String _pName = StringUtils.defaultIfBlank(context.getParamLabel(), context.getParamName());
                        _pName = __doGetI18nFormatMessage(context, _pName, _pName);
                        //
                        String _msg = StringUtils.trimToNull(((VEmail) context.getAnnotation()).msg());
                        if (_msg != null) {
                            _msg = __doGetI18nFormatMessage(context, _msg, _msg, _pName);
                        } else {
                            String __EMAIL = "ymp.validation.email";
                            _msg = __doGetI18nFormatMessage(context, __EMAIL, "{0} not a valid email address.", _pName);
                        }
                        return new ValidateResult(context.getParamName(), _msg);
                    }
                }
            }
        }
        return null;
    }
}
