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
package net.ymate.platform.validation;

import net.ymate.platform.core.i18n.I18N;
import org.apache.commons.lang.StringUtils;

/**
 * @author 刘镇 (suninformation@163.com) on 17/1/10 上午4:35
 * @version 1.0
 */
public abstract class AbstractValidator implements IValidator {

    protected String __doGetI18nFormatMessage(ValidateContext context, String key, String defaultValue, Object... args) {
        String _message = null;
        if (StringUtils.isNotBlank(context.getResourceName())) {
            _message = I18N.formatMessage(context.getResourceName(), key, "", args);
        }
        if (StringUtils.isBlank(_message)) {
            _message = I18N.formatMessage(VALIDATION_I18N_RESOURCE, key, defaultValue, args);
        }
        return _message;
    }
}
