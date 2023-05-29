package com.jun.plugin.freemark.code.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.jun.plugin.freemark.code.conf.PropertiesConfig;
import com.jun.plugin.freemark.code.conf.PropertiesService;
import com.jun.plugin.freemark.code.entity.Columns;
import com.jun.plugin.freemark.code.entity.Table;
/**
 * 查询数据库表信息
 * @author shichenyang89@gmail.com
 *
 */
public class SelectTableSql {
	private String name;
	private int age;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	private static Connection getConnection = null;

	/**
	 * 获取数据库连接
	 * 
	 * @param driver 驱动
	 * @param pwd 密码
	 * @param user 用户
	 * @param url 连接字符串
	 * @return 连接
	 */
	public static Connection getConnections() {
		try {
			PropertiesConfig config = PropertiesService.getApplicationConfig();
			Properties props = new Properties();
			//针对于oracle数据库做特殊处理
			if(config.getProperty("database.name").equals("oracle")){
				props.put("remarksReporting", "true");
			}
			props.put("user", config.getProperty("jdbc.username"));
			props.put("password", config.getProperty("jdbc.password"));
			Class.forName(config.getProperty("jdbc.driver"));
			getConnection = DriverManager.getConnection(config.getProperty("jdbc.url"), props);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getConnection;
	}

	/**
	 * 获取数据库信息
	 * @return
	 * @throws Exception
	 */
	public List<Table> getSchema() throws Exception {
//		String schema;
		getConnection=getConnections();
		DatabaseMetaData metaData= getConnection.getMetaData();//获得元数据
		//下面这一行,获得数据库所有表信息
		ResultSet result= metaData.getTables(getConnection.getCatalog(), "root", null, new String[]{"TABLE"});
		List<Table> list=new ArrayList<Table>();
		while (result.next()) {// 判断有没有下一行
			//获得表名
			String tableNmae=result.getString("TABLE_NAME");
			//获得表的注释
			String tableRemarks=result.getString("REMARKS");
			
			list.add(crateTbale(tableNmae,tableRemarks));
			//这个是我自己生成表的前缀
			/*if(tableNmae.length()>=8){
				String doc=tableNmae.substring(0,8);
				if("Y_DOCTOR".equals(doc)){
					list.add(crateTbale(tableNmae,tableRemarks));
				}
			}*/
		}
//		schema = getConnection.getMetaData().getUserName();
//		if ((schema == null) || (schema.length() == 0)) {
//			throw new Exception("ORACLE数据库模式不允许为空");
//		}
		return list;

	}

	/**
	 * 根据表名进行查找列
	 * @param tableName
	 * @param tableRemarks
	 * @return
	 * @throws Exception
	 */
	public Table crateTbale(String tableName,String tableRemarks) throws Exception {
		ResultSet result = null;// 查询数据库
		//数据库元数据
		DatabaseMetaData metaData= getConnection.getMetaData();
		//根据表名获得列信息
		result= metaData.getColumns(null,"%", tableName, "%");
		//自己编写的实体类
		Table table=new Table();
		table.setTableName(tableName);
		table.setTableRemarks(tableRemarks);
		List<Columns> list=new ArrayList<Columns>();
		//对列信息进行循环
		while(result.next()) { 
			//创建一个列的实体类
			Columns columns=new Columns();
			String columnName = result.getString("COLUMN_NAME"); 
			String columnType = result.getString("TYPE_NAME");
			String REMARKS=result.getString("REMARKS");
			int datasize = result.getInt("COLUMN_SIZE"); 
			int digits = result.getInt("DECIMAL_DIGITS"); 
			int nullable = result.getInt("NULLABLE"); 
			String nullable2=nullable==0?"非空":"可以为空";
			columns.setColumnName(columnName);
			columns.setColumnType(columnType);
			columns.setDatasize(datasize);
			columns.setDigits(digits);
			columns.setNullable(nullable2);
			columns.setREMARKS(REMARKS);
			list.add(columns);
		}
		table.setList(list);
		return table;
	}
}
