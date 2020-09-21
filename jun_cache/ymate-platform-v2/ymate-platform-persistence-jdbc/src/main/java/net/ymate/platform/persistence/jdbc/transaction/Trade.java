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
package net.ymate.platform.persistence.jdbc.transaction;

/**
 * 事务业务操作类，支持业务返回执行结果
 *
 * @author 刘镇 (suninformation@163.com) on 15/4/28 下午9:26
 * @version 1.0
 */
public abstract class Trade<T> implements ITrade {

    protected T __returns;

    /**
     * 设置执行结果
     *
     * @param returns 预返回的结果对象
     */
    protected void setReturns(T returns) {
        this.__returns = returns;
    }

    /**
     * @return 获取执行结果
     */
    public T getReturns() {
        return __returns;
    }
}
