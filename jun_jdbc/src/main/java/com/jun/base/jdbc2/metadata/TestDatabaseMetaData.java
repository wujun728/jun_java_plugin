package com.jun.base.jdbc2.metadata;

  
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.jun.base.jdbc2.test.JdbcByPropertiesUtil;  
  
  
public class TestDatabaseMetaData {  
    private JdbcByPropertiesUtil jbpu = JdbcByPropertiesUtil.getInstance();  
      
    public JdbcByPropertiesUtil getJbpu() {  
        return jbpu;  
    }  
      
    public void setJbpu(JdbcByPropertiesUtil jbpu){  
        this.jbpu = jbpu;  
    }  
      
    public Properties getProperties(){  
        Properties pros = JdbcByPropertiesUtil.readPropertiesFile();  
        return pros;  
    }  
  
    /** 
     * 读取配置文件jdbc.properties中的数据库名称 
     * @return 
     * @throws Exception 
     */  
    public String getDataSourceName()throws Exception{  
        Properties pros = this.getProperties();  
        String dbName = pros.get("dbName").toString();  
        return dbName;  
    }  
      
    /** 
     * 获取数据库中的表名称与视图名称 
     * @return 
     */  
    public List getTablesAndViews()throws Exception{  
        Connection conn = jbpu.getConnection();  
        ResultSet rs = null;  
        List list = new ArrayList();  
        try {  
            Properties pros = this.getProperties();  
            String schema = pros.get("user").toString();  
            DatabaseMetaData metaData = conn.getMetaData();  
            rs = metaData.getTables(null, schema, null, new String[]{"TABLE","VIEW"});  
            while(rs.next()){  
                String tableName = rs.getString("TABLE_NAME");  
                list.add(tableName);  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } finally{  
            jbpu.close(rs, null, conn);  
        }  
        return list;  
    }  
      
    /** 
     * 利用表名和数据库用户名查询出该表对应的字段类型 
     * @param tableName 表名 
     * @return 
     * @throws Exception 
     */  
    public String generateBean()throws Exception{  
        Connection conn = jbpu.getConnection();  
        ResultSet rs = null;  
        String strJavaBean = "";  
        String tableName = this.getDataSourceName();  
        try{  
            Properties pros = this.getProperties();  
            String schema = pros.get("user").toString();  
            DatabaseMetaData metaData = conn.getMetaData();  
            rs = metaData.getColumns(null, schema, tableName, null);  
            Map map = new HashMap();  
            while(rs.next()){  
                String columnName = rs.getString("COLUMN_NAME");//列名  
                String dataType  = rs.getString("DATA_TYPE");//字段数据类型(对应java.sql.Types中的常量)  
                String typeName = rs.getString("TYPE_NAME");//字段类型名称(例如：VACHAR2)  
                map.put(columnName, dataType+":"+typeName);  
            }  
              
            // 其它生成javaBean的相关操作  
              
        }catch(Exception e){  
            e.printStackTrace();  
        }finally{  
            jbpu.close(rs, null, conn);  
        }  
        return strJavaBean;  
    }  
      
}  