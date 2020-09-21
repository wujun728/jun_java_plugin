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
package net.ymate.platform.persistence.jdbc.scaffold;

import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.persistence.jdbc.IConnectionHolder;
import net.ymate.platform.persistence.jdbc.IDatabase;
import net.ymate.platform.persistence.jdbc.ISession;
import net.ymate.platform.persistence.jdbc.ISessionExecutor;
import net.ymate.platform.persistence.jdbc.base.IResultSetHandler;
import net.ymate.platform.persistence.jdbc.query.SQL;
import net.ymate.platform.persistence.jdbc.support.ResultSetHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 2017/10/19 下午3:34
 * @version 1.0
 */
public class TableInfo {

    private static final Log _LOG = LogFactory.getLog(TableInfo.class);

    public static List<String> getTableNames(IDatabase database) throws Exception {
        return database.openSession(new ISessionExecutor<List<String>>() {
            @Override
            public List<String> execute(ISession session) throws Exception {
                String _dbType = session.getConnectionHolder().getDialect().getName();
                String _sql = null;
                if ("mysql".equalsIgnoreCase(_dbType)) {
                    _sql = "show full tables where Table_type='BASE TABLE'";
                } else if ("oracle".equalsIgnoreCase(_dbType)) {
                    _sql = "select t.table_name from user_tables t";
                } else if ("sqlserver".equalsIgnoreCase(_dbType)) {
                    _sql = "select name from sysobjects where xtype='U'";
                } else {
                    throw new Error("The current database \"" + _dbType + "\" type not supported");
                }
                final List<String> _results = new ArrayList<String>();
                ResultSetHelper _helper = ResultSetHelper.bind(session.find(SQL.create(_sql), IResultSetHandler.ARRAY));
                if (_helper != null) {
                    _helper.forEach(new ResultSetHelper.ItemHandler() {
                        public boolean handle(ResultSetHelper.ItemWrapper wrapper, int row) throws Exception {
                            _results.add(wrapper.getAsString(0));
                            return true;
                        }
                    });
                }
                return _results;
            }
        });
    }

    public static List<String> getViewNames(IDatabase database) throws Exception {
        return database.openSession(new ISessionExecutor<List<String>>() {
            @Override
            public List<String> execute(ISession session) throws Exception {
                String _dbType = session.getConnectionHolder().getDialect().getName();
                String _sql = null;
                if ("mysql".equalsIgnoreCase(_dbType)) {
                    _sql = "show full tables where Table_type='VIEW'";
                } else if ("oracle".equalsIgnoreCase(_dbType)) {
                    _sql = "select view_name from user_views";
                } else if ("sqlserver".equalsIgnoreCase(_dbType)) {
                    _sql = "select name from sysobjects where xtype='V'";
                } else {
                    throw new Error("The current database \"" + _dbType + "\" type not supported");
                }
                final List<String> _results = new ArrayList<String>();
                ResultSetHelper _helper = ResultSetHelper.bind(session.find(SQL.create(_sql), IResultSetHandler.ARRAY));
                if (_helper != null) {
                    _helper.forEach(new ResultSetHelper.ItemHandler() {
                        public boolean handle(ResultSetHelper.ItemWrapper wrapper, int row) throws Exception {
                            _results.add(wrapper.getAsString(0));
                            return true;
                        }
                    });
                }
                return _results;
            }
        });
    }

    public static TableInfo create(IConnectionHolder connectionHolder, ConfigInfo configInfo, String tableName, boolean view) throws Exception {
        DatabaseMetaData _databaseMetaData = connectionHolder.getConnection().getMetaData();
        //
        Statement _statement = null;
        //
        List<String> _primaryKeys = new LinkedList<String>();
        try {
            if (!view) {
                ResultSet _resultSet = _databaseMetaData.getPrimaryKeys(configInfo.getDbName(),
                        connectionHolder.getDialect().getName().equalsIgnoreCase("oracle") ? configInfo.getDbUserName().toUpperCase() : configInfo.getDbUserName(), tableName);
                if (_resultSet == null) {
                    System.err.println("Database table \"" + tableName + "\" primaryKey resultSet is null, ignored");
                    return null;
                } else {
                    while (_resultSet.next()) {
                        _primaryKeys.add(_resultSet.getString(4).toLowerCase());
                    }
                    if (_primaryKeys.isEmpty()) {
                        System.err.println("Database table \"" + tableName + "\" does not set the primary key, ignored");
                        return null;
                    }
                }
            }
            //
            _statement = connectionHolder.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet _resultSet = _statement.executeQuery("SELECT * FROM ".concat(connectionHolder.getDialect().wrapIdentifierQuote(tableName)));
            //
            return new TableInfo(configInfo.getDbName(), configInfo.getDbUserName(), tableName, _primaryKeys, ColumnInfo.create(configInfo, connectionHolder.getDialect().getName(), tableName, _primaryKeys, _databaseMetaData, _resultSet.getMetaData()));
        } finally {
            if (_statement != null) {
                try {
                    _statement.close();
                } catch (SQLException e) {
                    _LOG.warn("", RuntimeUtils.unwrapThrow(e));
                }
            }
        }
    }

    private String catalog;

    private String schema;

    private String name;

    private List<String> pkSet;

    private Map<String, ColumnInfo> fieldMap;

    public TableInfo(String catalog, String schema, String name, List<String> pkSet, Map<String, ColumnInfo> fieldMap) {
        this.catalog = catalog;
        this.schema = schema;
        this.name = name;
        this.pkSet = pkSet;
        this.fieldMap = fieldMap;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getSchema() {
        return schema;
    }

    public String getName() {
        return name;
    }

    public List<String> getPkSet() {
        return pkSet;
    }

    public Map<String, ColumnInfo> getFieldMap() {
        return fieldMap;
    }
}
