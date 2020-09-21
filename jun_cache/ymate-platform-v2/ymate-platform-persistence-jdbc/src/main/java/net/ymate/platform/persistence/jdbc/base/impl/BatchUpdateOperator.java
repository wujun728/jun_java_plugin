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
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库批量更新操作器实现
 *
 * @author 刘镇 (suninformation@163.com) on 2011-9-23 下午01:15:43
 * @version 1.0
 */
public class BatchUpdateOperator extends AbstractOperator implements IBatchUpdateOperator {

    private static final Log _LOG = LogFactory.getLog(BatchUpdateOperator.class);

    private int[] effectCounts;

    private List<String> __batchSQL;

    private List<SQLBatchParameter> __batchParameters;

    public BatchUpdateOperator(IConnectionHolder connectionHolder) {
        this(null, connectionHolder, null);
    }

    public BatchUpdateOperator(IConnectionHolder connectionHolder, IAccessorConfig accessorConfig) {
        this(null, connectionHolder, accessorConfig);
    }

    public BatchUpdateOperator(String sql, IConnectionHolder connectionHolder) {
        this(sql, connectionHolder, null);
    }

    public BatchUpdateOperator(String sql, IConnectionHolder connectionHolder, IAccessorConfig accessorConfig) {
        super(sql, connectionHolder, accessorConfig);
        this.__batchSQL = new ArrayList<String>();
        this.__batchParameters = new ArrayList<SQLBatchParameter>();
    }

    @Override
    protected String __doSerializeParameters() {
        List<Object> _params = new ArrayList<Object>(__batchParameters);
        _params.addAll(__batchSQL);
        return _params.toString();
    }

    protected int __doExecute() throws Exception {
        Statement _statement = null;
        AccessorEventContext _context = null;
        boolean _hasEx = false;
        try {
            IAccessor _accessor = new BaseAccessor(this.getAccessorConfig());
            if (StringUtils.isNotBlank(this.getSQL())) {
                _statement = _accessor.getPreparedStatement(this.getConnectionHolder().getConnection(), this.getSQL());
                //
                for (SQLBatchParameter _batchParam : this.__batchParameters) {
                    for (int i = 0; i < _batchParam.getParameters().size(); i++) {
                        SQLParameter _param = _batchParam.getParameters().get(i);
                        if (_param.getValue() == null) {
                            ((PreparedStatement) _statement).setNull(i + 1, 0);
                        } else {
                            ((PreparedStatement) _statement).setObject(i + 1, _param.getValue());
                        }
                    }
                    ((PreparedStatement) _statement).addBatch();
                }
            } else {
                _statement = _accessor.getStatement(this.getConnectionHolder().getConnection());
            }
            //
            for (String _batchSQL : this.__batchSQL) {
                _statement.addBatch(_batchSQL);
            }
            //
            if (this.getAccessorConfig() != null) {
                this.getAccessorConfig().beforeStatementExecution(_context = new AccessorEventContext(_statement, JDBC.DB_OPERATION_TYPE.BATCH_UPDATE));
            }
            effectCounts = _statement.executeBatch();
            // 累计受影响的总记录数
            int _count = 0;
            for (int _c : effectCounts) {
                _count += _c;
            }
            return _count;
        } catch (Exception ex) {
            _hasEx = true;
            throw ex;
        } finally {
            if (!_hasEx && this.getAccessorConfig() != null && _context != null) {
                this.getAccessorConfig().afterStatementExecution(_context);
            }
            if (_statement != null) {
                _statement.clearBatch();
                _statement.close();
            }
        }
    }

    public int[] getEffectCounts() {
        return effectCounts;
    }

    public IBatchUpdateOperator addBatchSQL(String sql) {
        this.__batchSQL.add(sql);
        return this;
    }

    public IBatchUpdateOperator addBatchParameter(SQLBatchParameter parameter) {
        if (StringUtils.isBlank(this.getSQL())) {
            // 构造未设置SQL时将不支持添加批量参数
            throw new UnsupportedOperationException();
        }
        if (parameter != null) {
            this.__batchParameters.add(parameter);
        }
        return this;
    }

    public List<SQLBatchParameter> getBatchParameters() {
        return __batchParameters;
    }

    @Override
    public IOperator addParameter(Object parameter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IOperator addParameter(SQLParameter parameter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<SQLParameter> getParameters() {
        throw new UnsupportedOperationException();
    }
}
