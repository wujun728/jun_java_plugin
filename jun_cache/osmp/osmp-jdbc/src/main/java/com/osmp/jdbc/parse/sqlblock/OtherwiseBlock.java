/*   
 * Project: OSMP
 * FileName: OtherwiseBlock.java
 * version: V1.0
 */
package com.osmp.jdbc.parse.sqlblock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * otherwise块
 * @author heyu
 *
 */
public class OtherwiseBlock implements SQLBlock{
    private List<SQLBlock> sqlBlocks = new ArrayList<SQLBlock>();
    
    public OtherwiseBlock() {
    }
    
    public void addSqlBlock(SQLBlock sqlblock){
        if(sqlblock == null) return;
        sqlBlocks.add(sqlblock);
    }
    
    public void addSqlBlocks(List<SQLBlock> sqlblocks){
        if(sqlblocks == null) return;
        sqlBlocks.addAll(sqlblocks);
    }

    public String sql(Map<String, Object> params) {
        if(sqlBlocks.isEmpty()) return "";
        StringBuffer sb = new StringBuffer();
        for(SQLBlock block : sqlBlocks){
            sb.append(block.sql(params));
        }
        return sb.toString();
    }
    
    
}
