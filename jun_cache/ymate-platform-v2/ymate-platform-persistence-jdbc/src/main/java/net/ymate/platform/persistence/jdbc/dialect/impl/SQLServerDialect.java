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
package net.ymate.platform.persistence.jdbc.dialect.impl;

import net.ymate.platform.core.util.ExpressionUtils;
import net.ymate.platform.persistence.jdbc.JDBC;
import net.ymate.platform.persistence.jdbc.dialect.AbstractDialect;

/**
 * SQLServer2005及以上数据库方言接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 2012-4-19 下午3:38:40
 * @version 1.0
 */
public class SQLServerDialect extends AbstractDialect {

    public SQLServerDialect() {
        this.identifierQuote_begin = "[";
        this.identifierQuote_end = "]";
    }

    public String getName() {
        return JDBC.DATABASE.SQLSERVER.name();
    }

    @Override
    public String buildPagedQuerySQL(String originSql, int page, int pageSize) {
        int _limit = ((page - 1) * pageSize);
        boolean _position = originSql.toUpperCase().indexOf("SELECT") == originSql.toUpperCase().indexOf("SELECT DISTINCT");
        String _tmpSQL = originSql.substring((_position ? 15 : 6));
        return ExpressionUtils.bind("SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY __tc__) __rn__, * FROM (SELECT TOP ${_limit} 0 __tc__, ${_sql}) t) tt WHERE __rn__ > ${_offset}")
                .set("_limit", _limit + pageSize + "")
                .set("_sql", _tmpSQL)
                .set("_offset", _limit + "").getResult();
    }
}
