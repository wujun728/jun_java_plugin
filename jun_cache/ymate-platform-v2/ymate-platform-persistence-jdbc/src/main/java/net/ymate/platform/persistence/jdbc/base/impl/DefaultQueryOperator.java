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
package net.ymate.platform.persistence.jdbc.base.impl;

import net.ymate.platform.persistence.jdbc.IConnectionHolder;
import net.ymate.platform.persistence.jdbc.JDBC;
import net.ymate.platform.persistence.jdbc.base.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * 数据库查询操作器接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 2011-9-23 上午09:32:37
 * @version 1.0
 */
public class DefaultQueryOperator<T> extends AbstractOperator implements IQueryOperator<T> {

    private static final Log _LOG = LogFactory.getLog(DefaultQueryOperator.class);

    private IResultSetHandler<T> resultSetHandler;

    private List<T> resultSet;

    private int maxRow;

    public DefaultQueryOperator(String sql, IConnectionHolder connectionHolder, IResultSetHandler<T> resultSetHandler) {
        this(sql, connectionHolder, null, resultSetHandler);
    }

    public DefaultQueryOperator(String sql, IConnectionHolder connectionHolder, IResultSetHandler<T> resultSetHandler, int maxRow) {
        this(sql, connectionHolder, null, resultSetHandler, maxRow);
    }

    public DefaultQueryOperator(String sql, IConnectionHolder connectionHolder, IAccessorConfig accessorConfig, IResultSetHandler<T> resultSetHandler) {
        this(sql, connectionHolder, accessorConfig, resultSetHandler, 0);
    }

    public DefaultQueryOperator(String sql, IConnectionHolder connectionHolder, IAccessorConfig accessorConfig, IResultSetHandler<T> resultSetHandler, int maxRow) {
        super(sql, connectionHolder, accessorConfig);
        this.resultSetHandler = resultSetHandler;
        this.maxRow = maxRow;
    }

    protected int __doExecute() throws Exception {
        PreparedStatement _statement = null;
        ResultSet _resultSet = null;
        AccessorEventContext _context = null;
        boolean _hasEx = false;
        try {
            IAccessor _accessor = new BaseAccessor(this.getAccessorConfig());
            _statement = _accessor.getPreparedStatement(this.getConnectionHolder().getConnection(), this.getSQL());
            if (this.maxRow > 0) {
                _statement.setMaxRows(this.maxRow);
            }
            __doSetParameters(_statement);
            if (this.getAccessorConfig() != null) {
                this.getAccessorConfig().beforeStatementExecution(_context = new AccessorEventContext(_statement, JDBC.DB_OPERATION_TYPE.QUERY));
            }
            this.resultSet = this.getResultSetHandler().handle(_resultSet = _statement.executeQuery());
            return this.resultSet.size();
        } catch (Exception ex) {
            _hasEx = true;
            throw ex;
        } finally {
            if (!_hasEx && this.getAccessorConfig() != null && _context != null) {
                this.getAccessorConfig().afterStatementExecution(_context);
            }
            if (_resultSet != null) {
                _resultSet.close();
            }
            if (_statement != null) {
                _statement.close();
            }
        }
    }

    public IResultSetHandler<T> getResultSetHandler() {
        return resultSetHandler;
    }

    public List<T> getResultSet() {
        return resultSet;
    }

    public int getMaxRow() {
        return maxRow;
    }
}
