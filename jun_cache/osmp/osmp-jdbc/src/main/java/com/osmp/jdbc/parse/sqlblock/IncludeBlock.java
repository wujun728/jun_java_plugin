/*   
 * Project: OSMP
 * FileName: IncludeBlock.java
 * version: V1.0
 */
package com.osmp.jdbc.parse.sqlblock;

import java.util.Map;

import com.osmp.jdbc.define.tool.JdbcFinderHolder;
import com.osmp.jdbc.support.JdbcFinderManager;

/**
 * include块
 * @author heyu
 *
 */
public class IncludeBlock implements SQLBlock {
    
    //引入sql的id
    private String includeSql = "";
    
    public IncludeBlock(String sql){
        this.includeSql = sql;
    }
    
    @Override
    public String sql(Map<String, Object> params) {
        JdbcFinderManager finder = JdbcFinderHolder.get();
        if(finder == null) return "";
        String sqlAll = finder.getQueryStringByStatementKey(includeSql, params);
        if(sqlAll == null || "".equals(sqlAll)) return "";
        return sqlAll + " ";
    }

}
