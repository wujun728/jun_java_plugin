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

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 结果集数据处理接口抽象实现
 *
 * @author 刘镇 (suninformation@163.com) on 2011-9-22 下午04:14:15
 * @version 1.0
 */
public abstract class AbstractResultSetHandler<T> implements IResultSetHandler<T> {

    private int __columnCount;

    private ColumnMeta[] __columnMetas;

    public List<T> handle(ResultSet resultSet) throws Exception {
        // 分析结果集字段信息
        ResultSetMetaData _metaData = resultSet.getMetaData();
        __columnCount = _metaData.getColumnCount();
        __columnMetas = new ColumnMeta[__columnCount];
        for (int _idx = 0; _idx < __columnCount; _idx++) {
            __columnMetas[_idx] = new ColumnMeta(_metaData.getColumnLabel(_idx + 1), _metaData.getColumnType(_idx + 1));
        }
        //
        List<T> _results = new ArrayList<T>();
        while (resultSet.next()) {
            _results.add(this.__doProcessResultRow(resultSet));
        }
        return _results;
    }

    /**
     * @param resultSet 数据结果集对象，切勿对其进行游标移动等操作，仅约定用于提取当前行字段数据
     * @return 处理当前行结果集数据并返回指定的T类型对象
     * @throws Exception 可能产生的异常
     */
    protected abstract T __doProcessResultRow(ResultSet resultSet) throws Exception;

    protected int __doGetColumnCount() {
        return __columnCount;
    }

    protected ColumnMeta _doGetColumnMeta(int idx) {
        return __columnMetas[idx];
    }

    /**
     * 字段描述对象
     */
    public static class ColumnMeta {
        private String name;
        private int type;

        public ColumnMeta(String name, int type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public int getType() {
            return type;
        }
    }
}
