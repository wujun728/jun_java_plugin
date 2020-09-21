/*   
 * Project: OSMP
 * FileName: StringBlock.java
 * version: V1.0
 */
package com.osmp.jdbc.parse.sqlblock;

import java.util.Map;

/**
 * 文本块
 * @author heyu
 *
 */
public class StringBlock implements SQLBlock {
    private String sql;
    
    public StringBlock(String sql) {
        this.sql = sql;
    }

    public String sql(Map<String, Object> params) {
        if(sql == null) return "";
        return sql.trim() + " ";
    }

}
