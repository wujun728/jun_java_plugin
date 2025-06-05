package io.github.wujun728.sql;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.pool.DruidPooledConnection;
import io.github.wujun728.db.record.Page;
import io.github.wujun728.sql.engine.DynamicSqlEngine;
import io.github.wujun728.sql.entity.ApiDataSource;
import io.github.wujun728.sql.entity.ApiSql;
import io.github.wujun728.sql.entity.DBConfig;
import io.github.wujun728.sql.entity.SqlWithParam;
import io.github.wujun728.sql.utils.JdbcUtil;
import io.github.wujun728.sql.utils.PoolManager;
import io.github.wujun728.sql.utils.XmlParser;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ApiEngine {

    private static Logger logger = LoggerFactory.getLogger(ApiEngine.class);

    DynamicSqlEngine dynamicSqlEngine = new DynamicSqlEngine();

    DBConfig dbConfig;

    Map<String, Map<String, ApiSql>> sqlMap = new HashMap<>();
    Map<String, ApiDataSource> dataSourceMap;

    public ApiEngine(DBConfig dbConfig) {
        this.dbConfig = dbConfig;
        initXml();
    }

    public void initXml() {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(this.dbConfig.getSql());

            for (Resource res : resources) {
                String filename = res.getFilename();
                if(filename.equals("ds.xml")){
                    continue;
                }
                InputStream inputStream = res.getInputStream();
                String content = IOUtils.toString(inputStream, Charsets.toCharset(StandardCharsets.UTF_8));
                Map<String, ApiSql> stringSqlMap = XmlParser.parseSql(content);
                this.sqlMap.put(filename.split("\\.")[0], stringSqlMap);
                logger.info("APISQL register sql xml: {}", filename);
            }
            Resource dsResource = resolver.getResource(this.dbConfig.getDatasource());
            String filename = dsResource.getFilename();
            InputStream inputStream = dsResource.getInputStream();
            String content = IOUtils.toString(inputStream, Charsets.toCharset(StandardCharsets.UTF_8));
            this.dataSourceMap = XmlParser.parseDatasource(content);
            logger.info("APISQL register datasource xml: {}", filename);
        } catch (Exception e) {
            //logger.error(e.getMessage(), e);
            logger.warn(e.getMessage());
        }
    }

