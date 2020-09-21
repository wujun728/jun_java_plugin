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

import net.ymate.platform.persistence.jdbc.base.impl.ArrayResultSetHandler;
import net.ymate.platform.persistence.jdbc.base.impl.MapResultSetHandler;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * 结果集数据处理接口，用于完成对JDBC结果集原始数据的二次加工
 *
 * @author 刘镇 (suninformation@163.com) on 2010-6-2 下午02:16:09
 * @version 1.0
 */
public interface IResultSetHandler<T> {

    IResultSetHandler<Object[]> ARRAY = new ArrayResultSetHandler();

    IResultSetHandler<Map<String, Object>> MAP = new MapResultSetHandler();

    /**
     * @param resultSet 查询结果集
     * @return 执行结果集处理过程，并返回查询结果，决不为NULL
     * @throws Exception 可能产生的异常
     */
    List<T> handle(ResultSet resultSet) throws Exception;
}
