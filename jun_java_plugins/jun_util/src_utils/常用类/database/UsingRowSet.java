package book.database;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.JoinRowSet;
import javax.sql.rowset.Predicate;
import javax.sql.rowset.WebRowSet;

import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.FilteredRowSetImpl;
import com.sun.rowset.JdbcRowSetImpl;
import com.sun.rowset.JoinRowSetImpl;
import com.sun.rowset.WebRowSetImpl;

/**
 * 本例演示如何使用RowSet接口。RowSet继承ResultSet，比ResultSet更好用，
 * 是是JDK 1.5的新特征之一。该接口有几个子接口：
 * （1）CachedRowSet：可以不用与数据源建立长期的连接，只有当从数据库读取数据，
 * 或是往数据库写入数据的时候才会与数据库建立连接，它提供了一种轻量级的访问数据库的方式，其数据均存在内存中。
 * 避免了ResultSet在关闭Statement或Connection后无法读取数据的问题。
 * （2）JdbcRowSet：对ResultSet的对象进行包装，使得可以将ResultSet对象做为一个JavaBeans组件，
 * （3）FilteredRowSet：继承自CachedRowSet，可以根据设置条件得到数据的子集。
 * （4）JoinRowSet：继承自CachedRowSet，可以将多个RowSet对象进行SQL Join语句的合并。
 * （5）WebRowSet：继承自CachedRowSet，可以将WebRowSet对象输出成XML格式。
 */
public class UsingRowSet {
	/**
	 * 使用CachedRowSet。
	 * 一旦获得数据，CachedRowSet就可以断开与数据库的连接，
	 * 直到往数据库写入数据的时候才需建立连接。
	 */
	public static void usingCachedRowSet() throws SQLException{
		CachedRowSet crs = new CachedRowSetImpl();
		// 设置CachedRowSet的属性，用它可以直接连数据库
        crs.setUrl("jdbc:mysql://127.0.0.1/studentdb");
        crs.setUsername("test");
        crs.setPassword("test");
        crs.setCommand("select * from student_basic where score > ?");
        crs.setDouble(1, 60);

		try {
			// 需要先加载驱动，否则在使用执行时会找不到驱动类
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		}
		// CachedRowSet的execute方法执行SQL语句，
		// 首先会创建与数据库的连接，然后将结果集取出来，再关闭连接
		crs.execute();
		// 此时的CachedRowSet与数据库是断开连接的
        System.out.println("使用CachedRowSet操作数据前：");
        OperateDB.showResultSet(crs);
        
        // 在断开连接的情况下可以操作数据
        crs.beforeFirst();
        while (crs.next()) {
            if (crs.getString("name").equals("mary")) {
                crs.updateDouble("score",75);
                // 要想将更新的数据提交到数据库，必须进行下面两步
                // 首先确认要修改
                crs.updateRow();
                // 再调用acceptChanges方法获得与数据库的连接，将修改提交到数据库
                crs.acceptChanges();
                break;
            }
        }
        System.out.println("使用CachedRowSet操作数据后：");
        OperateDB.showResultSet(crs);
        
        crs.close();
	}
	
	/**
	 * 使用JdbcRowSet。
	 * JdbcRowSet功能与ResultSet类似,JdbcRowSet在操作时保持与数据库的连接,
	 * JdbcRowSet返回的结果默认是可以上下滚动和可更新的。
	 */
	public static void usingJdbcRowSet() throws SQLException{
		JdbcRowSet jdbcrs = new JdbcRowSetImpl();
		// 设置JdbcRowSet的属性，用它可以直接连数据库
        jdbcrs.setUrl("jdbc:mysql://127.0.0.1/studentdb");
        jdbcrs.setUsername("test");
        jdbcrs.setPassword("test");
        jdbcrs.setCommand("select * from student_basic where score > ?");
        jdbcrs.setDouble(1, 70);

		try {
			// 需要先加载驱动，否则在使用执行时会找不到驱动类
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		}
		// JdbcRowSet的execute方法执行SQL语句，首先获得数据库连接，再获取结果集，
		// 与CachedRowSet的execute方法不同，执行完后它不关闭连接，它会一直保持该连接，直到调用close方法。
        jdbcrs.execute();
        System.out.println("使用JdbcRowSet操作数据前：");
        OperateDB.showResultSet(jdbcrs);
        
        // 然后操作数据
        jdbcrs.beforeFirst();
        while (jdbcrs.next()) {
            if (jdbcrs.getString("name").equals("mary")) {
                jdbcrs.updateDouble("score",85);
                // 提交到数据库
                jdbcrs.updateRow(); 
                // 因为它本身是连接到数据库的，所以和CachedRowSet不同，它不需要再次获得连接。
                break;
            }
        }
        System.out.println("使用JdbcRowSet操作数据后：");
        OperateDB.showResultSet(jdbcrs);
        
        // 关闭结果集，此时关闭数据库连接
        jdbcrs.close();
	}
	
