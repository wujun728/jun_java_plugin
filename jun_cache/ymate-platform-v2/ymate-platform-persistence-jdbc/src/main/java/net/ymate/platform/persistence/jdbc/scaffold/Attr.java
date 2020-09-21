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
package net.ymate.platform.persistence.jdbc.scaffold;

import net.ymate.platform.core.util.ClassUtils;

/**
 * @author 刘镇 (suninformation@163.com) on 2017/10/19 下午3:19
 * @version 1.0
 */
public class Attr {

    private String varType;

    private String varName;

    private String columnName;

    private boolean autoIncrement;

    private boolean signed;

    private int precision;

    private int scale;

    private boolean nullable;

    private String defaultValue;

    private String remarks;

    private boolean readonly;

    public Attr(String varType, String varName, String columnName, boolean autoIncrement, boolean signed, int precision, int scale, boolean nullable, String defaultValue, String remarks) {
        this.varName = varName;
        this.varType = varType;
        this.columnName = columnName;
        this.autoIncrement = autoIncrement;
        this.signed = signed;
        try {
            if (!signed && !ClassUtils.isSubclassOf(Class.forName(varType), Number.class)) {
                this.signed = true;
            }
        } catch (Exception ignored) {
        }
        this.precision = precision;
        this.scale = scale;
        this.nullable = nullable;
        this.defaultValue = defaultValue;
        this.remarks = remarks;
    }

    public String getVarType() {
        return varType;
    }

    public String getVarName() {
        return varName;
    }

    public String getColumnName() {
        return columnName;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public boolean isSigned() {
        return signed;
    }

    public int getPrecision() {
        return precision;
    }

    public int getScale() {
        return scale;
    }

    public boolean isNullable() {
        return nullable;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public boolean isReadonly() {
        return readonly;
    }

    @Override
    public String toString() {
        return this.getVarName();
    }
}
