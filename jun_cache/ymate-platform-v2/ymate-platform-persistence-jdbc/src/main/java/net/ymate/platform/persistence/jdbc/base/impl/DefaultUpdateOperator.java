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

/**
 * 数据库更新操作器接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 2011-9-23 上午10:38:24
 * @version 1.0
 */
public class DefaultUpdateOperator extends AbstractOperator implements IUpdateOperator {

    private static final Log _LOG = LogFactory.getLog(DefaultUpdateOperator.class);

    private int effectCounts;

    public DefaultUpdateOperator(String sql, IConnectionHolder connectionHolder) {
        super(sql, connectionHolder);
    }

    public DefaultUpdateOperator(String sql, IConnectionHolder connectionHolder, IAccessorConfig accessorConfig) {
        super(sql, connectionHolder, accessorConfig);
    }

    protected int __doExecute() throws Exception {
        PreparedStatement _statement = null;
        AccessorEventContext _context = null;
        boolean _hasEx = false;
        try {
            IAccessor _accessor = new BaseAccessor(this.getAccessorConfig());
            _statement = _accessor.getPreparedStatement(this.getConnectionHolder().getConnection(), this.getSQL());
            __doSetParameters(_statement);
            if (this.getAccessorConfig() != null) {
                this.getAccessorConfig().beforeStatementExecution(_context = new AccessorEventContext(_statement, JDBC.DB_OPERATION_TYPE.UPDATE));
            }
            return effectCounts = _statement.executeUpdate();
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

    public int getEffectCounts() {
        return effectCounts;
    }
}
