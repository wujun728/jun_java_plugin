package klg.db.utils;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import klg.db.info.model.Column;
import klg.db.info.model.Table;

public class MysqlDBmeta {

	private DataSource dataSource;// 定义一个连接池对象

	public MysqlDBmeta(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	/**
	 * 
	 * @param dbName
	 * @return
	 * @throws SQLException
	 */
	public List<Table> getTabls(String dbName) throws SQLException {
		String sql = "select * from information_schema.`TABLES` WHERE TABLE_SCHEMA=\"" + dbName + "\" and TABLE_TYPE=\"BASE TABLE\"";
		List<Table> tables = (List<Table>) new QueryRunner().query(dataSource.getConnection(), sql, new BeanListHandler(Table.class));
		return tables;
	}

	/**
	 * 
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public List<Column> getColumns(String dbName, String tableName) throws SQLException {
		String sql = "select * from information_schema.`COLUMNS` WHERE TABLE_SCHEMA=\"" + dbName + "\" and TABLE_NAME=\"" + tableName + "\"";
		List<Column> columns = (List<Column>) new QueryRunner().query(dataSource.getConnection(), sql, new BeanListHandler(Column.class));
		return columns;
	}

}
