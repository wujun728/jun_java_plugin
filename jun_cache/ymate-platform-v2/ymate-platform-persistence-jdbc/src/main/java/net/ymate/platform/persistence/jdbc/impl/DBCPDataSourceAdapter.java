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

import net.ymate.platform.core.util.ResourceUtils;
import net.ymate.platform.persistence.jdbc.AbstractDataSourceAdapter;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 基于DBCP连接池的数据源适配器接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 2013年12月19日 下午2:47:10
 * @version 1.0
 */
public class DBCPDataSourceAdapter extends AbstractDataSourceAdapter {

    private static final Log _LOG = LogFactory.getLog(DBCPDataSourceAdapter.class);

    protected BasicDataSource __ds;

    protected void __doInit() throws Exception {
        Properties _props = new Properties();
        InputStream _in = ResourceUtils.getResourceAsStream("dbcp.properties", this.getClass());
        if (_in != null) {
            _props.load(_in);
        }
        //
        _props.put("driverClassName", __cfgMeta.getDriverClass());
        _props.put("url", __cfgMeta.getConnectionUrl());
        _props.put("username", __cfgMeta.getUsername());
        if (__cfgMeta.isPasswordEncrypted() && __cfgMeta.getPasswordClass() != null) {
            _props.put("password", __cfgMeta.getPasswordClass().newInstance().decrypt(__cfgMeta.getPassword()));
        } else {
            _props.put("password", __cfgMeta.getPassword());
        }
        __ds = (BasicDataSource) BasicDataSourceFactory.createDataSource(_props);

    }

    @Override
    public void destroy() {
        if (__ds != null) {
            try {
                __ds.close();
            } catch (SQLException e) {
                _LOG.warn("", e);
            }
        }
        //
        super.destroy();
    }

    public Connection getConnection() throws Exception {
        return __ds.getConnection();
    }
}
