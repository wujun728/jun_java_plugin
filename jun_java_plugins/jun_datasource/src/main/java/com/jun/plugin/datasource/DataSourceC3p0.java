package com.jun.plugin.datasource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
 
public class DataSourceC3p0 {
       /**
        * 释放连接
        * @param connection
        */
       public static void releaseConnection(Connection connection){
              try {
                     if(connection != null ) {
                            connection.close();
                     }
              }catch (Exception e) {
                     e.printStackTrace();
              }
       }
       private static DataSource dataSource = null;
       static{
              //dataSource资源只能初始化一次
              dataSource= new ComboPooledDataSource("c3p0");
       }
       /**
        * 获取连接
        * @return
        * @throws SQLException
        */
       public static Connection getConnection() throws SQLException{
              return dataSource.getConnection();
       }
	public static DataSource getDataSource() {
		return dataSource;
	}
}