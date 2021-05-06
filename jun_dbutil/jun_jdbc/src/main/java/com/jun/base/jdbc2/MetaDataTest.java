package com.jun.base.jdbc2;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class MetaDataTest {
	static Connection con = null;
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/erp2?useUnicode=true&characterEncoding=utf-8", "root", "mysqladmin");
			PreparedStatement pst = con.prepareStatement("drop table if exists user;");
			pst.execute();
			pst = con.prepareStatement(
					"create table user(id int auto_increment primary key comment '主键啊',name varchar(20) not"
							+ " null comment '名称啊',age int default 18 comment '年龄啊',salary float(8,2) comment '薪水啊',rq date,sj time,rj timestamp);");
			pst.execute();
			String sql = "insert into user (name,age,salary,rq,sj,rj) values (?,?,?,?,?,?)";
			pst = con.prepareStatement(sql);
			for (int i = 1; i <= 10; i++) {
				pst.setString(1, "zs" + i);
				pst.setInt(2, 17 + i);
				pst.setFloat(3, 2600 + i * 100.0f);
				long time = System.currentTimeMillis();
				pst.setDate(4, new java.sql.Date(time));
				pst.setTime(5, new java.sql.Time(time));
				pst.setTimestamp(6, new java.sql.Timestamp(time));
				pst.addBatch();
			}
			pst.executeBatch();
			pst.close();
		} catch (Exception ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}
	/**
	 * DatabaseMetaData一些用法
	 * 
	 * @throws Exception
	 */
	public static void getDBInfo() throws Exception {
		DatabaseMetaData dbmd = con.getMetaData();
		System.out.println(dbmd.getDatabaseProductName());// 获取数据库产品名称
		System.out.println(dbmd.getDatabaseProductVersion());// 获取数据库产品版本号
		System.out.println(dbmd.getCatalogSeparator());// 获取数据库用作类别和表名之间的分隔符 如test.user
		System.out.println(dbmd.getDriverVersion());// 获取驱动版本
		System.out.println("*******************可用的数据库列表*********************");
		ResultSet rs = dbmd.getCatalogs();// 取可在此数据库中使用的类别名,在mysql中说白了就是可用的数据库名称，只有一列
		while (rs.next()) {
			System.out.println(rs.getString(1));
		}
		System.out.println("********************所有表********************************");
		/**
		 * catalog 类别名称 schemaPattern 用户方案模式， tableNamePattern 表 types 类型 获取所有表
		 * dbmd.getTables(catalog, schemaPattern, tableNamePattern, types)
		 */
		rs = dbmd.getTables(null, null, null, new String[] { "TABLE" });// 参数列表 1:类别名称,2:
																		// 模式名称的模式,3:表名称模式,4:要包括的表类型所组成的列表
		while (rs.next()) {
			/**
			 * 所有的列信息。如下 TABLE_CAT String => 表类别（可为 null） TABLE_SCHEM String => 表模式（可为 null）
			 * TABLE_NAME String => 表名称 COLUMN_NAME String => 列名称 DATA_TYPE int => 来自
			 * java.sql.Types 的 SQL 类型 TYPE_NAME String => 数据源依赖的类型名称，对于 UDT，该类型名称是完全限定的
			 * COLUMN_SIZE int => 列的大小。 BUFFER_LENGTH 未被使用。 DECIMAL_DIGITS int => 小数部分的位数。对于
			 * DECIMAL_DIGITS 不适用的数据类型，则返回 Null。 NUM_PREC_RADIX int => 基数（通常为 10 或 2）
			 * NULLABLE int => 是否允许使用 NULL。 columnNoNulls - 可能不允许使用 NULL 值 columnNullable -
			 * 明确允许使用 NULL 值 columnNullableUnknown - 不知道是否可使用 null REMARKS String =>
			 * 描述列的注释（可为 null） COLUMN_DEF String => 该列的默认值，当值在单引号内时应被解释为一个字符串（可为 null）
			 * SQL_DATA_TYPE int => 未使用 SQL_DATETIME_SUB int => 未使用 CHAR_OCTET_LENGTH int =>
			 * 对于 char 类型，该长度是列中的最大字节数 ORDINAL_POSITION int => 表中的列的索引（从 1 开始） IS_NULLABLE
			 * String => ISO 规则用于确定列是否包括 null。 YES --- 如果参数可以包括 NULL NO --- 如果参数不可以包括 NULL
			 * 空字符串 --- 如果不知道参数是否可以包括 null SCOPE_CATLOG String => 表的类别，它是引用属性的作用域（如果
			 * DATA_TYPE 不是 REF，则为 null） SCOPE_SCHEMA String => 表的模式，它是引用属性的作用域（如果 DATA_TYPE
			 * 不是 REF，则为 null） SCOPE_TABLE String => 表名称，它是引用属性的作用域（如果 DATA_TYPE 不是 REF，则为
			 * null） SOURCE_DATA_TYPE short => 不同类型或用户生成 Ref 类型、来自 java.sql.Types 的 SQL
			 * 类型的源类型（如果 DATA_TYPE 不是 DISTINCT 或用户生成的 REF，则为 null） IS_AUTOINCREMENT String
			 * => 指示此列是否自动增加 YES --- 如果该列自动增加 NO --- 如果该列不自动增加 空字符串 --- 如果不能确定该列是否是自动增加参数
			 * 
			 */
			System.out.println(rs.getString(3) + "->" + rs.getString(4));// 打印表类别,表模式,表名称，列名称，
		}
		System.out.println("##############################################################");
		/**
		 * catalog 类别名称 schema 用户方案名称 table 表名 获取指定表的主键信息 dbmd.getPrimaryKeys(catalog,
		 * schema, table)
		 * 
		 */
		rs = dbmd.getPrimaryKeys("test", null, "user");
		while (rs.next()) {
			/**
			 * 所有列信息如下: TABLE_CAT String => 表类别（可为 null） TABLE_SCHEM String => 表模式（可为 null）
			 * TABLE_NAME String => 表名称 COLUMN_NAME String => 列名称 KEY_SEQ short => 主键中的序列号（值
			 * 1 表示主键中的第一列，值 2 表示主键中的第二列）。 PK_NAME String => 主键的名称（可为 null）
			 * 
			 */
			System.out.println(rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3) + "," + rs.getString(4)
					+ "," + rs.getShort(5) + "," + rs.getString(6));
		}
		System.out.println("##############################################################");
		/**
		 * catalog 类别名称 schemaPattern 用户方案，模式 tableNamePattern 表 columnNamePattern 列
		 * 获取表的列信息 dbmd.getColumns(catalog, schemaPattern, tableNamePattern,
		 * columnNamePattern)
		 */
		rs = dbmd.getColumns("test", null, "user", null);
		while (rs.next()) {
			/**
			 * 所有列如下： TABLE_CAT String => 表类别（可为 null） TABLE_SCHEM String => 表模式（可为 null）
			 * TABLE_NAME String => 表名称 COLUMN_NAME String => 列名称 DATA_TYPE int => 来自
			 * java.sql.Types 的 SQL 类型 TYPE_NAME String => 数据源依赖的类型名称，对于 UDT，该类型名称是完全限定的
			 * COLUMN_SIZE int => 列的大小。 BUFFER_LENGTH 未被使用。 DECIMAL_DIGITS int => 小数部分的位数。对于
			 * DECIMAL_DIGITS 不适用的数据类型，则返回 Null。 NUM_PREC_RADIX int => 基数（通常为 10 或 2）
			 * NULLABLE int => 是否允许使用 NULL。 columnNoNulls - 可能不允许使用 NULL 值 columnNullable -
			 * 明确允许使用 NULL 值 columnNullableUnknown - 不知道是否可使用 null REMARKS String =>
			 * 描述列的注释（可为 null） COLUMN_DEF String => 该列的默认值，当值在单引号内时应被解释为一个字符串（可为 null）
			 * SQL_DATA_TYPE int => 未使用 SQL_DATETIME_SUB int => 未使用 CHAR_OCTET_LENGTH int =>
			 * 对于 char 类型，该长度是列中的最大字节数 ORDINAL_POSITION int => 表中的列的索引（从 1 开始） IS_NULLABLE
			 * String => ISO 规则用于确定列是否包括 null。 YES --- 如果参数可以包括 NULL NO --- 如果参数不可以包括 NULL
			 * 空字符串 --- 如果不知道参数是否可以包括 null SCOPE_CATLOG String => 表的类别，它是引用属性的作用域（如果
			 * DATA_TYPE 不是 REF，则为 null） SCOPE_SCHEMA String => 表的模式，它是引用属性的作用域（如果 DATA_TYPE
			 * 不是 REF，则为 null） SCOPE_TABLE String => 表名称，它是引用属性的作用域（如果 DATA_TYPE 不是 REF，则为
			 * null） SOURCE_DATA_TYPE short => 不同类型或用户生成 Ref 类型、来自 java.sql.Types 的 SQL
			 * 类型的源类型（如果 DATA_TYPE 不是 DISTINCT 或用户生成的 REF，则为 null） IS_AUTOINCREMENT String
			 * => 指示此列是否自动增加 YES --- 如果该列自动增加 NO --- 如果该列不自动增加 空字符串 --- 如果不能确定该列是否是自动增加参数
			 * 
			 */
			System.out.println(
					rs.getString("COLUMN_NAME") + " 类型=" + rs.getInt("DATA_TYPE") + " 列大小=" + rs.getInt("COLUMN_SIZE")
							+ " 注释=" + rs.getString("REMARKS") + " 是否允许为NULL=" + rs.getInt("NULLABLE"));
			// 还有很多很多方法，在这里就不一一列举了
		}
	}
	/**
	 * ResultSetMetaData一些用法
	 * 
	 * @throws Exception
	 */
	public static void getRsInfo() throws Exception {
		PreparedStatement pst = con.prepareStatement("select * from user");
		ResultSet rs = pst.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();// 结果集元
		System.out.println("下面这些方法是ResultSetMetaData中方法");
		System.out.println("获得1列所在的Catalog名字 : " + rsmd.getCatalogName(1));
		System.out.println("获得1列对应数据类型的类 " + rsmd.getColumnClassName(1));
		System.out.println("获得该ResultSet所有列的数目 " + rsmd.getColumnCount());
		System.out.println("1列在数据库中类型的最大字符个数" + rsmd.getColumnDisplaySize(1));
		System.out.println(" 1列的默认的列的标题" + rsmd.getColumnLabel(1));
		// System.out.println("1列的模式" + rsmd.GetSchemaName(1));
		System.out.println("1列的类型,返回SqlType中的编号 " + rsmd.getColumnType(1));
		System.out.println("1列在数据库中的类型，返回类型全名" + rsmd.getColumnTypeName(1));
		System.out.println("1列类型的精确度(类型的长度): " + rsmd.getPrecision(1));
		System.out.println("1列小数点后的位数 " + rsmd.getScale(1));
		System.out.println("1列对应的模式的名称（应该用于Oracle） " + rsmd.getSchemaName(1));
		System.out.println("1列对应的表名 " + rsmd.getTableName(1));
		System.out.println("1列是否自动递增" + rsmd.isAutoIncrement(1));
		System.out.println("1列在数据库中是否为货币型" + rsmd.isCurrency(1));
		System.out.println("1列是否为空" + rsmd.isNullable(1));
		System.out.println("1列是否为只读" + rsmd.isReadOnly(1));
		System.out.println("1列能否出现在where中" + rsmd.isSearchable(1));
		rs.close();
		pst.close();
	}
	public static void main(String[] args) throws Exception {
		getRsInfo();
        getDBInfo();  
	}
	
	//*********************************************************
	private MetaDataTest jbpu = MetaDataTest.getInstance();  
    
    public MetaDataTest getJbpu() {  
        return jbpu;  
    }  
      
    public void setJbpu(MetaDataTest jbpu){  
        this.jbpu = jbpu;  
    }  
      
    public Properties getProperties(){  
        Properties pros = MetaDataTest.readPropertiesFile();  
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
    //************************************************************************
    private static String filePath = "jdbc.properties";   
    private static MetaDataTest instance = null;  
      
    public MetaDataTest() {  
        super();  
    }  
  
    /** 
     * 单例方式创建对象 
     * @return 
     */  
    public static MetaDataTest getInstance() {  
        if (instance == null) {  
            synchronized (MetaDataTest.class) {  
                if (instance == null) {  
                    instance = new MetaDataTest();  
                }  
            }  
        }  
        return instance;  
    }  
         
    /** 
     * 读取properties文件中 数据库连接信息 
     * @param filePath 
     * add 2012-4-17 
     */  
    public static Properties readPropertiesFile(){  
        String realFilePath = Thread.currentThread().getContextClassLoader().getResource("").getPath()+filePath;   
        Properties pros = new Properties();    
        try {    
            InputStream is = new BufferedInputStream(new FileInputStream(realFilePath));    
            pros.load(is);   
        } catch (Exception e) {    
            e.printStackTrace();  
        }  
        return pros;  
    }   
      
    /** 
     * 注册驱动 
     * 静态代码块 用于启动web服务器时加载驱动 
     */  
    static{  
        Properties pros = readPropertiesFile();  
        String className = (String) pros.get("className");  
        try {  
            Class.forName(className).newInstance();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
    }  
      
    /** 
     * 获取数据库连接 
     * modify 2012-4-17 
     * @param con 
     * @return 
     */  
    public Connection getConnection(){  
        Properties pros = readPropertiesFile();  
        String url = (String) pros.get("url");  
        String user = (String) pros.get("user");  
        String password = (String) pros.get("password");  
        Connection conn = null;  
        try {  
            conn = DriverManager.getConnection(url,user,password);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return conn;  
    }  
         
    /** 
     *  依次关闭ResultSet、Statement、Connection 
     *  若对象不存在则创建一个空对象 
     * @param rs 
     * @param st 
     * @param pst 
     * @param conn 
     */  
    public void close(ResultSet rs,Statement st,Connection conn){  
        if(rs != null){  
            try {  
                rs.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            } finally{  
                if(st != null){  
                    try {  
                        st.close();  
                    } catch (SQLException e) {  
                        e.printStackTrace();  
                    } finally{  
                        if(conn != null){  
                            try {  
                                conn.close();  
                            } catch (SQLException e) {  
                                e.printStackTrace();  
                            }  
                        }  
                    }  
                }  
            }  
        }  
    }  
  
  
    /** 
     * 新增、修改、删除、查询记录(也可以改为有结果集ResultSet返回的查询方法) 
     * @param sql 
     * @throws   
     */  
    public void execute(String sql){  
        MetaDataTest jbpu = getInstance();  
        Connection conn = null;  
        PreparedStatement pst = null;  
        try {  
            conn = jbpu.getConnection();  
            conn.setAutoCommit(false);  
            pst = conn.prepareStatement(sql);  
            pst.execute();  
            conn.commit();  
        } catch (Exception e) {  
            try {  
                conn.rollback();  
            } catch (SQLException e1) {  
                e1.printStackTrace();  
            }  
            e.printStackTrace();  
        } finally{  
            //Statement st = null;  
            ResultSet rs = null;  
            jbpu.close(rs, pst, conn);   
        }  
    }  
   
    /** 
     * 新增、修改、删除记录 
     * @param sql 
     * @throws   
     */  
    public void executeUpdate(String sql) {  
        MetaDataTest jbpu = getInstance();  
        Connection conn = null;  
        PreparedStatement pst = null;  
        try {  
            conn = jbpu.getConnection();  
            conn.setAutoCommit(false);  
            pst = conn.prepareStatement(sql);  
            pst.executeUpdate();  
            conn.commit();  
        } catch (Exception e) {  
            try {  
                conn.rollback();  
            } catch (SQLException e1) {  
                e1.printStackTrace();  
            }  
            e.printStackTrace();  
        } finally{  
            //Statement st = null;  
            ResultSet rs = null;  
            jbpu.close(rs, pst, conn);   
        }  
    }  
    
    
    
}