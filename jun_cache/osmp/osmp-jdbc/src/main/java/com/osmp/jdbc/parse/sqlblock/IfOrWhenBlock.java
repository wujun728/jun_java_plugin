/*   
 * Project: OSMP
 * FileName: IfOrWhenBlock.java
 * version: V1.0
 */
package com.osmp.jdbc.parse.sqlblock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.osmp.jdbc.parse.Condition;

/**
 * 包含test筛选条件的sql块(if或者when)
 * @author heyu
 *
 */
public class IfOrWhenBlock implements SQLBlock{
    private List<SQLBlock> sqlBlocks = new ArrayList<SQLBlock>();
    private String property;
    private Condition cond;
    private String value;
    
    public IfOrWhenBlock(String property, Condition cond, String value) {
        this.property = property;
        this.cond = cond;
        this.value = value;
    }
    
    public void addSqlBlock(SQLBlock sqlblock){
        if(sqlblock == null) return;
        sqlBlocks.add(sqlblock);
    }
    
    public void addSqlBlocks(List<SQLBlock> sqlblocks){
        if(sqlblocks == null) return;
        sqlBlocks.addAll(sqlblocks);
    }

    public String sql(Map<String, Object> paramsMap) {
        if(paramsMap == null) paramsMap = new HashMap<String, Object>();
        if(!compareValues(paramsMap.get(property), value , cond)) return "";
        StringBuffer sb = new StringBuffer();
        for(SQLBlock block : sqlBlocks){
            sb.append(block.sql(paramsMap));
        }
        return sb.toString();
    }
    
    private static boolean compareValues(Object obj1,Object obj2,Condition cond){
        if(cond == null) return false;
        if(cond == Condition.ISNULL){
            return obj1 == null;
        }else if(cond == Condition.ISEMPTY){
            return obj1 == null || "".equals(obj1.toString().trim());
        }else if(cond == Condition.NOTNULL){
            return obj1 != null;
        }else if(cond == Condition.NOTEMPTY){
            return obj1 != null && !"".equals(obj1.toString().trim());
        }
        
        if(obj1 == null) return false;
        
        if(cond == Condition.EQUAL){
            return obj1.toString().equalsIgnoreCase(obj2.toString());
        }else if(cond == Condition.NOTEQUAL){
            return !obj1.toString().equalsIgnoreCase(obj2.toString());
        }
        Double db1 = null;
        Double db2 = null;
        try{
            db1 = Double.valueOf(obj1.toString());
            db2 = Double.valueOf(obj2.toString());
        }
        catch (Exception e) {
        }
        if(db1 == null || db2 == null) return false;
        if(cond == Condition.GREAT){
            return db1 > db2;
        }else if(cond == Condition.LESS){
            return db1 < db2;
        }else if(cond == Condition.NOTGREAT){
            return db1 <= db2;
        }else if(cond == Condition.NOTLESS){
            return db1 >= db2;
        }
        return false;
    }
    
}
