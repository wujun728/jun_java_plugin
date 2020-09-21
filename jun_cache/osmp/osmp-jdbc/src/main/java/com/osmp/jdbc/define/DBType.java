package com.osmp.jdbc.define;

public enum DBType {
    ORACLE,SQLSERVER,MYSQL;
    
    public final static DBType getDBType(String url){
        if(url == null) return null;
        if(url.startsWith("jdbc:jtds:sqlserver")){
            return DBType.SQLSERVER;
        }else if(url.startsWith("jdbc:mysql")){
            return DBType.MYSQL;
        }
        return DBType.ORACLE;
    }
}
