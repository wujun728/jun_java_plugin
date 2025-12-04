/**   
 * Copyright © 2019 dream horse Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.gitee.mybatis.fl.convert
 * @author: flying-cattle  
 * @date: 2019年4月9日 下午8:13:13 
 */
package com.gitee.flying.cattle.mdg.convert;

import com.gitee.flying.cattle.mdg.convert.ITypeConvert;
/**   
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明：MYSQL 数据库字段类型转换</p>
 * @version: v3.0.0
 * @author: flying-cattle
 *
 * Modification History:
 * Date         		Author        Version          Description
 *---------------------------------------------------------------*
 * 2019年4月9日      		flying-cattle     v3.0.0           initialize
 */
public class MySqlTypeConvert  implements ITypeConvert {

    @Override
    public IColumnType processTypeConvert(DateType dateType, String fieldType) {
        String t = fieldType.toLowerCase();
        if (t.contains("char")) {
            return DbColumnType.STRING;
        } else if (t.contains("bigint")) {
            return DbColumnType.LONG;
        } else if (t.contains("tinyint(1)")) {
            return DbColumnType.BOOLEAN;
        } else if (t.contains("int")) {
            return DbColumnType.INTEGER;
        } else if (t.contains("text")) {
            return DbColumnType.STRING;
        } else if (t.contains("bit")) {
            return DbColumnType.BOOLEAN;
        } else if (t.contains("decimal")) {
            return DbColumnType.BIG_DECIMAL;
        } else if (t.contains("clob")) {
            return DbColumnType.CLOB;
        } else if (t.contains("blob")) {
            return DbColumnType.BLOB;
        } else if (t.contains("binary")) {
            return DbColumnType.BYTE_ARRAY;
        } else if (t.contains("float")) {
            return DbColumnType.FLOAT;
        } else if (t.contains("double")) {
            return DbColumnType.DOUBLE;
        } else if (t.contains("json") || t.contains("enum")) {
            return DbColumnType.STRING;
        } else if (t.contains("date") || t.contains("time") || t.contains("year")) {
            switch (dateType) {
                case ONLY_DATE:
                    return DbColumnType.DATE;
                case SQL_PACK:
                    switch (t) {
                        case "date":
                            return DbColumnType.DATE_SQL;
                        case "time":
                            return DbColumnType.TIME;
                        case "year":
                            return DbColumnType.DATE_SQL;
                        default:
                            return DbColumnType.TIMESTAMP;
                    }
                case TIME_PACK:
                    switch (t) {
                        case "date":
                            return DbColumnType.LOCAL_DATE;
                        case "time":
                            return DbColumnType.LOCAL_TIME;
                        case "year":
                            return DbColumnType.YEAR;
                        default:
                            return DbColumnType.LOCAL_DATE_TIME;
                    }
            }
        }
        return DbColumnType.STRING;
    }
}
