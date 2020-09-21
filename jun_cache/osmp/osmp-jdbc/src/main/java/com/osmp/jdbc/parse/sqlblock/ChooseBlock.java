/*   
 * Project: OSMP
 * FileName: ChooseBlock.java
 * version: V1.0
 */
package com.osmp.jdbc.parse.sqlblock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * choose块
 * @author heyu
 *
 */
public class ChooseBlock implements SQLBlock{
    private List<SQLBlock> sqlBlocks = new ArrayList<SQLBlock>();
    
    public ChooseBlock() {
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
        for(int i = 0,j=sqlBlocks.size();i<j;i++){
            String sql = sqlBlocks.get(i).sql(params);
            if(sql != null && !"".equals(sql)){
                return sql;
            }
        }
        return "";
    }
}
