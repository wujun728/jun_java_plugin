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
 * 字符串长度验证
 *
 * @author 刘镇 (suninformation@163.com) on 2013-4-17 下午5:17:22
 * @version 1.0
 */
@Validator(VLength.class)
@CleanProxy
public class LengthValidator extends AbstractValidator {

    public ValidateResult validate(ValidateContext context) {
        boolean _matched = false;
        VLength _vLength = (VLength) context.getAnnotation();
        Object _paramValue = context.getParamValue();
        if (_paramValue != null) {
            int _length = 0;
            if (!_paramValue.getClass().isArray()) {
                String _value = BlurObject.bind(_paramValue).toStringValue();
                if (StringUtils.isNotBlank(_value)) {
                    _length = _value.length();
                }
            } else {
                Object[] _values = (Object[]) _paramValue;
                _length = _values.length;
            }
            //
            if (_vLength.min() > 0 && _vLength.max() == _vLength.min() && _length != _vLength.max()) {
                _matched = true;
            } else if (_vLength.min() > 0 && _length < _vLength.min()) {
                _matched = true;
            } else if (_vLength.max() > 0 && _length > _vLength.max()) {
                _matched = true;
            }
        }
        if (_matched) {
            String _pName = StringUtils.defaultIfBlank(context.getParamLabel(), context.getParamName());
            _pName = __doGetI18nFormatMessage(context, _pName, _pName);
            String _msg = StringUtils.trimToNull(_vLength.msg());
            if (_msg != null) {
                _msg = __doGetI18nFormatMessage(context, _msg, _msg, _pName);
            } else {
                if (_vLength.max() > 0 && _vLength.min() > 0) {
                    if (_vLength.max() == _vLength.min()) {
                        _msg = __doGetI18nFormatMessage(context, "ymp.validation.length_eq", "{0} length must be eq {1}.", _pName, _vLength.max());
                    } else {
                        _msg = __doGetI18nFormatMessage(context, "ymp.validation.length_between", "{0} length must be between {1} and {2}.", _pName, _vLength.min(), _vLength.max());
                    }
                } else if (_vLength.max() > 0) {
                    _msg = __doGetI18nFormatMessage(context, "ymp.validation.length_max", "{0} length must be lt {1}.", _pName, _vLength.max());
                } else {
                    _msg = __doGetI18nFormatMessage(context, "ymp.validation.length_min", "{0} length must be gt {1}.", _pName, _vLength.min());
                }
            }
            return new ValidateResult(context.getParamName(), _msg);
        }
        return null;
    }
}
