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
import org.apache.commons.lang.math.NumberUtils;

/**
 * 数值类型参数验证
 *
 * @author 刘镇 (suninformation@163.com) on 2013-4-17 下午8:36:16
 * @version 1.0
 */
@Validator(VNumeric.class)
@CleanProxy
public class NumericValidator extends AbstractValidator {

    public ValidateResult validate(ValidateContext context) {
        Object _paramValue = context.getParamValue();
        if (_paramValue != null) {
            boolean _matched = false;
            boolean _flag = false;
            VNumeric _vNumeric = (VNumeric) context.getAnnotation();
            try {
                Number _number = NumberUtils.createNumber(BlurObject.bind(_paramValue).toStringValue());
                if (_number == null) {
                    _matched = true;
                    _flag = true;
                } else {
                    if (_vNumeric.min() > 0 && _number.doubleValue() < _vNumeric.min()) {
                        _matched = true;
                    } else if (_vNumeric.max() > 0 && _number.doubleValue() > _vNumeric.max()) {
                        _matched = true;
                    }
                }
            } catch (Exception e) {
                _matched = true;
                _flag = true;
            }
            if (_matched) {
                String _pName = StringUtils.defaultIfBlank(context.getParamLabel(), context.getParamName());
                _pName = __doGetI18nFormatMessage(context, _pName, _pName);
                String _msg = StringUtils.trimToNull(_vNumeric.msg());
                if (_msg != null) {
                    _msg = __doGetI18nFormatMessage(context, _msg, _msg, _pName);
                } else {
                    if (_flag) {
                        String __NUMERIC = "ymp.validation.numeric";
                        _msg = __doGetI18nFormatMessage(context, __NUMERIC, "{0} not a valid numeric.", _pName);
                    } else {
                        if (_vNumeric.max() > 0 && _vNumeric.min() > 0) {
                            String __NUMERIC_BETWEEN = "ymp.validation.numeric_between";
                            _msg = __doGetI18nFormatMessage(context, __NUMERIC_BETWEEN, "{0} numeric must be between {1} and {2}.", _pName, _vNumeric.min(), _vNumeric.max());
                        } else if (_vNumeric.max() > 0) {
                            String __NUMERIC_MAX = "ymp.validation.numeric_max";
                            _msg = __doGetI18nFormatMessage(context, __NUMERIC_MAX, "{0} numeric must be lt {1}.", _pName, _vNumeric.max());
                        } else {
                            String __NUMERIC_MIN = "ymp.validation.numeric_min";
                            _msg = __doGetI18nFormatMessage(context, __NUMERIC_MIN, "{0} numeric must be gt {1}.", _pName, _vNumeric.min());
                        }
                    }
                }
                return new ValidateResult(context.getParamName(), _msg);
            }
        }
        return null;
    }
}
