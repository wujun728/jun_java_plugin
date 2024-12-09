
package io.github.wujun728.db;

import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import io.github.wujun728.db.dialect.*;
import io.github.wujun728.db.utils.DataSourcePool;
import io.github.wujun728.db.utils.RecordUtil;
import io.github.wujun728.db.utils.SqlUtil;
import io.github.wujun728.rest.entity.ApiSql;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.github.wujun728.db.DbPro.dataSourceMap;
import static io.github.wujun728.db.DbPro.jdbcTemplateMap;

/**
 * Db 操作类，支持SQL模式、Record模式，Bean模式（JPA Bean模式、Mybatis Entity模式）
 */
@SuppressWarnings("rawtypes")
public class Db<T> {

	private static DbPro MAIN = null;
	private static Map<String, String> TABLE_PK_MAP = new HashMap<>();
//	private static final Map<String, JdbcTemplate> jdbcTemplateMap = new ConcurrentHashMap<>(24);
//	private static final Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>(24);

	public final static String main = "main";
//	public final static String MAIN_CONFIG_NAME = "main";

	private static Dialect dialect = new MysqlDialect();
//	private static JdbcTemplate jdbcTemplate = null;
//	private static DataSource dataSource = null;

	private static final Map<String, DbPro> cache = new HashMap<>(32, 0.25F);

	static {
		try {
			DataSource dataSource = SpringUtil.getBean(DataSource.class);
			if(dataSource!=null){
//				dataSourceMap.put(main, dataSource);
				Db.init(Db.main,dataSource);
				/*try {
					jdbcTemplate = SpringUtil.getBean(JdbcTemplate.class);
					jdbcTemplateMap.put(main, jdbcTemplate);
				} catch (Exception e) {
					System.out.println("warning : ExceptionInInitializerError 当前非Spring容器运行，请添加spring jdbc支持。" + e.getMessage());
				}*/
			}
		} catch (Exception e) {
			System.out.println("warning : ExceptionInInitializerError 当前非Spring容器运行，请首次初始化DbTemplate.init的数据源。" + e.getMessage());
		}
	}

	public static DbPro use() {
		MAIN.setDataSource(dataSourceMap.get(main));
		MAIN.setJdbcTemplate(jdbcTemplateMap.get(main));
		return MAIN;
	}


//	public static Db use() {
//		return use(main);
//	}

	public static DbPro use(String configName) {
		DbPro result = cache.get(configName);
		if (result == null || dataSourceMap.get(configName) == null || jdbcTemplateMap.get(configName) == null) {
			System.err.println("error : 当前Db.use(" + configName + ")的数据源,不存在。请使用[main]数据源,或者使用初始化的configName数据源。");
			throw new RuntimeException("error : 当前Db.use(" + configName + ")的数据源,不存在。请使用[main]数据源,或者使用初始化的configName数据源。");
		}
		result.setDataSource(dataSourceMap.get(configName));
		result.setJdbcTemplate(jdbcTemplateMap.get(configName));
		return result;
	}

	public static void init(String configName, DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplateMap.put(configName, jdbcTemplate);
		dataSourceMap.put(configName, dataSource);
		DbPro result = new DbPro(configName);
		cache.put(configName, result);
		MAIN = cache.get(main);
		registerRecord(dataSource);
	}

	static {
		try {
			DataSource dataSource = SpringUtil.getBean(DataSource.class);
			dataSourceMap.put(main, dataSource);
		} catch (Exception e) {
			System.err.println("warning : ExceptionInInitializerError 当前非Spring容器运行，请首次初始化Db.init的数据源。" + e.getMessage());
		}
		try {
			JdbcTemplate jdbcTemplate = SpringUtil.getBean(JdbcTemplate.class);
			jdbcTemplateMap.put(main, jdbcTemplate);
		} catch (Exception e) {
			System.err.println("warning : ExceptionInInitializerError 当前非Spring容器运行，请添加spring jdbc支持。" + e.getMessage());
		}
	}



