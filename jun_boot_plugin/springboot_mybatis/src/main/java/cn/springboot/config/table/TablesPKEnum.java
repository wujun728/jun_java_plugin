package cn.springboot.config.table;

import java.util.Map;
import java.util.TreeMap;

/** 
 * @Description 数据表配置类
 * @author Wujun
 * @date Apr 12, 2017 2:12:02 PM  
 */
public enum TablesPKEnum {

    T_NEWS("T_NEWS", "1001"), 
    T_SYS_PERMISSION("T_SYS_PERMISSION", "1002"), 
    T_SYS_ROLE("T_SYS_ROLE", "1003"), 
    T_SYS_ROLE_PERMISSION("T_SYS_ROLE_PERMISSION", "1004"), 
    T_SYS_USER("T_SYS_USER", "1005"), 
    T_SYS_USER_ROLE("T_SYS_USER_ROLE", "1006");

    TablesPKEnum(String tableName, String tableCode) {
        this.tableName = tableName;
        this.tableCode = tableCode;
    }
    
    public static Map<String,Key> getTables() {
        TablesPKEnum[] tables = TablesPKEnum.values();
        Map<String,Key> map = new TreeMap<>();
        Key key = null;
        for (TablesPKEnum tablePk : tables) {
            key = new Key();
            key.setTableName(tablePk.tableName);
            key.setTableCode(tablePk.tableCode);
            map.put(tablePk.tableName , key);
        }
        return map;
    }

    private String tableName;
    private String tableCode;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

}
