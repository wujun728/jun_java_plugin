/*   
 * Project: OSMP
 * FileName: InBlock.java
 * version: V1.0
 */
package com.osmp.jdbc.parse.sqlblock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * in条件块
 * @author heyu
 *
 */
public class InBlock implements SQLBlock {

    private List<SQLBlock> sqlBlocks = new ArrayList<SQLBlock>();
    //in条件参数属性
    private String filterProperty;
    //in条件分隔符
    private String separator;
    
    public InBlock(String filterProperty, String separator) {
        this.filterProperty = filterProperty;
        this.separator = separator;
    }
    
    public void addSqlBlock(SQLBlock sqlblock){
        if(sqlblock == null) return;
        sqlBlocks.add(sqlblock);
    }
    
    public void addSqlBlocks(List<SQLBlock> sqlblocks){
        if(sqlblocks == null) return;
        sqlBlocks.addAll(sqlblocks);
    }
    
    @Override
    public String sql(Map<String, Object> params) {
        StringBuffer sb = new StringBuffer();
        for(SQLBlock block : sqlBlocks){
            sb.append(block.sql(params));
        }
        String sqlAll = sb.toString();
        if(filterProperty == null || separator == null || "".equals(filterProperty.trim()) || "".equals(separator.trim())) return sqlAll;
        if(sqlAll.indexOf(":"+filterProperty) < 0) return sqlAll;
        Object vals = params.get(filterProperty);
        if(vals == null) return sqlAll;
        String[] arr = vals.toString().split(separator);
        params.put(filterProperty, Arrays.asList(arr));
        return sqlAll;
    }

}
