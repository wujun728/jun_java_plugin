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
package net.ymate.platform.core.beans.intercept;

/**
 * @author 刘镇 (suninformation@163.com) on 2017/10/12 上午10:22
 * @version 1.0
 */
public abstract class AbstractInterceptor implements IInterceptor {

    @Override
    public Object intercept(InterceptContext context) throws Exception {
        Object _result = null;
        switch (context.getDirection()) {
            case BEFORE:
                _result = __before(context);
                break;
            case AFTER:
                _result = __after(context);
        }
        return _result;
    }

    protected abstract Object __before(InterceptContext context) throws Exception;

    protected abstract Object __after(InterceptContext context) throws Exception;
}
