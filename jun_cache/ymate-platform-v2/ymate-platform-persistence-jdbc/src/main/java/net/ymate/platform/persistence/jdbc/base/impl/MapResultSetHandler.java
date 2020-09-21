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

import net.ymate.platform.persistence.jdbc.base.AbstractResultSetHandler;

import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 采用Map存储数据的结果集数据处理接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 2011-10-25下午11:51:38
 * @version 1.0
 */
public class MapResultSetHandler extends AbstractResultSetHandler<Map<String, Object>> {

    protected Map<String, Object> __doProcessResultRow(ResultSet resultSet) throws Exception {
        Map<String, Object> _result = new LinkedHashMap<String, Object>(__doGetColumnCount()); // 要保持字段的顺序!!
        for (int _idx = 0; _idx < __doGetColumnCount(); _idx++) {
            _result.put(_doGetColumnMeta(_idx).getName(), resultSet.getObject(_idx + 1));
        }
        return _result;
    }
}
