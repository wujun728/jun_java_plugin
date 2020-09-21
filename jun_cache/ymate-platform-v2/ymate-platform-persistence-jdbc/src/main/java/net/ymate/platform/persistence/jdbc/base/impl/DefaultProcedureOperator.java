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

import net.ymate.platform.core.util.ExpressionUtils;
import net.ymate.platform.persistence.jdbc.IConnectionHolder;
import net.ymate.platform.persistence.jdbc.JDBC;
import net.ymate.platform.persistence.jdbc.base.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库存储过程操作器接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 16/12/8 上午1:04
 * @version 1.0
 */
public class DefaultProcedureOperator<T> extends AbstractOperator implements IProcedureOperator<T> {

    private static final Log _LOG = LogFactory.getLog(DefaultProcedureOperator.class);

    /**
     * 存储过程OUT参数类型集合
     */
    private List<Integer> __outParams = new ArrayList<Integer>();

    private IOutResultProcessor __resultProcessor;

    private IResultSetHandler<T> __resultSetHandler;

    private List<List<T>> __resultSets = new ArrayList<List<T>>();

    public DefaultProcedureOperator(String sql, IConnectionHolder connectionHolder) {
        super(sql, connectionHolder);
    }

    public DefaultProcedureOperator(String sql, IConnectionHolder connectionHolder, IAccessorConfig accessorConfig) {
        super(sql, connectionHolder, accessorConfig);
    }

    public void execute() throws Exception {
        if (!this.executed) {
            StopWatch _time = new StopWatch();
            _time.start();
            try {
                __doExecute();
                // 执行过程未发生异常将标记已执行，避免重复执行
                this.executed = true;
            } finally {
                _time.stop();
                this.expenseTime = _time.getTime();
                //
                if (this.getConnectionHolder().getDataSourceCfgMeta().isShowSQL()) {
                    _LOG.info(ExpressionUtils.bind("[${sql}]${param}[${count}][${time}]")
                            .set("sql", StringUtils.defaultIfBlank(this.sql, "@NULL"))
                            .set("param", __doSerializeParameters())
                            .set("count", "N/A")
                            .set("time", this.expenseTime + "ms").getResult());
                }
            }
        }
    }

    public IProcedureOperator<T> execute(IResultSetHandler<T> resultSetHandler) throws Exception {
        __resultSetHandler = resultSetHandler;
        this.execute();
        return this;
    }

    public IProcedureOperator<T> execute(IOutResultProcessor resultProcessor) throws Exception {
        __resultProcessor = resultProcessor;
        this.execute();
        return this;
    }

    protected int __doExecute() throws Exception {
        CallableStatement _statement = null;
        AccessorEventContext _context = null;
        boolean _hasEx = false;
        try {
            IAccessor _accessor = new BaseAccessor(this.getAccessorConfig());
            _statement = _accessor.getCallableStatement(this.getConnectionHolder().getConnection(), __doBuildCallSQL());
            __doSetParameters(_statement);
            __doRegisterOutParams(_statement);
            if (this.getAccessorConfig() != null) {
                this.getAccessorConfig().beforeStatementExecution(_context = new AccessorEventContext(_statement, JDBC.DB_OPERATION_TYPE.PROCEDURE));
            }
            boolean _flag = _statement.execute();
            if (_flag) {
                do {
                    ResultSet _result = _statement.getResultSet();
                    if (_result != null) {
                        __resultSets.add(__resultSetHandler.handle(_result));
                        _result.close();
                    }
                } while (_statement.getMoreResults());
            } else {
                int _idx = this.getParameters().size() + 1;
                for (Integer _paramType : __outParams) {
                    __resultProcessor.process(_idx, _paramType, _statement.getObject((_idx)));
                    _idx++;
                }
            }
            return -1;
        } catch (Exception ex) {
            _hasEx = true;
            throw ex;
        } finally {
            if (!_hasEx && this.getAccessorConfig() != null && _context != null) {
                this.getAccessorConfig().afterStatementExecution(_context);
            }
            if (_statement != null) {
                _statement.close();
            }
        }
    }

    /**
     * @return 构建存储过程CALL语句(根据不同的数据库, 可由子类重新实现)
     */
    protected String __doBuildCallSQL() {
        List<String> _params = new ArrayList<String>();
        for (int i = 0; i < this.getParameters().size() + this.__outParams.size(); i++) {
            _params.add("?");
        }
        this.sql = "{CALL " + this.getSQL() + (_params.isEmpty() ? "()" : "(" + StringUtils.join(_params, ',') + ")") + "}";
        return this.sql;
    }

    /**
     * 注册存储过程输出的参数(从最后一个输入参数后开始, 根据不同的数据库，可由子类重新实现)
     *
     * @param statement CallableStatement
     * @throws SQLException 可能产生的任何异常
     */
    protected void __doRegisterOutParams(CallableStatement statement) throws SQLException {
        int _idx = this.getParameters().size() + 1;
        for (Integer _type : __outParams) {
            statement.registerOutParameter(_idx++, _type);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public IProcedureOperator<T> addParameter(SQLParameter parameter) {
        return (IProcedureOperator<T>) super.addParameter(parameter);
    }

    @Override
    @SuppressWarnings("unchecked")
    public IProcedureOperator<T> addParameter(Object parameter) {
        return (IProcedureOperator<T>) super.addParameter(parameter);
    }

    public IProcedureOperator<T> addOutParameter(Integer sqlParamType) {
        this.__outParams.add(sqlParamType);
        return this;
    }

    public IProcedureOperator<T> setOutResultProcessor(IOutResultProcessor outResultProcessor) {
        __resultProcessor = outResultProcessor;
        return this;
    }

    public IProcedureOperator<T> setResultSetHandler(IResultSetHandler<T> resultSetHandler) {
        __resultSetHandler = resultSetHandler;
        return this;
    }

    public List<List<T>> getResultSets() {
        return __resultSets;
    }
}
