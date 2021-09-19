package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import entity.Column;
import entity.Table;

/**
 * 黑马架构师  V2.5
 * 
 * @author 传智播客研究院 黑马刘皇叔
 *
 */
public class Util {
	private String dbType;//数据库类型
	private String driverName;
	private String userName;
	private String passWord;
	private String url;
	private String dbName;//数据库名称
	private String ip;
	
		
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<String> getSchemas() throws ClassNotFoundException, SQLException
	{
		
		Class.forName(driverName);
		Connection connection = java.sql.DriverManager.getConnection(url, userName, passWord);
		DatabaseMetaData metaData = connection.getMetaData();
		ResultSet rs = metaData.getCatalogs();
		List<String> list=new ArrayList<String>();		
		while(rs.next())
		{
			list.add( rs.getString(1));
		}
		rs.close();
		connection.close();
		return list;		
	}
 
	/**
	 * 获取表及字段信息
	 * @param
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public List<Table> getDbInfo() throws ClassNotFoundException, SQLException
	{
		//加载转换器
		Map<String, String> convertMap = xml.XmlUtil.readNu("typeConverter.xml");
		
		Class.forName(driverName);
		Properties props =new Properties();
        props.put("remarksReporting","true");
        props.put("user", userName);
        props.put("password", passWord);
        if(dbName!=null  ){
        	url=url.replace("[db]", dbName);
        }
        
        if(ip!=null && !ip.equals("")){
        	url=url.replace("[ip]", ip);
        }else
        {
        	url=url.replace("[ip]", "127.0.0.1");
        }
     
		Connection connection = java.sql.DriverManager.getConnection(url, props);
		
	   
		
		DatabaseMetaData metaData = connection.getMetaData();
		
		String schema=null;
		String catalog=null;
		//如果是oracle数据库
		if(dbType!=null && dbType.toUpperCase().indexOf("ORACLE")>=0)
		{
			schema=userName.toUpperCase();
			catalog = connection.getCatalog();
		}
		ResultSet tablers = metaData.getTables(catalog, schema, null, new String[]{"TABLE"});  
		
		List<Table> list=new ArrayList<Table>();		
		while(tablers.next())
		{
			entity.Table table=new Table();
			String tableName=tablers.getString("TABLE_NAME");
			
			//如果为垃圾表
			if(tableName.indexOf("=")>=0 || tableName.indexOf("$")>=0)
			{
				continue;
			}
			
			//判断 表名为全大写 ，则转换为小写
			if(tableName.toUpperCase().equals(tableName))
			{
				table.setName(tableName.toLowerCase());
			}else
			{
				table.setName(tableName);
			}
						
			table.setComment(tablers.getString("REMARKS"));
			
			//获得主键
			ResultSet primaryKeys = metaData.getPrimaryKeys(catalog, schema, tableName);
			List<String> keys=new ArrayList<String>();
			while(primaryKeys.next())
			{
				String keyname=primaryKeys.getString("COLUMN_NAME");
				//判断 表名为全大写 ，则转换为小写
				if(keyname.toUpperCase().equals(keyname))
				{
					keyname=keyname.toLowerCase();//转换为小写
				}
				keys.add(keyname);
			}
			System.out.println("信息："+catalog+"   "+schema+"   "+tableName);
			//获得所有列
			ResultSet columnrs = metaData.getColumns(catalog, schema, tableName, null);
			
			List<Column> columnList=new ArrayList<Column>();		
			while(columnrs.next())
			{
				Column column=new Column();
				String columnName=  columnrs.getString("COLUMN_NAME");
				//判断 表名为全大写 ，则转换为小写
				if(columnName.toUpperCase().equals(columnName))
				{
					columnName=columnName.toLowerCase();//转换为小写
				}				
				column.setColumnName(columnName);	
				
				String columnDbType = columnrs.getString("TYPE_NAME");
				column.setColumnDbType(columnDbType);//数据库原始类型
				
				String typeName = convertMap.get(columnDbType);//获取转换后的类型
				if(typeName==null)
				{
					typeName=columnrs.getString("TYPE_NAME");
				}
				column.setColumnType(typeName);
				
				String remarks = columnrs.getString("REMARKS");//备注 
				if(remarks==null)
				{
					remarks=columnName;
				}
				column.setColumnComment(remarks);
				
				if(keys.contains(columnName))//如果该列是主键
				{
					column.setColumnKey("PRI");
					table.setKey(column.getColumnName());
				}else
				{
					column.setColumnKey("");
				}
				int decimal_digits =columnrs.getInt("DECIMAL_DIGITS");//小数位数
				if(decimal_digits>0)
				{
					column.setColumnType("Double");//如果是小数则设置为Double
				}
				
				column.setDecimal_digits(decimal_digits);//
				column.setColums_size(columnrs.getInt("COLUMN_SIZE"));//字段长度
				
				columnList.add(column);
			}
			columnrs.close();
			table.setColumns(columnList);
			
			list.add(table );
			
		}
		tablers.close();
		connection.close();
		return list;		
	}
	
	
}
