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
package net.ymate.platform.persistence.jdbc.impl;

import net.ymate.platform.persistence.jdbc.AbstractDataSourceAdapter;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 默认数据源适配器
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-29 下午4:12:05
 * @version 1.0
 */
public class DefaultDataSourceAdapter extends AbstractDataSourceAdapter {

    private String __password;

    protected void __doInit() throws Exception {
        //
        Class.forName(__cfgMeta.getDriverClass());
        //
        __password = __cfgMeta.getPassword();
        if (__cfgMeta.isPasswordEncrypted() && __cfgMeta.getPasswordClass() != null) {
            __password = __cfgMeta.getPasswordClass().newInstance().decrypt(__password);
        }
    }

    public Connection getConnection() throws Exception {
        return DriverManager.getConnection(__cfgMeta.getConnectionUrl(), __cfgMeta.getUsername(), __password);
    }

    public void destroy() {
        if (__inited) {
            __password = null;
            super.destroy();
        }
    }
}
