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
package net.ymate.platform.persistence.impl;

import net.ymate.platform.persistence.Fields;
import net.ymate.platform.persistence.IFunction;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

/**
 * @author 刘镇 (suninformation@163.com) on 17/6/22 上午10:50
 * @version 1.0
 */
public class DefaultFunction implements IFunction {

    private Fields __params;

    private boolean __flag;

    public DefaultFunction() {
        __params = Fields.create();
    }

    public DefaultFunction(String funcName) {
        if (StringUtils.isBlank(funcName)) {
            throw new NullArgumentException("funcName");
        }
        __params = Fields.create(funcName, "(");
        __flag = true;
    }

    private IFunction __opt(String opt, String param) {
        return space().param(opt).space().param(param);
    }

    public IFunction addition(Number param) {
        return __opt("+", param.toString());
    }

    public IFunction addition(String param) {
        return __opt("+", param);
    }

    public IFunction addition(IFunction param) {
        return __opt("+", param.build());
    }

    public IFunction subtract(Number param) {
        return __opt("-", param.toString());
    }

    public IFunction subtract(String param) {
        return __opt("-", param);
    }

    public IFunction subtract(IFunction param) {
        return __opt("-", param.build());
    }

    public IFunction multiply(Number param) {
        return __opt("*", param.toString());
    }

    public IFunction multiply(String param) {
        return __opt("*", param);
    }

    public IFunction multiply(IFunction param) {
        return __opt("*", param.build());
    }

    public IFunction divide(Number param) {
        return __opt("/", param.toString());
    }

    public IFunction divide(String param) {
        return __opt("/", param);
    }

    public IFunction divide(IFunction param) {
        return __opt("/", param.build());
    }

    public IFunction param(Number param) {
        __params.add(param.toString());
        return this;
    }

    public IFunction param(Number[] params) {
        if (params != null && params.length > 0) {
            boolean _flag = false;
            for (Number _n : params) {
                if (_n != null) {
                    if (_flag) {
                        separator();
                    }
                    __params.add(_n.toString());
                    _flag = true;
                }
            }
        }
        return this;
    }

    public IFunction separator() {
        __params.add(", ");
        return this;
    }

    public IFunction space() {
        __params.add(" ");
        return this;
    }

    public IFunction bracketBegin() {
        __params.add("(");
        return this;
    }

    public IFunction bracketEnd() {
        __params.add(")");
        return this;
    }

    public IFunction param(IFunction param) {
        __params.add(param.build());
        return this;
    }

    public IFunction paramWS(Object... params) {
        if (params != null && params.length > 0) {
            boolean _flag = false;
            for (Object _p : params) {
                if (_p != null) {
                    if (_flag) {
                        separator();
                    }
                    if (_p instanceof IFunction) {
                        __params.add(((IFunction) _p).build());
                    } else {
                        __params.add(_p.toString());
                    }
                    _flag = true;
                }
            }
        }
        return this;
    }

    public IFunction param(String param) {
        __params.add(param);
        return this;
    }

    public IFunction param(String[] params) {
        if (params != null && params.length > 0) {
            boolean _flag = false;
            for (String _p : params) {
                if (_p != null) {
                    if (_flag) {
                        separator();
                    }
                    __params.add(_p);
                    _flag = true;
                }
            }
        }
        return this;
    }

    public IFunction param(String prefix, String field) {
        __params.add(prefix, field);
        return this;
    }

    public Fields params() {
        return __params;
    }

    public String build() {
        if (__flag) {
            __params.add(")");
        }
        return StringUtils.join(__params.toArray(), StringUtils.EMPTY);
    }
}