	/**
	 * main方法，测试使用
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/db_qixing_bk?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true";
		String username = "root";
		String password = "";
		String driver = "com.mysql.cj.jdbc.Driver";
		DataSource dataSource = DataSourcePool.init("main", url, username, password);
		//
		Db.init(Db.main, dataSource);
		//查询数据并返回单条Record
		Record record = Db.findById("biz_mail", 1);
		StaticLog.info(JSONUtil.toJsonPrettyStr(record));

		List data1 = Db.use(main).queryAll(ApiSql.class);
		List data12 = Db.queryAll(ApiSql.class);

		//查询数据并转为Bean清单
		//List<ApiSql> data2 = Db.queryForBeanList(" SELECT * FROM api_sql ", ApiSql.class, null);

		//Page<Record> data3 = Db.paginate(1, 10, " SELECT * ", " FROM api_sql ");
		StaticLog.info(JSONUtil.toJsonPrettyStr(data1));
		StaticLog.info(JSONUtil.toJsonPrettyStr(data12));
	}

	public static Page<Record> paginate(Integer pageNumber, Integer limit, String select, String from) {
		return MAIN.paginate(pageNumber,limit,select,from);
	}

	public static <T> List queryForBeanList(String sql, Class clazz, Object... params) {
		return MAIN.queryForBeanList(sql, clazz, params) ;
	}

	public static Record findById(String biz_mail, int i) {
		return null;
	}

	private static void registerRecord(DataSource dataSource) {
		List<String> tables = MetaUtil.getTables(dataSource);
		tables.forEach(tableName -> {
			Table table = MetaUtil.getTableMeta(dataSource, tableName);
			String pkStr =  String.join(",", table.getPkNames());
			TABLE_PK_MAP.put(tableName, pkStr);// map不清，只做替换
		});
	}

	public static String getPkNames(String tableName) {
		return TABLE_PK_MAP.get(tableName);
	}
	/*public static String getPkNames(DataSource ds, String tableName) {
		Table table = MetaUtil.getTableMeta(ds, tableName);
		return String.join(",", table.getPkNames());
	}*/


	private static Dialect getDialect(DataSource dataSource) {
		try {
			String dbName = dataSource.getConnection().getMetaData().getDatabaseProductName();
			if(dbName.equalsIgnoreCase("Oracle")){
				return new OracleDialect();
			}else if(dbName.equalsIgnoreCase("mysql")){
				return new MysqlDialect();
			}else if(dbName.equalsIgnoreCase("sqlite")){
				return new Sqlite3Dialect();
			}else if(dbName.equalsIgnoreCase("postgresql")){
				return new PostgreSqlDialect();
			}else{
				return new AnsiSqlDialect();
			}
		} catch (SQLException e){
			throw new RuntimeException("获取数据库连接失败");
		}
	}



	//************************************************************************************************************************************************
	//Record 111111111111111111  begin   **************************************************************************************************************************************
	//************************************************************************************************************************************************


	public static  List<Map<String, Object>> findMaps(String sql, Object... paras) {
		return MAIN.findMaps(sql,paras);
	}

	@Deprecated
	public static  List<Map<String, Object>> findMaps2(String sql) {
		return MAIN.findMaps2(sql);
	}

	@Deprecated
	public static int updateBySql(String sql) {
		return MAIN.updateBySql(sql);
	}


	public static Boolean deleteById(String tableName, Object... idValues) {
		return MAIN.deleteById(tableName,idValues);
	}

	public static   Boolean deleteByIds2(String tableName, String primaryKey, Object... idValues) {
		return MAIN.deleteByIds2(tableName,primaryKey,idValues);
	}

	public static Boolean deleteBySql(String sql, Object... paras) {
		return MAIN.deleteBySql(sql,paras);
	}

	public static Boolean deleteBySql(String sql) {
		return MAIN.deleteBySql(sql);
	}

	public static <T> List findObjectList(Class clazz, String sql, Object... params) {
		return MAIN.findObjectList(clazz,sql,params);
	}


