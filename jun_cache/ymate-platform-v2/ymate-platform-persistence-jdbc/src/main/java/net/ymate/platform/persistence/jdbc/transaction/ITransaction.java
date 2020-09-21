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

import net.ymate.platform.persistence.jdbc.IConnectionHolder;
import net.ymate.platform.persistence.jdbc.JDBC;

import java.sql.SQLException;

/**
 * 事务处理接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 2011-9-6 下午03:59:38
 * @version 1.0
 */
public interface ITransaction {

    /**
     * @return 获取事务级别
     */
    JDBC.TRANSACTION getLevel();

    /**
     * 设置事务级别
     *
     * @param level 事务级别
     */
    void setLevel(JDBC.TRANSACTION level);

    /**
     * @return 获取事务Id
     */
    String getId();

    /**
     * 提交事务
     *
     * @throws SQLException 可能产生的异常
     */
    void commit() throws SQLException;

    /**
     * 回滚事务
     *
     * @throws SQLException 可能产生的异常
     */
    void rollback() throws SQLException;

    /**
     * 关闭事务（连接）
     *
     * @throws SQLException 可能产生的异常
     */
    void close() throws SQLException;

    /**
     * @param dsName 数据源名称
     * @return 获取数据库连接持有者对象
     */
    IConnectionHolder getConnectionHolder(String dsName);

    /**
     * 注册一个ConnectionHolder对象由事务管理(相同数据源仅允许注册一次)
     *
     * @param connectionHolder 数据库连接持有者对象
     * @throws SQLException 可能产生的异常
     */
    void registerConnectionHolder(IConnectionHolder connectionHolder) throws SQLException;
}
