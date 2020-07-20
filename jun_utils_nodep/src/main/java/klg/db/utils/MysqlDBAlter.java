package klg.db.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class MysqlDBAlter {

	private DataSource dataSource;// 定义一个连接池对象

	public MysqlDBAlter(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	/**
	 * 更新字段的字符集和校验规则
	 * 
	 * @param dbName
	 *            数据库名称
	 * @param tablePrefix
	 *            表前缀
	 * @throws SQLException
	 */
	public void alterFieldCharset(String dbName, String tablePrefix) throws SQLException {
		// CHARACTER_SET_NAME is not null,只有字符串有字符集
		String sql = "select * from information_schema.`COLUMNS` WHERE TABLE_SCHEMA='" + dbName + "' and TABLE_NAME like '" + tablePrefix + "%'  and CHARACTER_SET_NAME is not null";
		List<Map<String, Object>> fields = new QueryRunner().query(dataSource.getConnection(), sql, new MapListHandler());
		for (Map<String, Object> field : fields) {
			String alter = "ALTER TABLE `" + field.get("TABLE_NAME") + "` MODIFY COLUMN `" + field.get("COLUMN_NAME") + "` " + field.get("COLUMN_TYPE") + " CHARACTER SET utf8 COLLATE utf8_general_ci;";
			System.out.println(alter);
		}
	}

	/**
	 * 更改表的引擎，字符集，校验规则
	 * 
	 * @param dbName
	 *            数据库名称
	 * @param tablePrefix
	 *            表前缀
	 * @throws SQLException
	 */
	public void alterTableCharset(String dbName, String tablePrefix) throws SQLException {
		String sql = "select * from information_schema.`TABLES` WHERE TABLE_SCHEMA='" + dbName + "' and TABLE_NAME like '" + tablePrefix + "%'";
		List<Map<String, Object>> tables = new QueryRunner().query(dataSource.getConnection(), sql, new MapListHandler());
		for (Map<String, Object> table : tables) {
			String alter = "ALTER TABLE " + table.get("TABLE_NAME") + " ENGINE=InnoDB DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci;";
			System.out.println(alter);
		}
	}

	public static void alterFieldNameToUppercase(File sqlFile) throws IOException {
		String content = FileUtils.readFileToString(sqlFile, StandardCharsets.UTF_8);
		String changedContent = replace(content, "upperCase");
		FileUtils.write(sqlFile, changedContent, StandardCharsets.UTF_8, false);

	}

	public static void alterFieldNameToLowercase(File sqlFile) throws IOException {
		String content = FileUtils.readFileToString(sqlFile, StandardCharsets.UTF_8);
		String changedContent = replace(content, "lowerCase");
		FileUtils.write(sqlFile, changedContent, StandardCharsets.UTF_8, false);
	}
	
	private static String replace(String content,String type){
		Pattern p = Pattern.compile("`(.*?)`");
		Matcher m = p.matcher(content);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String name = m.group(1);
			if("upperCase".equals(type)){
				m.appendReplacement(sb, "`" + StringUtils.upperCase(name) + "`");			
			}else{
				m.appendReplacement(sb, "`" + StringUtils.lowerCase(name) + "`");
			}
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	
	
	
	public static void main(String[] args) {
		String sqlFilePath="C:/Users/kevin/Desktop/temp/coderfun_boot.sql";
		File sqlFile =new File(sqlFilePath);
		try {
			MysqlDBAlter.alterFieldNameToLowercase(sqlFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
