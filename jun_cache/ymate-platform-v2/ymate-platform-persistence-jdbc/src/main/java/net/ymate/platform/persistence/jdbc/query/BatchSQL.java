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
package net.ymate.platform.persistence.jdbc.query;

import net.ymate.platform.persistence.Params;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量更新SQL语句及参数对象
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/11 下午2:25
 * @version 1.0
 */
public final class BatchSQL {

    private String __batchSQL;

    private List<Params> __params;

    private List<String> __sqls;

    public static BatchSQL create(String batchSQL) {
        return new BatchSQL(batchSQL);
    }

    public static BatchSQL create() {
        return new BatchSQL(null);
    }

    private BatchSQL(String batchSQL) {
        this.__sqls = new ArrayList<String>();
        this.__params = new ArrayList<Params>();
        this.__batchSQL = batchSQL;
    }

    public List<Params> params() {
        return this.__params;
    }

    public String getSQL() {
        return this.__batchSQL;
    }

    public List<String> getSQLs() {
        return this.__sqls;
    }

    public BatchSQL addParameter(Params param) {
        if (StringUtils.isBlank(__batchSQL)) {
            // 构造未设置SQL时将不支持添加批量参数
            throw new UnsupportedOperationException();
        }
        __params.add(param);
        return this;
    }

    public BatchSQL addSQL(String sql) {
        this.__sqls.add(sql);
        return this;
    }
}