//    public void initApiSql(DataSource dataSource) {
//        try {
//            Connection connection = dataSource.getConnection();
//            String sql_api = " select * from api_sql ";
//            List<Map<String, Object>>  apiSqls = SqlXmlUtil.query(connection,sql_api);
//
//            for(Map map : apiSqls){
//                BeanUtil.copyToList(FieldUtils)
//                MapUtil.toCamelCaseMap(map);
//            }
//
//            for (Resource res : resources) {
//                String filename = res.getFilename();
//                InputStream inputStream = res.getInputStream();
//                String content = IOUtils.toString(inputStream, Charsets.toCharset(StandardCharsets.UTF_8));
//                Map<String, SqlNode> stringSqlMap = XmlParser.parseSql(content);
//                this.sqlMap.put(filename.split("\\.")[0], stringSqlMap);
//                logger.info("APISQL register sql xml: {}", filename);
//            }
//            Resource dsResource = resolver.getResource(this.dbConfig.getDatasource());
//
//            String sql_datasource = " select * from api_datasource ";
//            List<Map<String, Object>>  apiDS = SqlXmlUtil.query(connection,sql_datasource);
//
//
//            String filename = dsResource.getFilename();
//            InputStream inputStream = dsResource.getInputStream();
//            String content = IOUtils.toString(inputStream, Charsets.toCharset(StandardCharsets.UTF_8));
//            this.dataSourceMap = XmlParser.parseDatasource(content);
//            logger.info("APISQL register datasource xml: {}", filename);
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        }
//    }


    public Page<JSONObject> executeQueryPage(String namespace, String sqlId, Map<String, Object> data, int pageNumber, int limit) {
        Page pageVo = new Page();
        ApiSql sql = this.sqlMap.get(namespace).get(sqlId);
        if (!dataSourceMap.containsKey(sql.getDatasourceId())) {
            throw new RuntimeException("datasource not found : " + sql.getDatasourceId());
        }
        data.put("start",(pageNumber-1)*(limit));
        data.put("end",pageNumber*limit);
        ApiDataSource dataSource = dataSourceMap.get(sql.getDatasourceId());
        String pageSql = sql.getText()+ " LIMIT #{start}, #{end} ";
        SqlMeta sqlMeta = dynamicSqlEngine.parse(pageSql, data);
        List<JSONObject> results = ApiEngine.executeQuery(dataSource, sqlMeta.getSql(), sqlMeta.getJdbcParamValues());//executeQuery(namespace, sqlId, data);

        String totalSql = "  select count(1) as count FROM ( "+sql.getText()+" ) AS subquery ";
        SqlMeta sqlMeta2 = dynamicSqlEngine.parse(totalSql, data);

        Long totalpage = ApiEngine.executeQueryInt(dataSource, sqlMeta2.getSql(), sqlMeta2.getJdbcParamValues());
        pageVo.setList(results);
        pageVo.setTotalRow(Math.toIntExact(totalpage));
        pageVo.setPageNumber(pageNumber);
        pageVo.setPageSize(limit);
        pageVo.setTotalPage(Math.toIntExact(totalpage) / limit);
        return pageVo;
    }
    /**
     * execute select sql with parameters, return a list of java bean entity
     *
     * @param namespace name of xml file
     * @param sqlId     sql id in <sql> tag
     * @param data      sql parameters
     * @param clazz     class of java bean entity
     * @return
     */
    public <T> List<T> executeQueryEntity(String namespace, String sqlId, Map<String, Object> data, Class<T> clazz) {
        List<JSONObject> list = executeQuery(namespace, sqlId, data);

        List<T> collect = list.stream().map(t -> JSONUtil.toBean(t,clazz)).collect(Collectors.toList());
        return collect;
    }

    /**
     * execute select sql with parameters, return a list of JSONObject
     *
     * @param namespace name of xml file
     * @param sqlId     sql id in <sql> tag
     * @param data      sql parameters
     * @return
     */
    public List<JSONObject> executeQuery(String namespace, String sqlId, Map<String, Object> data) {
        try {
            if (!sqlMap.containsKey(namespace)) {
                throw new RuntimeException("namespace not found : " + namespace);
            } else {
                if (!sqlMap.get(namespace).containsKey(sqlId)) {
                    throw new RuntimeException("sqlId not found : " + sqlId);
                } else {
                    ApiSql sql = this.sqlMap.get(namespace).get(sqlId);
                    if (!dataSourceMap.containsKey(sql.getDatasourceId())) {
                        throw new RuntimeException("datasource not found : " + sql.getDatasourceId());
                    }
                    ApiDataSource dataSource = dataSourceMap.get(sql.getDatasourceId());
                    SqlMeta sqlMeta = dynamicSqlEngine.parse(sql.getText(), data);

                    return ApiEngine.executeQuery(dataSource, sqlMeta.getSql(), sqlMeta.getJdbcParamValues());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public JSONObject executeQueryOne(String namespace, String sqlId, Map<String, Object> data) {
        try {
            if (!sqlMap.containsKey(namespace)) {
                throw new RuntimeException("namespace not found : " + namespace);
            } else {
                if (!sqlMap.get(namespace).containsKey(sqlId)) {
                    throw new RuntimeException("sqlId not found : " + sqlId);
                } else {
                    ApiSql sql = this.sqlMap.get(namespace).get(sqlId);
                    if (!dataSourceMap.containsKey(sql.getDatasourceId())) {
                        throw new RuntimeException("datasource not found : " + sql.getDatasourceId());
                    }
                    ApiDataSource dataSource = dataSourceMap.get(sql.getDatasourceId());
                    SqlMeta sqlMeta = dynamicSqlEngine.parse(sql.getText(), data);
                    return ApiEngine.executeQueryById(dataSource, sqlMeta.getSql(), sqlMeta.getJdbcParamValues());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public int executeQueryCount(String namespace, String sqlId, Map<String, Object> data) {
        try {
            if (!sqlMap.containsKey(namespace)) {
                throw new RuntimeException("namespace not found : " + namespace);
            } else {
                if (!sqlMap.get(namespace).containsKey(sqlId)) {
                    throw new RuntimeException("sqlId not found : " + sqlId);
                } else {
                    ApiSql sql = this.sqlMap.get(namespace).get(sqlId);
                    if (!dataSourceMap.containsKey(sql.getDatasourceId())) {
                        throw new RuntimeException("datasource not found : " + sql.getDatasourceId());
                    }
                    ApiDataSource dataSource = dataSourceMap.get(sql.getDatasourceId());
                    SqlMeta sqlMeta = dynamicSqlEngine.parse(sql.getText(), data);
                    Object obj =  ApiEngine.executeQueryOneColumn(dataSource, sqlMeta.getSql(), sqlMeta.getJdbcParamValues());
                    if(obj instanceof Integer){
                        return (int) obj;
                    }else {
                        return (Integer) obj;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * execute select sql without parameters, return a list of JSONObject
     *
     * @param namespace name of xml file
     * @param sqlId     sql id in <sql> tag
     * @return
     */
    public List<JSONObject> executeQuery(String namespace, String sqlId) {
        return executeQuery(namespace, sqlId, null);
    }

    /**
     * execute select sql without parameters, return a list of java bean entity
     *
     * @param namespace name of xml file
     * @param sqlId     sql id in <sql> tag
     * @param clazz     class of java bean entity
     * @return
     */
    public <T> List<T> executeQueryEntity(String namespace, String sqlId, Class<T> clazz) {
        return executeQueryEntity(namespace, sqlId, null, clazz);
    }

    /**
     * execute insert/delete/update sql with parameters
     *
     * @param namespace name of xml file
     * @param sqlId     sql id in <sql> tag
     * @param data      sql parameters
     * @return
     */
    public int executeUpdate(String namespace, String sqlId, Map<String, Object> data) {
        try {
            if (!sqlMap.containsKey(namespace)) {
                throw new RuntimeException("namespace not found : " + namespace);
            } else {
                if (!sqlMap.get(namespace).containsKey(sqlId)) {
                    throw new RuntimeException("sqlId not found : " + sqlId);
                } else {
                    ApiSql sql = this.sqlMap.get(namespace).get(sqlId);
                    if (!dataSourceMap.containsKey(sql.getDatasourceId())) {
                        throw new RuntimeException("datasource not found : " + sql.getDatasourceId());
                    }
                    ApiDataSource dataSource = dataSourceMap.get(sql.getDatasourceId());
                    SqlMeta sqlMeta = dynamicSqlEngine.parse(sql.getText(), data);

                    return ApiEngine.executeUpdate(dataSource, sqlMeta.getSql(), sqlMeta.getJdbcParamValues());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * execute insert/delete/update sql without parameters
     *
     * @param namespace name of xml file
     * @param sqlId     sql id in <sql> tag
     * @return
     */
    public int executeUpdate(String namespace, String sqlId) {
        return executeUpdate(namespace, sqlId, null);
    }

    /**
     * execute select/insert/delete/update sql with parameters
     *
     * @param namespace name of xml file
     * @param sqlId     sql id in <sql> tag
     * @param data      sql parameters
     * @return
     */
    public Object execute(String namespace, String sqlId, Map<String, Object> data) {
        try {
            if (!sqlMap.containsKey(namespace)) {
                throw new RuntimeException("namespace not found : " + namespace);
            } else {
                if (!sqlMap.get(namespace).containsKey(sqlId)) {
                    throw new RuntimeException("sqlId not found : " + sqlId);
                } else {
                    ApiSql sql = this.sqlMap.get(namespace).get(sqlId);
                    if (!dataSourceMap.containsKey(sql.getDatasourceId())) {
                        throw new RuntimeException("datasource not found : " + sql.getDatasourceId());
                    }
                    ApiDataSource dataSource = dataSourceMap.get(sql.getDatasourceId());
                    SqlMeta sqlMeta = dynamicSqlEngine.parse(sql.getText(), data);

                    return ApiEngine.execute(dataSource, sqlMeta.getSql(), sqlMeta.getJdbcParamValues());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * execute select/insert/delete/update sql without parameters
     *
     * @param namespace name of xml file
     * @param sqlId     sql id in <sql> tag
     * @return
     */
    public Object execute(String namespace, String sqlId) {
        return execute(namespace, sqlId, null);
    }

    public List<Object> executeWithinTransaction(List<SqlWithParam> sqlList) {
        DruidPooledConnection connection = null;

        try {
            String namespace = sqlList.get(0).getNamespace();
            connection = getConnection(namespace, sqlList.get(0).getSqlId());
            connection.setAutoCommit(false);

            if (!sqlMap.containsKey(namespace)) {
                throw new RuntimeException("namespace not found : " + namespace);
            } else {
                List<Object> result = new ArrayList<>();
                for (int i = 0; i < sqlList.size(); i++) {
                    String sqlId = sqlList.get(i).getSqlId();

                    if (!sqlMap.get(namespace).containsKey(sqlId)) {
                        throw new RuntimeException("sqlId not found : " + sqlId);
                    } else {
                        ApiSql sql = this.sqlMap.get(namespace).get(sqlId);
                        SqlMeta sqlMeta = dynamicSqlEngine.parse(sql.getText(), sqlList.get(i).getParameters());

                        Object object = JdbcUtil.execute(connection, sqlMeta.getSql(), sqlMeta.getJdbcParamValues());
                        result.add(object);
                    }
                }
                connection.commit();
                return result;
            }
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private DruidPooledConnection getConnection(String namespace, String sqlId) throws SQLException {


        if (!sqlMap.containsKey(namespace)) {
            throw new RuntimeException("namespace not found : " + sqlId);
        } else {
            if (!sqlMap.get(namespace).containsKey(sqlId)) {
                throw new RuntimeException("sqlId not found : " + sqlId);
            } else {
                ApiSql sql = this.sqlMap.get(namespace).get(sqlId);
                if (!dataSourceMap.containsKey(sql.getDatasourceId())) {
                    throw new RuntimeException("datasource not found : " + sql.getDatasourceId());
                }
                ApiDataSource dataSource = dataSourceMap.get(sql.getDatasourceId());
                DruidPooledConnection connection = PoolManager.getPooledConnection(dataSource);
                return connection;
            }
        }

    }

    // --------------------------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------------------------

    public static List<JSONObject> executeQuery(ApiDataSource datasource, String sql, List<Object> jdbcParamValues) {
        DruidPooledConnection connection = null;
        try {
            connection = PoolManager.getPooledConnection(datasource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return JdbcUtil.executeQuery(connection,sql,jdbcParamValues);
    }

    public static JSONObject executeQueryById(ApiDataSource datasource, String sql, List<Object> jdbcParamValues) {
        DruidPooledConnection connection = null;
        try {
            connection = PoolManager.getPooledConnection(datasource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return JdbcUtil.executeQueryById(connection,sql,jdbcParamValues);
    }
    public static Long executeQueryInt(ApiDataSource datasource, String sql, List<Object> jdbcParamValues) {
        Object obj = executeQueryOneColumn(datasource, sql, jdbcParamValues);
        if(obj instanceof Long){
            return (Long) obj;
        }else{
            return Long.valueOf(String.valueOf(obj));
        }
    }

    public static String executeQueryString(ApiDataSource datasource, String sql, List<Object> jdbcParamValues) {
        Object obj = executeQueryOneColumn(datasource, sql, jdbcParamValues);
        return (String) obj;
    }

    public static Object executeQueryOneColumn(ApiDataSource datasource, String sql, List<Object> jdbcParamValues) {
        DruidPooledConnection connection = null;
        try {
            connection = PoolManager.getPooledConnection(datasource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return JdbcUtil.executeQueryOneColumn(connection,sql,jdbcParamValues);
    }

    public static int executeUpdate(ApiDataSource datasource, String sql, List<Object> jdbcParamValues) {
        DruidPooledConnection connection = null;
        try {
            connection = PoolManager.getPooledConnection(datasource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return JdbcUtil.executeUpdate(connection,sql,jdbcParamValues);
    }

    public static Object execute(ApiDataSource datasource, String sql, List<Object> jdbcParamValues) {
        DruidPooledConnection connection = null;
        try {
            connection = PoolManager.getPooledConnection(datasource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return JdbcUtil.execute(connection,sql,jdbcParamValues);
    }


}