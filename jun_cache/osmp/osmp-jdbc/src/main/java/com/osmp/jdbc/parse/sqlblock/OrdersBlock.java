/*   
 * Project: OSMP
 * FileName: OrdersBlock.java
 * version: V1.0
 */
package com.osmp.jdbc.parse.sqlblock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * orders块
 * @author heyu
 *
 */
public class OrdersBlock implements SQLBlock {

    private List<SQLBlock> sqlBlocks = new ArrayList<SQLBlock>();
    
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
        if(sqlBlocks.isEmpty()) return "";
        StringBuffer sb = new StringBuffer();
        for(SQLBlock block : sqlBlocks) {
            String sql = block.sql(params);
            if(sql == null || "".equals(sql)) continue;
            sb.append(sql);
            if(!sql.trim().endsWith(",")){
                sb.append(",");
            }
        }
        String sqlAll = sb.toString();
        if("".equals(sqlAll)) return "";
        if(!sqlAll.trim().startsWith("order ")){
            sqlAll = "order by "+sqlAll.trim();
        }
        return sqlAll.substring(0,sqlAll.length()-1) + " ";
    }
    
}
