
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
        Record record = Db.use().findById("biz_mail", 1);
        StaticLog.info(JSONUtil.toJsonPrettyStr(record));
        List data1 = Db.use(main).find(SqlUtil.getSelect(ApiSql.class).getSql());
        List data12 = Db.use().find(SqlUtil.getSelect(ApiSql.class).getSql());

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


}



