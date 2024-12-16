
package io.github.wujun728.db.record;

import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import io.github.wujun728.db.utils.DataSourcePool;
import io.github.wujun728.db.utils.SqlUtil;
import io.github.wujun728.rest.entity.ApiSql;
import io.github.wujun728.sql.SqlXmlUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

import static io.github.wujun728.db.record.DbPro.dataSourceMap;
import static io.github.wujun728.db.record.DbPro.jdbcTemplateMap;

/**
 * Db 操作类，支持SQL模式、Record模式，Bean模式（JPA Bean模式、Mybatis Entity模式）
 */
@SuppressWarnings("rawtypes")
public class Db<T> {

    private static DbPro MAIN = null;

    public final static String main = "main";
//    private static final Map<String, DbPro> cache = new HashMap<>(32, 0.25F);
    static {
        try {
            DataSource dataSource = SpringUtil.getBean(DataSource.class);
            if (dataSource != null) {
//				dataSourceMap.put(main, dataSource);
                Db.init(Db.main, dataSource);
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

    public static DbPro use(String configName) {
        DbPro result = DbPro.cache.get(configName);
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
        MAIN = DbPro.use(main);
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
        List data1 = Db.use(main).find(SqlUtil.getSelect(ApiSql.class).getSql());
        List data12 = Db.find(SqlUtil.getSelect(ApiSql.class).getSql());

        //查询数据并转为Bean清单
        //List<ApiSql> data2 = Db.queryForBeanList(" SELECT * FROM api_sql ", ApiSql.class, null);
        //Page<Record> data3 = Db.paginate(1, 10, " SELECT * ", " FROM api_sql ");
        StaticLog.info(JSONUtil.toJsonPrettyStr(data1));
        StaticLog.info(JSONUtil.toJsonPrettyStr(data12));
    }


	/*public static String getPkNames(DataSource ds, String tableName) {
		Table table = MetaUtil.getTableMeta(ds, tableName);
		return String.join(",", table.getPkNames());
	}*/


//	************************************************************************************************************************************************
//	Class 111111111111111111  begin   **************************************************************************************************************
//	************************************************************************************************************************************************


    public static JdbcTemplate getJdbcTemplate() {
        return MAIN.getJdbcTemplate();
    }
    public static <T> List<T> findBeanList(Class<T> clazz, String sql) {
        return MAIN.findBeanList(clazz, sql);
    }

    public static <T> List findBeanList(Class clazz, String sql, Object... params) {
        return MAIN.findBeanList(clazz, sql, params);
    }
    public <T> List findMapList(Class clazz, String sql, Object... params) {
        return MAIN.findMapList(clazz, sql, params);
    }
    public <T> List findRecordList(Class clazz, String sql, Object... params) {
        return MAIN.findRecordList(clazz, sql, params);
    }


    public static List<Map<String, Object>> findBeanList(Class beanClass, Map<String, Object> params) {
        return MAIN.findBeanList(beanClass, params);
    }



    public static Integer saveBeanBackPrimaryKey(Object bean) {
        return MAIN.saveBeanBackPrimaryKey(bean);
    }

    public static Integer saveBean(Object bean) {
        return MAIN.saveBean(bean);
    }

    public static Integer updateBean(Object bean) {
        return MAIN.updateBean(bean);
    }

    public static Integer deleteBean(Object bean) {
        return MAIN.deleteBean(bean);
    }

    public static  <T> T  findBeanById(Class beanClass, Object... id) {
        return (T) MAIN.findBeanById(beanClass, id);
    }

    public static <T> T findBeanByIds(Class beanClass, String primaryKeys, Object... id) {
        return MAIN.findBeanByIds(beanClass,primaryKeys, id);
    }

    public  static Object  findBeanBySql(Class clazz, String sql, Object... params) {
        return MAIN.findBeanBySql(clazz,sql, params);
    }

    public static <T> Page findBeanPages(Class beanClass, int page, int rows) {
        return MAIN.findBeanPages(beanClass, page, rows);
    }

    public static <T> Page findBeanPages(Class beanClass, int page, int rows, Map<String, Object> params) {
        return MAIN.findBeanPages(beanClass, page, rows, params);
    }


    /**
     * 查询
     */
	/*public static  Object queryForObject(Class beanClass, String sql, Object[] params) {
		try {
			return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(beanClass), params);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}*/
    /*public static <T> List findEntityAll(Class beanClass) {
        return MAIN.findEntityAll(beanClass);
    }*/


    public static List<Map<String, Object>> queryList(Class beanClass, Map<String, Object> params) {
        return MAIN.queryList(beanClass, params);
    }


    public static List<Map<String, Object>> queryBeanFieldsList(Class beanClass, String[] fields, Object[] parmas) {
        return MAIN.queryBeanFieldsList(beanClass, fields, parmas);
    }


    public static Page queryBeanPage(Class beanClass, int page, int rows) {
        return MAIN.queryBeanPage(beanClass, page, rows);
    }

    public static Page queryBeanPage(Class beanClass, int page, int rows, Map<String, Object> params) {
        return MAIN.queryBeanPage(beanClass, page, rows, params);
    }


    //************************************************************************************************************************************************
    //Class 111111111111111111  end   ***************************************************************************************************************
    //************************************************************************************************************************************************


    /*************************************************************************************************************************************************
     SQL 111111111111111111  begin   ***************************************************************************************************************
     * 业务层 禁止写sql语句，以下方法仅供子类调用
     *************************************************************************************************************************************************/

    public static List<Map<String, Object>> queryList(String sql, Object[] params) {
        return MAIN.queryList(sql, params);
    }

    public static List<Map<String, Object>> queryList(String sql) {
        return MAIN.queryList(sql);
    }


	/*public static   List<Map<String, Object>> query(String sql, Object[] paras) {
		List results = jdbcTemplate.queryForList(sql, paras);
		return results;
	}

	public static   List<Map<String, Object>> query(String sql) {
		List results = jdbcTemplate.queryForList(sql);
		return results;
	}*/


    public static String queryForString(String sql, Object[] params) {
        return MAIN.queryForString(sql, params);
    }

    public static Date queryForDate(String sql, Object[] params) {
        return MAIN.queryForDate(sql, params);
    }


    public static Page<Map> queryMapPages(String sql, int page, int rows, Object[] params) {
        return MAIN.queryMapPages(sql, page, rows, params);
    }

    public static int count(String sql, Object... params) {
        return MAIN.count(sql, params);
    }

    public static int queryForInt(String sql, Object[] params) {
        return MAIN.queryForInt(sql, params);
    }


    public static int updateSql(String sql) {
        return MAIN.updateSql(sql);
    }


    public static Boolean deleteById(String tableName, Object... idValues) {
        return MAIN.deleteById(tableName, idValues);
    }

    public static Boolean deleteByIds(String tableName, String primaryKey, Object... idValues) {
        return MAIN.deleteByIds(tableName, primaryKey, idValues);
    }


    public static Boolean deleteBySql(String sql, Object... paras) {
        return MAIN.deleteBySql(sql, paras);
    }

    public static Boolean deleteBySql(String sql) {
        return MAIN.deleteBySql(sql);
    }

    /*************************************************************************************************************************************************
     SQL 111111111111111111  end   ***************************************************************************************************************
     *************************************************************************************************************************************************/


    //************************************************************************************************************************************************
    //Record begin  **************************************************************************************************************************************
    //************************************************************************************************************************************************

    /**
     * 根据id查找
     *
     * @param tableName 表名
     * @param id        主键
     * @return Record
     */
    public static Record findById(String tableName, Object id) {
        return findRecordById(tableName,id);
    }
    public static Record findRecordById(String tableName, Object id) {
        return MAIN.findRecordById(tableName, id);
    }


    public static Record findByIds(String tableName, String primaryKey, Object... idValues) {
        return MAIN.findByIds(tableName, primaryKey, idValues);
    }
    public static List<Record> findByColumnValueRecords(String tableName, String columnNames, Object... columnValues) {
        return MAIN.findByColumnValueRecords(tableName, columnNames, columnValues);
    }

    public static <T> List  findByColumnValueBeans(Class clazz, String columnNames, Object... columnValues) {
        return MAIN.findByColumnValueBeans(clazz, columnNames, columnValues);
    }
    public static <T> List  findByWhereSqlForBean(Class clazz, String whereSql, Object... columnValues) {
        return MAIN.findByWhereSqlForBean(clazz, whereSql, columnValues);
    }

    public static List<Record> find(String sql) {
        return MAIN.find(sql);
    }


    public static List<Record> find(String sql, Object... param) {
        return MAIN.find(sql, param);
    }

    public static Page<Record> paginate(Integer pageNumber, Integer limit, String select, String from) {
        return MAIN.paginate(pageNumber, limit, select, from);
    }

    public static Page<Record> paginate(Integer pageNumber, Integer limit, String select, String from, Map<String, Object> params) {
        return MAIN.paginate(pageNumber, limit, select, from, params);
    }


    /**
     * 保存record
     *
     * @param tableName 表名
     * @param record    record对象
     */
    public static boolean save(String tableName, Record record) {
        return MAIN.save(tableName, record);
    }

    public static boolean update(String tableName, Record record) {
        return MAIN.update(tableName, record);
    }

    public static boolean delete(String tableName, Record record) {
        return MAIN.delete(tableName, record);
    }


    //************************************************************************************************************************************************
    //Record end  **************************************************************************************************************************************
    //************************************************************************************************************************************************


    //************************************************************************************************************************************************
    //Mybatis  XML  SQL 111111111111111111  end   SqlXmlUtil *************************************************************************
    //************************************************************************************************************************************************

    public static Object executeSqlXml(String sqlXml, Map params) throws SQLException {
        return MAIN.executeSqlXml(sqlXml, params);
    }

    public static int updateSqlXml(String sqlXml, Map params) throws SQLException {
        return MAIN.updateSqlXml(sqlXml, params);
    }
    public static List<Map<String, Object>> querySqlXml(String sqlXml, Map params) throws SQLException {
        return MAIN.querySqlXml(sqlXml, params);
    }

    //************************************************************************************************************************************************
    //Mybatis  XML  SQL 111111111111111111  end   SqlXmlUtil *************************************************************************
    //************************************************************************************************************************************************


}



