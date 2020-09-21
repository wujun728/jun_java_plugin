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

import com.mchange.v2.c3p0.ComboPooledDataSource;
import net.ymate.platform.persistence.jdbc.AbstractDataSourceAdapter;

import java.sql.Connection;

/**
 * 基于C3P0连接池的数据源适配器接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 2013-6-5 下午4:27:09
 * @version 1.0
 */
public class C3P0DataSourceAdapter extends AbstractDataSourceAdapter {

    protected ComboPooledDataSource __ds;

    protected void __doInit() throws Exception {
        __ds = new ComboPooledDataSource();
        __ds.setDriverClass(__cfgMeta.getDriverClass());
        __ds.setJdbcUrl(__cfgMeta.getConnectionUrl());
        __ds.setUser(__cfgMeta.getUsername());
        if (__cfgMeta.isPasswordEncrypted() && __cfgMeta.getPasswordClass() != null) {
            __ds.setPassword(__cfgMeta.getPasswordClass().newInstance().decrypt(__cfgMeta.getPassword()));
        } else {
            __ds.setPassword(__cfgMeta.getPassword());
        }
    }

    @Override
    public void destroy() {
        if (__ds != null) {
            __ds.close();
        }
        //
        super.destroy();
    }

    public Connection getConnection() throws Exception {
        return __ds.getConnection();
    }
}
