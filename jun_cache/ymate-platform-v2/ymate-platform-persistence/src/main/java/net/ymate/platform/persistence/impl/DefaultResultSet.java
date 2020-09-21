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
package net.ymate.platform.persistence.impl;

import net.ymate.platform.persistence.IResultSet;

import java.util.Collections;
import java.util.List;

/**
 * 统一查询结果封装对象接口默认实现
 *
 * @author 刘镇 (suninformation@163.com) on 2011-9-24 下午08:32:02
 * @version 1.0
 */
public class DefaultResultSet<T> implements IResultSet<T> {

    private int pageNumber;

    private int pageSize;

    private int pageCount;

    private long recordCount;

    private List<T> resultData;

    /**
     * 构造方法，不采用分页方式
     *
     * @param resultData 结果集
     */
    public DefaultResultSet(List<T> resultData) {
        this(resultData, 0, 0, 0);
    }

    /**
     * 构造方法，采用分页计算
     *
     * @param resultData  当前页数据
     * @param pageNumber  当前页号
     * @param pageSize    每页记录数
     * @param recordCount 总记录数
     */
    public DefaultResultSet(List<T> resultData, int pageNumber, int pageSize, long recordCount) {
        this.resultData = Collections.unmodifiableList(resultData);
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.recordCount = recordCount;
        // pageNumber和pageSize两者均小于0则视为不分页
        if (pageNumber > 0 && pageSize > 0) {
            // 根据记录总数和分页参数计算总页数
            if (recordCount > 0) {
                if (recordCount % pageSize > 0) {
                    this.pageCount = (int) (recordCount / pageSize + 1);
                } else {
                    this.pageCount = (int) (recordCount / pageSize);
                }
            }
        }
    }

    public boolean isResultsAvailable() {
        return resultData != null && !resultData.isEmpty();
    }

    public boolean isPaginated() {
        return pageNumber > 0 && pageSize > 0;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public List<T> getResultData() {
        return resultData;
    }
}
