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
package net.ymate.platform.persistence.jdbc;

import net.ymate.platform.core.YMP;
import net.ymate.platform.persistence.IDataSourceRouter;

/**
 * JDBC数据库模块管理器接口
 *
 * @author 刘镇 (suninformation@163.com) on 15/3/29 下午7:06
 * @version 1.0
 */
public interface IDatabase {

    String MODULE_NAME = "persistence.jdbc";

    /**
     * @return 返回所属YMP框架管理器实例
     */
    YMP getOwner();

    /**
     * @return 返回JDBC数据库模块配置对象
     */
    IDatabaseModuleCfg getModuleCfg();

    /**
     * @return 获取默认数据源连接持有者对象
     * @throws Exception 可能产生的异常
     */
    IConnectionHolder getDefaultConnectionHolder() throws Exception;

    /**
     * @param dsName 数据源名称
     * @return 获取由dsName指定的数据源连接持有者对象
     * @throws Exception 可能产生的异常
     */
    IConnectionHolder getConnectionHolder(String dsName) throws Exception;

    /**
     * 安全关闭数据源的连接持有者(确保非事务状态下执行关闭)
     *
     * @param connectionHolder 数据源的连接持有者对象
     * @throws Exception 可能产生的异常
     */
    void releaseConnectionHolder(IConnectionHolder connectionHolder) throws Exception;

    <T> T openSession(ISessionExecutor<T> executor) throws Exception;

    <T> T openSession(String dsName, ISessionExecutor<T> executor) throws Exception;

    <T> T openSession(IConnectionHolder connectionHolder, ISessionExecutor<T> executor) throws Exception;

    <T> T openSession(IDataSourceRouter dataSourceRouter, ISessionExecutor<T> executor) throws Exception;

    /**
     * @return 开启数据库连接会话(注意一定记得关闭会话)
     * @throws Exception 可能产生的异常
     */
    ISession openSession() throws Exception;

    ISession openSession(String dsName) throws Exception;

    ISession openSession(IConnectionHolder connectionHolder) throws Exception;

    ISession openSession(IDataSourceRouter dataSourceRouter) throws Exception;
}