	public static Integer saveBackPrimaryKey(Object bean) {
		return MAIN.saveBackPrimaryKey(bean);
	}


	public static Integer saveEntity(Object bean) {
		return MAIN.saveEntity(bean);
	}


	public static Integer updateEntity(Object bean) {
		return MAIN.updateEntity(bean);
	}


	public static Integer deleteEntity(Object bean) {
		return MAIN.deleteEntity(bean);
	}


	public static Object findObjectById(Class beanClass, Object... id) {
		return MAIN.findObjectById(beanClass,id);
	}

	public static Object findObjectByParams(Class beanClass, String[] fields, Object... parmas) {
		return MAIN.findObjectByParams(beanClass,fields,parmas);
	}

	public static Object findObject(Class beanClass, String sql, Object... params) {
		return MAIN.findObject(beanClass,sql,params);
	}

	@Deprecated
	public static <T> List findListAll(Class beanClass) {
		return MAIN.findListAll(beanClass);
	}


	public static List<Map<String, Object>> findList(Class beanClass, Map<String, Object> params) {
		return MAIN.findList(beanClass,params);
	}


	public static List<Map<String, Object>> findList(Class beanClass, String field, Object parmas) {
		return MAIN.findList(beanClass,field,parmas);
	}


	public static List<Map<String, Object>> findList(Class beanClass, String[] fields, Object... parmas) {
		return MAIN.findList(beanClass,fields,parmas);
	}


	public static Page findPages(Class beanClass, int page, int rows) {
		return MAIN.findPages(beanClass, page, rows);
	}
	public static Page findPages(Class beanClass, int page, int rows, Map<String, Object> params) {
		return MAIN.findPages(beanClass,page,rows,params);
	}


	/********************************************************************************
	 * 业务层 禁止写sql语句，以下方法仅供子类调用
	 ********************************************************************************/


	public static List<Map<String, Object>> findList(String sql, Object... params) {
		return MAIN.findList(sql, params);
	}

	public static List<Map<String, Object>> findList(String sql) {
		return MAIN.findList(sql);
	}


	public static Page findPages(String sql, int page, int rows, Object... params) {
		return MAIN.findPages(sql,page,rows,params);
	}

	public static int count(String sql, Object... params) {
		return MAIN.count(sql,params);
	}
	public static int findForInt(String sql, Object... params) {
		return MAIN.findForInt(sql,params);
	}
	public static String findForString(String sql, Object... params) {
		return MAIN.findForString(sql,params);
	}
	public static Date findForDate(String sql, Object... params) {
		return MAIN.findForDate(sql,params);
	}

	/********************************************************************************
	 * 私有方法
	 ********************************************************************************/


	//************************************************************************************************************************************************
	//Record 111111111111111111  end   **************************************************************************************************************************************
	//************************************************************************************************************************************************


	public static Object executeSqlXml(String sqlXml, Map params) throws SQLException {
		return MAIN.executeSqlXml(sqlXml,params);
	}
//	public static Object executeSqlXml(String sqlXml, Map params) throws SQLException {
//		return MAIN.executeSqlXml(sqlXml,params);
//	}
//	public static Object executeSqlXml(String sqlXml, Map params) throws SQLException {
//		return MAIN.executeSqlXml(sqlXml,params);
//	}

	public static List<Map<String, Object>> findMaps(String sql) {
		return MAIN.findMaps(sql);
	}
	public static  <T> T findObjById(Class<T> clazz, Object idValue) {
		return MAIN.findById(clazz,idValue);
	}


	public static boolean deleteById(String tableName, Object idValue) {
		return MAIN.deleteById(tableName, idValue);
	}

	public static boolean deleteById(String tableName, String primaryKey, Object idValue) {
		return MAIN.deleteById(tableName, primaryKey, idValue);
	}

	public static <T> List queryAll(Class beanClass) {
		return MAIN.queryAll(beanClass);
	}
}



