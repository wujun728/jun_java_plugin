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
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 必填项验证
 *
 * @author 刘镇 (suninformation@163.com) on 2013-4-13 下午6:06:29
 * @version 1.0
 */
@Validator(VRequried.class)
@CleanProxy
public class RequriedValidator extends AbstractValidator {

    public ValidateResult validate(ValidateContext context) {
        boolean _matched = false;
        Object _paramValue = context.getParamValue();
        if (_paramValue == null) {
            _matched = true;
        } else {
            if (!_paramValue.getClass().isArray()) {
                if (StringUtils.isBlank(BlurObject.bind(_paramValue).toStringValue())) {
                    _matched = true;
                }
            } else {
                _matched = ArrayUtils.isEmpty((Object[]) _paramValue);
            }
        }
        if (_matched) {
            String _pName = StringUtils.defaultIfBlank(context.getParamLabel(), context.getParamName());
            _pName = __doGetI18nFormatMessage(context, _pName, _pName);
            //
            String _msg = StringUtils.trimToNull(((VRequried) context.getAnnotation()).msg());
            if (_msg != null) {
                _msg = __doGetI18nFormatMessage(context, _msg, _msg, _pName);
            } else {
                String __REQURIED = "ymp.validation.requried";
                _msg = __doGetI18nFormatMessage(context, __REQURIED, "{0} must be requried.", _pName);
            }
            return new ValidateResult(context.getParamName(), _msg);
        }
        return null;
    }
}
