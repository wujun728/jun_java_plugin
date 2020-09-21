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
package net.ymate.platform.persistence.jdbc.transaction.impl;

import net.ymate.platform.core.util.UUIDUtils;
import net.ymate.platform.persistence.jdbc.IConnectionHolder;
import net.ymate.platform.persistence.jdbc.JDBC;
import net.ymate.platform.persistence.jdbc.transaction.ITransaction;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认JDBC事务处理接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 2011-9-6 下午04:43:45
 * @version 1.0
 */
public class DefaultTransaction implements ITransaction {

    private String __id;

    private JDBC.TRANSACTION __level;

    private Map<String, TransactionMeta> __transMetas;

    public DefaultTransaction() {
        this.__id = UUIDUtils.UUID();
        this.__transMetas = new HashMap<String, TransactionMeta>();
    }

    public DefaultTransaction(JDBC.TRANSACTION level) {
        this();
        this.setLevel(level);
    }

    public JDBC.TRANSACTION getLevel() {
        return __level;
    }

    public void setLevel(JDBC.TRANSACTION level) {
        if (level == null || (this.__level != null && this.__level.getLevel() > 0)) {
            return;
        }
        this.__level = level;
    }

    public String getId() {
        return __id;
    }

    public void commit() throws SQLException {
        for (TransactionMeta _meta : this.__transMetas.values()) {
            _meta.connectionHolder.getConnection().commit();
        }
    }

    public void rollback() throws SQLException {
        for (TransactionMeta _meta : this.__transMetas.values()) {
            _meta.connectionHolder.getConnection().rollback();
        }
    }

    public void close() throws SQLException {
        try {
            for (TransactionMeta _meta : this.__transMetas.values()) {
                _meta.release();
            }
        } finally {
            this.__transMetas = null;
        }
    }

    public IConnectionHolder getConnectionHolder(String dsName) {
        if (this.__transMetas.containsKey(dsName)) {
            return this.__transMetas.get(dsName).connectionHolder;
        }
        return null;
    }

    public void registerConnectionHolder(IConnectionHolder connectionHolder) throws SQLException {
        String _dsName = connectionHolder.getDataSourceCfgMeta().getName();
        if (!this.__transMetas.containsKey(_dsName)) {
            this.__transMetas.put(_dsName, new TransactionMeta(connectionHolder, getLevel()));
        }
    }

    /**
     * 事务信息描述对象
     *
     * @author 刘镇 (suninformation@163.com) on 2010-10-16 下午03:50:01
     * @version 1.0
     */
    private static class TransactionMeta {

        // 数据库连接持有者对象
        IConnectionHolder connectionHolder;

        /**
         * 构造器
         *
         * @param connectionHolder 数据库连接持有者对象
         * @param initLevel        初始事务级别
         * @throws SQLException 可能产生的异常
         */
        TransactionMeta(IConnectionHolder connectionHolder, JDBC.TRANSACTION initLevel) throws SQLException {
            this.connectionHolder = connectionHolder;
            if (this.connectionHolder.getConnection().getAutoCommit()) {
                this.connectionHolder.getConnection().setAutoCommit(false);
            }
            if (initLevel != null) {
                if (initLevel.getLevel() != connectionHolder.getConnection().getTransactionIsolation()) {
                    this.connectionHolder.getConnection().setTransactionIsolation(initLevel.getLevel());
                }
            }
        }

        /**
         * 释放数据源、连接资源
         */
        void release() throws SQLException {
            try {
                if (this.connectionHolder != null) {
                    this.connectionHolder.release();
                }
            } finally {
                this.connectionHolder = null;
            }
        }
    }
}
