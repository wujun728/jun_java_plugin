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
package net.ymate.platform.persistence.jdbc.base;

import net.ymate.platform.core.util.ExpressionUtils;
import net.ymate.platform.persistence.base.Type;
import net.ymate.platform.persistence.jdbc.DataSourceCfgMeta;
import net.ymate.platform.persistence.jdbc.IConnectionHolder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作器接口抽象实现
 *
 * @author 刘镇 (suninformation@163.com) on 2011-9-22 下午10:19:53
 * @version 1.0
 */
public abstract class AbstractOperator implements IOperator {

    private static final Log _LOG = LogFactory.getLog(AbstractOperator.class);

    protected String sql;

    private IConnectionHolder connectionHolder;

    private IAccessorConfig accessorConfig;

    private List<SQLParameter> parameters;

    protected long expenseTime;

    /**
     * 是否已执行
     */
    protected boolean executed;

    public AbstractOperator(String sql, IConnectionHolder connectionHolder) {
        this(sql, connectionHolder, null);
    }

    public AbstractOperator(String sql, IConnectionHolder connectionHolder, IAccessorConfig accessorConfig) {
        this.sql = sql;
        this.connectionHolder = connectionHolder;
        this.accessorConfig = accessorConfig;
        this.parameters = new ArrayList<SQLParameter>();
    }

    public void execute() throws Exception {
        if (!this.executed) {
            StopWatch _time = new StopWatch();
            _time.start();
            int _effectCounts = 0;
            try {
                _effectCounts = __doExecute();
                // 执行过程未发生异常将标记已执行，避免重复执行
                this.executed = true;
            } finally {
                _time.stop();
                this.expenseTime = _time.getTime();
                //
                DataSourceCfgMeta _meta = this.connectionHolder.getDataSourceCfgMeta();
                if (_meta.isShowSQL()) {
                    String _logStr = ExpressionUtils.bind("[${sql}]${param}[${count}][${time}]")
                            .set("sql", StringUtils.defaultIfBlank(this.sql, "@NULL"))
                            .set("param", __doSerializeParameters())
                            .set("count", _effectCounts + "")
                            .set("time", this.expenseTime + "ms").getResult();
                    StringBuilder _stackSB = new StringBuilder(_logStr);
                    if (_meta.isStackTraces()) {
                        boolean _packageFilter = StringUtils.isNotBlank(_meta.getStackTracePackage());
                        StackTraceElement[] _stacks = new Throwable().getStackTrace();
                        if (_stacks != null && _stacks.length > 0) {
                            int _depth = _meta.getStackTraceDepth() <= 0 ? _stacks.length : (_meta.getStackTraceDepth() > _stacks.length ? _stacks.length : _meta.getStackTraceDepth());
                            if (_depth > 0) {
                                for (int _idx = 0; _idx < _depth; _idx++) {
                                    if (_packageFilter && (!StringUtils.startsWith(_stacks[_idx].getClassName(), _meta.getStackTracePackage()) || StringUtils.contains(_stacks[_idx].getClassName(), "$$EnhancerByCGLIB$$"))) {
                                        continue;
                                    }
                                    _stackSB.append("\n\t--> ").append(_stacks[_idx]);
                                }
                            }
                        }
                    }
                    _LOG.info(_stackSB.toString());
                }
            }
        }
    }

    protected String __doSerializeParameters() {
        return this.parameters.toString();
    }

    /**
     * @return 执行具体的操作过程，并返回影响行数
     * @throws Exception 执行过程中产生的异常
     */
    protected abstract int __doExecute() throws Exception;

    protected void __doSetParameters(PreparedStatement statement) throws SQLException {
        int _idx = 1;
        for (SQLParameter _param : this.getParameters()) {
            if (_param.getValue() == null) {
                statement.setNull(_idx++, 0);
            } else if (_param.getType() != null && !Type.FIELD.UNKNOW.equals(_param.getType())) {
                statement.setObject(_idx++, _param.getValue(), _param.getType().getType());
            } else {
                statement.setObject(_idx++, _param.getValue());
            }
        }
    }

    public boolean isExecuted() {
        return executed;
    }

    public String getSQL() {
        return sql;
    }

    public IAccessorConfig getAccessorConfig() {
        return accessorConfig;
    }

    public void setAccessorConfig(IAccessorConfig accessorConfig) {
        this.accessorConfig = accessorConfig;
    }

    public IConnectionHolder getConnectionHolder() {
        return connectionHolder;
    }

    public long getExpenseTime() {
        return expenseTime;
    }

    public List<SQLParameter> getParameters() {
        return this.parameters;
    }

    public IOperator addParameter(SQLParameter parameter) {
        if (parameter != null) {
            this.parameters.add(parameter);
        }
        return this;
    }

    public IOperator addParameter(Object parameter) {
        if (parameter == null) {
            this.parameters.add(new SQLParameter(Type.FIELD.UNKNOW, null));
        } else if (parameter instanceof SQLParameter) {
            this.parameters.add((SQLParameter) parameter);
        } else {
            this.parameters.add(new SQLParameter(parameter));
        }
        return this;
    }
}