	/**
	 * 使用FilteredRowSet。
	 * FilteredRowSet接口中规定了可以设定过滤器，其过滤接口为Predicate接口，
	 * 必须实现Predicate接口中的evaluate方法
	 */
	public static void usingFilteredRowSet() throws SQLException{
		FilteredRowSet frs = new FilteredRowSetImpl();
		// 设置FilteredRowSet的属性，用它可以直接连数据库
        frs.setUrl("jdbc:mysql://127.0.0.1/studentdb");
        frs.setUsername("test");
        frs.setPassword("test");
        frs.setCommand("select * from student_basic where score > ?");
        frs.setDouble(1, 80);

		try {
			// 需要先加载驱动，否则在使用执行时会找不到驱动类
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		}
		// FilteredRowSet继承CachedRowSet，
		// 所以execute方法功能与CachedRowSet的execute方法一样

		frs.execute();
		// 此时的CachedRowSet与数据库是断开连接的
        System.out.println("使用FilteredRowSet过滤数据之前：");
        OperateDB.showResultSet(frs);
        // 设置过滤器，过滤器必须实现Predicate接口定义的三个execute方法。
        frs.setFilter(new Predicate(){
        	public boolean evaluate(RowSet rs){
        		CachedRowSet crs=(CachedRowSet)rs;
        		// 如果名字为mary则返回true
        		try {
        			if (crs.getString("name").equals("mary")){
        				return true;
        			} 
        		} catch (SQLException e){
        		}
        		return false;
        	}
        	public boolean evaluate(Object arg0, int arg1) throws SQLException{
        		return false;
        	}
        	public boolean evaluate(Object arg0, String arg1) throws SQLException{
        		return false;
        	}
        });

        System.out.println("使用FilteredRowSet过滤数据之后：");
        OperateDB.showResultSet(frs);
        
        frs.close();
	}
	
	/**
	 * 使用JoinRowSet。
	 * JoinRowSet可以将多个RowSet对象进行join合并，
	 * Join的列可以通过每个RowSet通过调用setMatchColumn方法来设置。
	 * setMatchColumn方式是Joinable接口定义的方法，五种类型的RowSet规定都需要实现该接口。
	 * JoinRowSet继承CachedRowSet，也不需要保持与数据库的连接。
	*/
	public static void usingJoinRowSet() throws SQLException{
		JoinRowSet joinrs = new JoinRowSetImpl();
		
		CachedRowSet crs = new CachedRowSetImpl();
		// 设置CachedRowSet的属性，用它可以直接连数据库
		crs.setUrl("jdbc:mysql://127.0.0.1/studentdb");
		crs.setUsername("test");
		crs.setPassword("test");
		crs.setCommand("select * from student_basic");
		try {
			// 需要先加载驱动，否则在使用执行时会找不到驱动类
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		}
		// 获取结果集
		crs.execute();
		// 设置结果集在Join时匹配的列名。
		crs.setMatchColumn("name");
		// 将结果集数据放入JoinRowSet
		joinrs.addRowSet(crs);
		crs.close();
		
		// 查另外一个表
		crs.setCommand("select name, address from student_address");
		crs.execute();
		crs.setMatchColumn("name");
		joinrs.addRowSet(crs);
		crs.close();
		
		System.out.println("使用JoinRowSet对多个结果集进行Join操作：");
		// 此时两个结果集已经Join在一起了，
		// 表student_basic的name列和student_address的name列进行匹配
		while (joinrs.next()){
			// name属性公有，score属性为表student_basic所有
			System.out.print(joinrs.getString("name") + "\t");
			System.out.print(joinrs.getDouble("score") + "\t");
			// address属性为student_address所有
			System.out.println(new String(joinrs.getBytes("address")));
		}

        joinrs.close();
	}
	
	/**
	 * 使用WebRowSet。
	 * WebRowSet继承自CachedRowSet，支持XML格式的查询，更新等操作，
	 * 下面将WebRowSet对象输出成XML格式到文件。
	*/
	public static void usingWebRowSet() throws SQLException{
		WebRowSet wrs = new WebRowSetImpl();
		
		// 设置CachedRowSet的属性，用它可以直接连数据库
		wrs.setUrl("jdbc:mysql://127.0.0.1/studentdb");
		wrs.setUsername("test");
		wrs.setPassword("test");
		wrs.setCommand("select * from student_basic");
		try {
			// 需要先加载驱动，否则在使用执行时会找不到驱动类
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		}
		// 获取结果集
		wrs.execute();
		
		// 输出到XML文件
		try {
			FileOutputStream out = new FileOutputStream("student_basic_data.xml");
			wrs.writeXml(out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		wrs.close();
	}

	public static void main(String[] args) throws SQLException {
		UsingRowSet.usingCachedRowSet();
		UsingRowSet.usingJdbcRowSet();
		UsingRowSet.usingFilteredRowSet();
		UsingRowSet.usingJoinRowSet();
		UsingRowSet.usingWebRowSet();
	}
}
