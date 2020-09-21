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

import net.ymate.platform.persistence.jdbc.DataSourceCfgMeta;
import net.ymate.platform.persistence.jdbc.IConnectionHolder;
import net.ymate.platform.persistence.jdbc.IDataSourceAdapter;
import net.ymate.platform.persistence.jdbc.dialect.IDialect;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 默认数据库Connection对象持有者接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-29 下午4:44:08
 * @version 1.0
 */
public class DefaultConnectionHolder implements IConnectionHolder {

    private IDataSourceAdapter __dsAdapter;

    private Connection __conn;

    public DefaultConnectionHolder(IDataSourceAdapter dsAdapter) throws Exception {
        __dsAdapter = dsAdapter;
        if (__dsAdapter.tryInitializeIfNeed()) {
            __conn = dsAdapter.getConnection();
        }
    }

    public DataSourceCfgMeta getDataSourceCfgMeta() {
        return __dsAdapter.getDataSourceCfgMeta();
    }

    public Connection getConnection() {
        return __conn;
    }

    public void release() {
        try {
            if (this.__conn != null && !this.__conn.isClosed()) {
                this.__conn.close();
            }
        } catch (SQLException ignored) {
        }
    }

    public IDialect getDialect() {
        return __dsAdapter.getDialect();
    }
}
