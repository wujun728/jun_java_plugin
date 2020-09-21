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
package net.ymate.platform.persistence;

import java.util.List;

/**
 * 统一查询结果封装对象接口定义(支持分页)
 *
 * @author 刘镇 (suninformation@163.com) on 2011-9-24 下午08:32:02
 * @version 1.0
 */
public interface IResultSet<T> {

    /**
     * @return 当前结果集是否可用，即是否为空或元素数量为0
     */
    boolean isResultsAvailable();

    /**
     * @return 返回当前结果集是否已分页
     */
    boolean isPaginated();

    /**
     * @return 返回当前页号
     */
    int getPageNumber();

    /**
     * @return 返回每页记录数
     */
    int getPageSize();

    /**
     * @return 返回总页数
     */
    int getPageCount();

    /**
     * @return 返回总记录数
     */
    long getRecordCount();

    /**
     * @return 返回结果集数据
     */
    List<T> getResultData();
}
