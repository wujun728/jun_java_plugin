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

/**
 * 分页参数对象
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/7 下午1:20
 * @version 1.0
 */
public final class Page {

    /**
     * 默认分页大小
     */
    public static int DEFAULT_PAGE_SIZE = 20;

    /**
     * 分页大小
     */
    private int __pageSize;

    /**
     * 当前页号
     */
    private int __page;

    /**
     * 是否执行总记录数统计
     */
    private boolean __count;

    public static Page create(int page) {
        return new Page(page);
    }

    public static Page createIfNeed(int page, int pageSize) {
        if (page > 0 && pageSize > 0) {
            return new Page(page).pageSize(pageSize);
        }
        return null;
    }

    private Page(int page) {
        this.__page = page > 0 ? page : 1;
        this.__count = true;
    }

    public int pageSize() {
        return __pageSize > 0 ? __pageSize : DEFAULT_PAGE_SIZE;
    }

    public Page pageSize(int pageSize) {
        this.__pageSize = pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
        return this;
    }

    public int page() {
        return __page;
    }

    public boolean isCount() {
        return __count;
    }

    public Page count(boolean count) {
        this.__count = count;
        return this;
    }
}
