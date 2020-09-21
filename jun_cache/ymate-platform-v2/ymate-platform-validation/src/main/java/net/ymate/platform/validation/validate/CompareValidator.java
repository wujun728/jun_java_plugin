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
 * 参数值比较验证
 *
 * @author 刘镇 (suninformation@163.com) on 2013-4-17 下午9:57:16
 * @version 1.0
 */
@Validator(VCompare.class)
@CleanProxy
public class CompareValidator extends AbstractValidator {

    public ValidateResult validate(ValidateContext context) {
        Object _paramValue = context.getParamValue();
        if (_paramValue != null && !_paramValue.getClass().isArray()) {
            VCompare _vCompare = (VCompare) context.getAnnotation();
            boolean _matched = BlurObject.bind(_paramValue).toStringValue().equals(BlurObject.bind(context.getParamValue(_vCompare.with())).toString());
            switch (_vCompare.cond()) {
                case NOT_EQ:
                    _matched = !_matched;
                    break;
            }
            if (!_matched) {
                String _pName = StringUtils.defaultIfBlank(context.getParamLabel(), context.getParamName());
                _pName = __doGetI18nFormatMessage(context, _pName, _pName);

                String _pLabel = StringUtils.defaultIfBlank(_vCompare.withLabel(), _vCompare.with());
                _pLabel = __doGetI18nFormatMessage(context, _pLabel, _pLabel);
                //
                String _msg = StringUtils.trimToNull(_vCompare.msg());
                if (_msg != null) {
                    _msg = __doGetI18nFormatMessage(context, _msg, _msg, _pName, _pLabel);
                } else {
                    switch (_vCompare.cond()) {
                        case NOT_EQ:
                            String __COMPARE_NOT_EQ = "ymp.validation.compare_not_eq";
                            _msg = __doGetI18nFormatMessage(context, __COMPARE_NOT_EQ, "{0} can not eq {1}.", _pName, _pLabel);
                            break;
                        case EQ:
                            String __COMPARE_EQ = "ymp.validation.compare_eq";
                            _msg = __doGetI18nFormatMessage(context, __COMPARE_EQ, "{0} must be eq {1}.", _pName, _pLabel);
                    }
                }
                return new ValidateResult(context.getParamName(), _msg);
            }
        }
        return null;
    }
}
