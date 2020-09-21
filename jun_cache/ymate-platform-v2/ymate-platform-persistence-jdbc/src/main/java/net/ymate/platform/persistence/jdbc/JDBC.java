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

import net.ymate.platform.core.Version;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.module.annotation.Module;
import net.ymate.platform.persistence.IDataSourceRouter;
import net.ymate.platform.persistence.jdbc.dialect.IDialect;
import net.ymate.platform.persistence.jdbc.dialect.impl.*;
import net.ymate.platform.persistence.jdbc.impl.*;
import net.ymate.platform.persistence.jdbc.repo.RepoHandler;
import net.ymate.platform.persistence.jdbc.repo.annotation.Repository;
import net.ymate.platform.persistence.jdbc.transaction.Transactions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库模块管理器
 *
 * @author 刘镇 (suninformation@163.com) on 2011-9-10 下午11:45:25
 * @version 1.0
 */
@Module
public class JDBC implements IModule, IDatabase {

    public static final Version VERSION = new Version(2, 0, 3, JDBC.class.getPackage().getImplementationVersion(), Version.VersionType.Release);

    private static final Log _LOG = LogFactory.getLog(JDBC.class);

    private static volatile IDatabase __instance;

    private YMP __owner;

    private IDatabaseModuleCfg __moduleCfg;

    private Map<String, IDataSourceAdapter> __dsCaches;

    private boolean __inited;

    /**
     * @return 返回默认数据库模块管理器实例对象
     */
    public static IDatabase get() {
        if (__instance == null) {
            synchronized (VERSION) {
                if (__instance == null) {
                    __instance = YMP.get().getModule(JDBC.class);
                }
            }
        }
        return __instance;
    }

    /**
     * @param owner YMP框架管理器实例
     * @return 返回指定YMP框架管理器容器内的数据库模块管理器实例
     */
    public static IDatabase get(YMP owner) {
        return owner.getModule(JDBC.class);
    }

    public String getName() {
        return IDatabase.MODULE_NAME;
    }

    public void init(YMP owner) throws Exception {
        if (!__inited) {
            //
            _LOG.info("Initializing ymate-platform-persistence-jdbc-" + VERSION);
            //
            __owner = owner;
            __moduleCfg = new DefaultModuleCfg(owner);
            //
            __owner.registerHandler(Repository.class, new RepoHandler(this));
            //
            __dsCaches = new HashMap<String, IDataSourceAdapter>();
            for (DataSourceCfgMeta _meta : __moduleCfg.getDataSourceCfgs().values()) {
                IDataSourceAdapter _adapter = _meta.getAdapterClass().newInstance();
                _adapter.initialize(_meta);
                // 将数据源适配器添加到缓存
                __dsCaches.put(_meta.getName(), _adapter);
            }
            //
            __inited = true;
        }
    }

    public boolean isInited() {
        return __inited;
    }

    public void destroy() throws Exception {
        if (__inited) {
            __inited = false;
            //
            for (IDataSourceAdapter _adapter : __dsCaches.values()) {
                _adapter.destroy();
            }
            __dsCaches = null;
            __moduleCfg = null;
            __owner = null;
        }
    }

    public YMP getOwner() {
        return __owner;
    }

    public IDatabaseModuleCfg getModuleCfg() {
        return __moduleCfg;
    }

    public IConnectionHolder getDefaultConnectionHolder() throws Exception {
        String _defaultDSName = __moduleCfg.getDataSourceDefaultName();
        return getConnectionHolder(_defaultDSName);
    }

    public IConnectionHolder getConnectionHolder(String dsName) throws Exception {
        IConnectionHolder _returnValue = null;
        if (Transactions.get() != null) {
            _returnValue = Transactions.get().getConnectionHolder(dsName);
            if (_returnValue == null) {
                _returnValue = new DefaultConnectionHolder(__dsCaches.get(dsName));
                Transactions.get().registerConnectionHolder(_returnValue);
            }
        } else {
            _returnValue = new DefaultConnectionHolder(__dsCaches.get(dsName));
        }
        return _returnValue;
    }

    public void releaseConnectionHolder(IConnectionHolder connectionHolder) throws Exception {
        // 需要判断当前连接是否参与事务，若存在事务则不进行关闭操作
        if (Transactions.get() == null) {
            if (connectionHolder != null) {
                connectionHolder.release();
            }
        }
    }

    public <T> T openSession(ISessionExecutor<T> executor) throws Exception {
        return openSession(getDefaultConnectionHolder(), executor);
    }

    public <T> T openSession(String dsName, ISessionExecutor<T> executor) throws Exception {
        return openSession(getConnectionHolder(dsName), executor);
    }

    public <T> T openSession(IConnectionHolder connectionHolder, ISessionExecutor<T> executor) throws Exception {
        ISession _session = new DefaultSession(connectionHolder);
        try {
            return executor.execute(_session);
        } finally {
            _session.close();
        }
    }

