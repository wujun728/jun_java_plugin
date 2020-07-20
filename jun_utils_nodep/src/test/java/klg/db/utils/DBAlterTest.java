package klg.db.utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Ignore;
import org.junit.Test;

import klg.common.utils.MyPrinter;
import klg.db.info.model.Table;

public class DBAlterTest {
	DataSource dataSource =null;
	{
		Properties pro = new Properties();
		try {
			pro.load(DBAlterTest.class.getClassLoader().getResourceAsStream("dbcp.properties"));
			 dataSource= BasicDataSourceFactory.createDataSource(pro);// 得到一个连接池对象
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Test
	@Ignore
	public void test() throws SQLException{
		System.out.println(dataSource.getConnection());
	}
	
	@Test
	@Ignore
	public void testResult() throws SQLException{
		BeanListHandler<Table> beanListHandler = new BeanListHandler<>(Table.class);
		List<Table> tables= beanListHandler.handle(dataSource.getConnection().getMetaData().getTables(null, null, null, new String[]{"TABLE"}));
		MyPrinter.printJson(tables);
	}
	
}
