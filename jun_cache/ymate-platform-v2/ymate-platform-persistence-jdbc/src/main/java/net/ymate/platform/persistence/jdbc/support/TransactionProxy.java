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
package net.ymate.platform.persistence.jdbc.support;

import net.ymate.platform.core.beans.annotation.Order;
import net.ymate.platform.core.beans.annotation.Proxy;
import net.ymate.platform.core.beans.proxy.IProxy;
import net.ymate.platform.core.beans.proxy.IProxyChain;
import net.ymate.platform.persistence.jdbc.JDBC;
import net.ymate.platform.persistence.jdbc.annotation.Transaction;
import net.ymate.platform.persistence.jdbc.transaction.Trade;
import net.ymate.platform.persistence.jdbc.transaction.Transactions;

/**
 * JDBC数据库事务代理
 *
 * @author 刘镇 (suninformation@163.com) on 15/4/29 上午10:07
 * @version 1.0
 */
@Proxy(annotation = Transaction.class, order = @Order(666))
public class TransactionProxy implements IProxy {

    public Object doProxy(final IProxyChain proxyChain) throws Throwable {
        JDBC.TRANSACTION _currentLevel = null;
        // 判断方法对象是否被声明@Transaction注解，否则忽略
        if (proxyChain.getTargetMethod().isAnnotationPresent(Transaction.class)) {
            // 获取当前类声明的全局事务级别参数
            _currentLevel = proxyChain.getTargetClass().getAnnotation(Transaction.class).value();
            //
            JDBC.TRANSACTION _tmpLevel = proxyChain.getTargetMethod().getAnnotation(Transaction.class).value();
            // 如果全局事务级别被设置或低于NONE，则分析targetMethod是否存在@Transaction注解声明并尝试获取其事务级别设置
            if (_currentLevel.compareTo(JDBC.TRANSACTION.NONE) > 0) {
                _currentLevel = _tmpLevel;
            }
        }
        // 如果事务级别非空，则开启事务
        if (_currentLevel != null) {
            return Transactions.execute(_currentLevel, new Trade<Object>() {
                public void deal() throws Throwable {
                    this.setReturns(proxyChain.doProxyChain());
                }
            });
        } else {
            return proxyChain.doProxyChain();
        }
    }
}
