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
package net.ymate.platform.persistence.base;

import java.sql.Types;

/**
 * 数据常量/枚举类型
 *
 * @author 刘镇 (suninformation@163.com) on 15/3/29 下午5:23
 * @version 1.0
 */
public class Type {

    /**
     * 字段类型
     */
    public enum FIELD {
        VARCHAR(Types.VARCHAR),
        CHAR(Types.CHAR),
        TEXT(Types.LONGVARCHAR),
        BOOLEAN(Types.SMALLINT),
        BINARY(Types.BINARY),
        TIMESTAMP(Types.TIMESTAMP),
        DATE(Types.DATE),
        TIME(Types.TIME),
        INT(Types.INTEGER),
        LONG(Types.BIGINT),
        FLOAT(Types.FLOAT),
        NUMBER(Types.NUMERIC),
        UNKNOW(Types.OTHER);

        private int type;

        FIELD(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    /**
     * 操作类型: 查询、更新、批量更新、存储过程
     */
    public enum OPT {
        QUERY, UPDATE // , BATCH_UPDATE, PROC
    }
}
