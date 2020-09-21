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
package net.ymate.platform.persistence.jdbc.query;

/**
 * @author 刘镇 (suninformation@163.com) on 15/9/25 13:23
 * @version 1.0
 */
public final class Union {

    private Select __select;

    private boolean __all;

    public static Union create(Select select) {
        return new Union(select);
    }

    private Union(Select select) {
        __select = select;
    }

    public Union all() {
        __all = true;
        return this;
    }

    public boolean isAll() {
        return __all;
    }

    public Select select() {
        return __select;
    }

}