    public <T> T openSession(IDataSourceRouter dataSourceRouter, ISessionExecutor<T> executor) throws Exception {
        return openSession(getConnectionHolder(dataSourceRouter.getDataSourceName()), executor);
    }

    public ISession openSession() throws Exception {
        return new DefaultSession(getDefaultConnectionHolder());
    }

    public ISession openSession(String dsName) throws Exception {
        return new DefaultSession(getConnectionHolder(dsName));
    }

    public ISession openSession(IConnectionHolder connectionHolder) throws Exception {
        return new DefaultSession(connectionHolder);
    }

    public ISession openSession(IDataSourceRouter dataSourceRouter) throws Exception {
        return new DefaultSession(getConnectionHolder(dataSourceRouter.getDataSourceName()));
    }

    /////


    /**
     * 数据库类型
     */
    public enum DATABASE {
        MYSQL, ORACLE, SQLSERVER, DB2, SQLLITE, POSTGRESQL, HSQLDB, H2, UNKNOW
    }

    /**
     * 数据库操作类型
     */
    public enum DB_OPERATION_TYPE {
        QUERY, UPDATE, BATCH_UPDATE, PROCEDURE
    }

    /**
     * 数据库事务类型
     */
    public enum TRANSACTION {
        /**
         * 不（使用）支持事务
         */
        NONE(Connection.TRANSACTION_NONE),

        /**
         * 在一个事务中进行查询时，允许读取提交前的数据，数据提交后，当前查询就可以读取到数据，update数据时候并不锁住表
         */
        READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),

        /**
         * 俗称“脏读”（dirty read），在没有提交数据时能够读到已经更新的数据
         */
        READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),
        /**
         * 在一个事务中进行查询时，不允许读取其他事务update的数据，允许读取到其他事务提交的新增数据
         */
        REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),

        /**
         * 在一个事务中进行查询时，不允许任何对这个查询表的数据修改
         */
        SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);

        private int _level;

        /**
         * 构造器
         *
         * @param level 事务级别
         */
        TRANSACTION(int level) {
            this._level = level;
        }

        /**
         * @return the level
         */
        public int getLevel() {
            return _level;
        }

        /**
         * @param level the level to set
         */
        public void setLevel(int level) {
            this._level = level;
        }
    }

    /**
     * 框架提供的已知数据源适配器名称映射
     */
    public static Map<String, String> DS_ADAPTERS;

    /**
     * 框架提供的已知数据库连接驱动
     */
    public static Map<DATABASE, String> DB_DRIVERS;

    /**
     * 框架提供的已知数据库方言
     */
    public static Map<DATABASE, Class<? extends IDialect>> DB_DIALECTS;

    static {
        //
        DS_ADAPTERS = new HashMap<String, String>();
        DS_ADAPTERS.put("default", DefaultDataSourceAdapter.class.getName());
        DS_ADAPTERS.put("jndi", JNDIDataSourceAdapter.class.getName());
        DS_ADAPTERS.put("c3p0", C3P0DataSourceAdapter.class.getName());
        DS_ADAPTERS.put("dbcp", DBCPDataSourceAdapter.class.getName());
        //
        DB_DRIVERS = new HashMap<DATABASE, String>();
        DB_DRIVERS.put(DATABASE.MYSQL, "com.mysql.jdbc.Driver");
        DB_DRIVERS.put(DATABASE.ORACLE, "oracle.jdbc.OracleDriver");
        DB_DRIVERS.put(DATABASE.SQLSERVER, "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        DB_DRIVERS.put(DATABASE.DB2, "com.ibm.db2.jcc.DB2Driver");
        DB_DRIVERS.put(DATABASE.SQLLITE, "org.sqlite.JDBC");
        DB_DRIVERS.put(DATABASE.POSTGRESQL, "org.postgresql.Driver");
        DB_DRIVERS.put(DATABASE.HSQLDB, "org.hsqldb.jdbcDriver");
        DB_DRIVERS.put(DATABASE.H2, "org.h2.Driver");
        //
        DB_DIALECTS = new HashMap<DATABASE, Class<? extends IDialect>>();
        DB_DIALECTS.put(DATABASE.MYSQL, MySQLDialect.class);
        DB_DIALECTS.put(DATABASE.ORACLE, OracleDialect.class);
        DB_DIALECTS.put(DATABASE.SQLSERVER, SQLServerDialect.class);
        DB_DIALECTS.put(DATABASE.DB2, DB2Dialect.class);
        DB_DIALECTS.put(DATABASE.SQLLITE, SQLiteDialect.class);
        DB_DIALECTS.put(DATABASE.POSTGRESQL, PostgreSQLDialect.class);
        DB_DIALECTS.put(DATABASE.HSQLDB, HSQLDBDialect.class);
        DB_DIALECTS.put(DATABASE.H2, H2Dialect.class);
    }
}
