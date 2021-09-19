	
		package com.jun.plugin.dbutils;
		
		import java.sql.Connection;
		import java.sql.SQLException;		
		import javax.sql.DataSource;
		import org.apache.commons.dbutils.QueryRunner;
		import com.mchange.v2.c3p0.ComboPooledDataSource;
		
		public class JdbcUtils{
			
			//读取c3p0-config.xml文件的默认配置
			private static ComboPooledDataSource  datasource =new ComboPooledDataSource();
			
					/*
					 * 使用连接池返回连接对象
					 */
			//	测试连接
				/*
				 * 	@Test
					public void  test() throws SQLException{
						System.out.println(getconnection());
					}
				*/
					
					public static Connection getconnection() throws SQLException{
						
						return datasource.getConnection();
					}
					/*
					 * 返回连接池对象
					 */
					public static DataSource getDataSource(){	
								return datasource;
					}
					public static QueryRunner getQueryRunner(){
							
								return new QueryRunner(datasource);
					}
		}
