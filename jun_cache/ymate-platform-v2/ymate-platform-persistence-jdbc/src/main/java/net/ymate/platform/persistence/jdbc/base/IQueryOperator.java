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

import java.util.List;

/**
 * 数据库查询操作器接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 2010-12-25 下午02:00:07
 * @version 1.0
 */
public interface IQueryOperator<T> extends IOperator {

    /**
     * @return 返回结果集数据处理对象
     */
    IResultSetHandler<T> getResultSetHandler();

    /**
     * @return 返回查询结果
     */
    List<T> getResultSet();

    /**
     * @return 结果集返回最大记录数，默认为0，表示没有任何限制
     */
    int getMaxRow();
}
