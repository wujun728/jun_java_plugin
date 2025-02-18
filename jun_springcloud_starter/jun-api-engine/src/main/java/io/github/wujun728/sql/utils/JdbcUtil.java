package io.github.wujun728.sql.utils;

import cn.hutool.log.StaticLog;
import com.alibaba.fastjson.JSONObject;
import io.github.wujun728.db.record.Page;
import io.github.wujun728.sql.SqlMeta;
import io.github.wujun728.sql.engine.DynamicSqlEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class JdbcUtil {

    static DynamicSqlEngine engine = new DynamicSqlEngine();
    //DynamicSqlEngine dynamicSqlEngine = new DynamicSqlEngine();

    public static DynamicSqlEngine getEngine() {
        return engine;
    }

    private static Logger logger = LoggerFactory.getLogger(JdbcUtil.class);


    public static List<JSONObject> executeQuery(Connection connection, String sql, List<Object> jdbcParamValues) {
        logger.debug(sql);
        //DruidPooledConnection connection = null;
        try {
            //connection = PoolManager.getPooledConnection(datasource);
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            //参数注入
            for (int i = 1; i <= jdbcParamValues.size(); i++) {
                statement.setObject(i, jdbcParamValues.get(i - 1));
            }
            ResultSet rs = statement.executeQuery();
            int columnCount = rs.getMetaData().getColumnCount();
            List<String> columns = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = rs.getMetaData().getColumnLabel(i);
                columns.add(columnName);
            }
            List list = new ArrayList();
            while (rs.next()) {
                JSONObject jo = new JSONObject();
                columns.stream().forEach(t -> {
                    try {
                        Object value = rs.getObject(t);
                        jo.put(t, value);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
                list.add(jo);
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    public static JSONObject executeQueryById(Connection connection, String sql, List<Object> jdbcParamValues) {
        logger.debug(sql);
        //DruidPooledConnection connection = null;
        try {

            //connection = PoolManager.getPooledConnection(datasource);
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            //参数注入
            for (int i = 1; i <= jdbcParamValues.size(); i++) {
                statement.setObject(i, jdbcParamValues.get(i - 1));
            }
            ResultSet rs = statement.executeQuery();
            int columnCount = rs.getMetaData().getColumnCount();
            List<String> columns = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = rs.getMetaData().getColumnLabel(i);
                columns.add(columnName);
            }
            List<JSONObject> list = new ArrayList();
            while (rs.next()) {
                JSONObject jo = new JSONObject();
                columns.stream().forEach(t -> {
                    try {
                        Object value = rs.getObject(t);
                        jo.put(t, value);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
                list.add(jo);
            }
            if(list.size()>0){
                StaticLog.info("当前查询到{}条数据",list.size());
                return list.get(0);
            }else{
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static Long executeQueryInt(Connection connection, String sql, List<Object> jdbcParamValues) {
        Object obj = executeQueryOneColumn(connection, sql, jdbcParamValues);
        if(obj instanceof Long){
            return (Long) obj;
        }else{
            return Long.valueOf(String.valueOf(obj));
        }
    }

    public static String executeQueryString(Connection connection, String sql, List<Object> jdbcParamValues) {
        Object obj = executeQueryOneColumn(connection, sql, jdbcParamValues);
        return (String) obj;
    }



    public static Object executeQueryOneColumn(Connection connection, String sql, List<Object> jdbcParamValues) {
        logger.debug(sql);
        //DruidPooledConnection connection = null;
        try {
            //connection = PoolManager.getPooledConnection(datasource);
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            //参数注入
            for (int i = 1; i <= jdbcParamValues.size(); i++) {
                statement.setObject(i, jdbcParamValues.get(i - 1));
            }

            ResultSet rs = statement.executeQuery();
            int columnCount = rs.getMetaData().getColumnCount();
            List<String> columns = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = rs.getMetaData().getColumnLabel(i);
                columns.add(columnName);
            }
            if(columnCount>1){
                throw new RuntimeException(" only allow one column ");
            }
            AtomicReference count = new AtomicReference();
            while (rs.next()) {
                columns.stream().forEach(t -> {
                    try {
                        count.set(rs.getObject(t));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
                StaticLog.info("当前查询到的count数据{}",count.get());
                return count.get();
            }
            return count.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    public static int executeUpdate(Connection connection, String sql, List<Object> jdbcParamValues) {
        logger.debug(sql);
        //DruidPooledConnection connection = null;
        try {
            //connection = PoolManager.getPooledConnection(datasource);
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            //参数注入
            for (int i = 1; i <= jdbcParamValues.size(); i++) {
                statement.setObject(i, jdbcParamValues.get(i - 1));
            }
            int i = statement.executeUpdate();
            return i;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }



    public static Object execute(Connection connection, String sql, List<Object> jdbcParamValues) {
        logger.debug(sql);
        //DruidPooledConnection connection = null;
        try {
            //connection = PoolManager.getPooledConnection(datasource);
            connection.setAutoCommit(true);
            PreparedStatement statement = connection.prepareStatement(sql);
            //参数注入
            for (int i = 1; i <= jdbcParamValues.size(); i++) {
                statement.setObject(i, jdbcParamValues.get(i - 1));
            }
            boolean hasResultSet = statement.execute();
            if (hasResultSet) {
                ResultSet rs = statement.getResultSet();
                int columnCount = rs.getMetaData().getColumnCount();

                List<String> columns = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = rs.getMetaData().getColumnLabel(i);
                    columns.add(columnName);
                }
                List<JSONObject> list = new ArrayList<>();
                while (rs.next()) {
                    JSONObject jo = new JSONObject();
                    columns.stream().forEach(t -> {
                        try {
                            Object value = rs.getObject(t);
                            jo.put(t, value);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    });
                    list.add(jo);
                }
                return list;
            } else {
                int updateCount = statement.getUpdateCount();
                return updateCount;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


    }


    /*public static Object execute(Connection connection, String sql, List<Object> jdbcParamValues) throws SQLException {
        logger.debug(sql);
        PreparedStatement statement = connection.prepareStatement(sql);
        //参数注入
        for (int i = 1; i <= jdbcParamValues.size(); i++) {
            statement.setObject(i, jdbcParamValues.get(i - 1));
        }
        boolean hasResultSet = statement.execute();
        if (hasResultSet) {
            ResultSet rs = statement.getResultSet();
            int columnCount = rs.getMetaData().getColumnCount();

            List<String> columns = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = rs.getMetaData().getColumnLabel(i);
                columns.add(columnName);
            }
            List<JSONObject> list = new ArrayList<>();
            while (rs.next()) {
                JSONObject jo = new JSONObject();
                columns.stream().forEach(t -> {
                    try {
                        Object value = rs.getObject(t);
                        jo.put(t, value);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
                list.add(jo);
            }
            return list;
        } else {
            int updateCount = statement.getUpdateCount();
            return updateCount;
        }
    }*/


    public static Page<JSONObject> executeQueryPage(Connection connection,String sqlStr, Map<String, Object> data, int pageNumber, int limit) {
        logger.debug(sqlStr);
        Page pageVo = new Page();
//        ApiSql sql = this.sqlMap.get(namespace).get(sqlId);
//        if (!dataSourceMap.containsKey(sql.getDatasourceId())) {
//            throw new RuntimeException("datasource not found : " + sql.getDatasourceId());
//        }
        data.put("start",(pageNumber-1)*(limit));
        data.put("end",pageNumber*limit);
//        ApiDataSource dataSource = dataSourceMap.get(sql.getDatasourceId());
        String pageSql = sqlStr+ " LIMIT #{start}, #{end} ";
        logger.debug(pageSql);
        SqlMeta sqlMeta = engine.parse(pageSql, data);
        List<JSONObject> results = JdbcUtil.executeQuery(connection, sqlMeta.getSql(), sqlMeta.getJdbcParamValues());//executeQuery(namespace, sqlId, data);

        String totalSql = "  select count(1) as count FROM ( "+sqlStr+" ) AS subquery ";
        logger.debug(totalSql);
        SqlMeta sqlMeta2 = engine.parse(totalSql, data);

        Long totalpage = JdbcUtil.executeQueryInt(connection, sqlMeta2.getSql(), sqlMeta2.getJdbcParamValues());
        pageVo.setList(results);
        pageVo.setTotalRow(Math.toIntExact(totalpage));
        pageVo.setPageNumber(pageNumber);
        pageVo.setPageSize(limit);
        pageVo.setTotalPage(Math.toIntExact(totalpage) / limit);
        return pageVo;
    }

    //** 11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
    //** 11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
    //** 11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111

    /**
     * 1、查询返回List<JSONObject>
     * 2、新增、修改、删除返回int，成功条数。
     * 3、连接没有关闭，需要在调用方关闭
     * @param connection
     * @param sql
     * @return
     */
    public static Object executeSql(Connection connection, String sql, Map<String, Object> sqlParam,Boolean closeConn) throws SQLException {
        Object obj = executeSql(connection,sql,sqlParam);
        if(closeConn){
            connection.close();
        }
        return obj;
    }
    public static Object executeSql(Connection connection, String sql, Map<String, Object> sqlParam) throws SQLException {
        SqlMeta sqlMeta = JdbcUtil.getEngine().parse(sql, sqlParam);
        return JdbcUtil.executeSql(connection, sqlMeta.getSql(), sqlMeta.getJdbcParamValues());
    }
    public static Object executeSql(Connection connection, String sql, List<Object> jdbcParamValues)
            throws SQLException {
//		log.debug(JSON.toJSONString(jdbcParamValues));
        try {
            System.out.println("execute sql is "+sql);
            PreparedStatement statement = connection.prepareStatement(sql);
            // 参数注入
            for (int i = 1; i <= jdbcParamValues.size(); i++) {
                statement.setObject(i, jdbcParamValues.get(i - 1));
            }
            boolean hasResultSet = statement.execute();

            if (hasResultSet) {
                ResultSet rs = statement.getResultSet();
                int columnCount = rs.getMetaData().getColumnCount();

                List<String> columns = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = rs.getMetaData().getColumnLabel(i);
                    columns.add(columnName);
                }
                List<cn.hutool.json.JSONObject> list = new ArrayList<>();
                while (rs.next()) {
                    cn.hutool.json.JSONObject jo = new cn.hutool.json.JSONObject();
                    columns.stream().forEach(t -> {
                        try {
                            Object value = rs.getObject(t);
                            jo.put(t, value);
                            if(value==null) {
                                jo.put(t, "");
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    });
                    list.add(jo);
                }
                return list;
            } else {
                int updateCount = statement.getUpdateCount();
                System.out.println("["+updateCount + "] rows affected");
                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connection.close();
        }
    }

    public static int update(Connection connection, String sql, Map<String, Object> sqlParam) throws SQLException {
        SqlMeta sqlMeta = JdbcUtil.getEngine().parse(sql, sqlParam);
        return JdbcUtil.update(connection, sqlMeta.getSql(), sqlMeta.getJdbcParamValues());
    }
    public static int update(Connection connection, String sql, List<Object> jdbcParamValues) throws SQLException {
//		log.debug(JSON.toJSONString(jdbcParamValues));
        try {
            System.out.println("execute sql is "+sql);
            PreparedStatement statement = connection.prepareStatement(sql);
            // 参数注入
            for (int i = 1; i <= jdbcParamValues.size(); i++) {
                statement.setObject(i, jdbcParamValues.get(i - 1));
            }
            boolean hasResultSet = statement.execute();

            if (hasResultSet) {
                throw new RuntimeException("查询脚本调用query方法");
            } else {
                int updateCount = statement.getUpdateCount();
                System.out.println("["+updateCount + "] rows affected");
                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            connection.close();
        }
    }

    public static Long count(Connection connection, String sql, Map<String, Object> sqlParam) throws SQLException {
        SqlMeta sqlMeta = JdbcUtil.getEngine().parse(sql, sqlParam);
        List<Map<String,Object>> datas =  JdbcUtil.query(connection, sqlMeta.getSql(), sqlMeta.getJdbcParamValues());
        if(datas.size()==1){
            try {
                Map<String,Object> data = datas.get(0);
                for(String key: data.keySet()){
                    Object val = data.get(key);
                    return (Long) val;
                }
            } catch (Exception e) {
                throw new RuntimeException("count脚本执行返回有误");
            }
        }
        if(datas.size()>1){
            throw new RuntimeException("count脚本执行返回多条");
        }
        return 0L;
    }
    public static List<Map<String,Object>> query(Connection connection, String sql, Map<String, Object> sqlParam) throws SQLException {
        SqlMeta sqlMeta = JdbcUtil.getEngine().parse(sql, sqlParam);
        return JdbcUtil.query(connection, sqlMeta.getSql(), sqlMeta.getJdbcParamValues());
    }
    public static List<Map<String,Object>> query(Connection connection, String sql, List<Object> jdbcParamValues)
            throws SQLException {
//		log.debug(JSON.toJSONString(jdbcParamValues));
        System.out.println("execute sql is "+sql);
        PreparedStatement statement = connection.prepareStatement(sql);
        // 参数注入
        for (int i = 1; i <= jdbcParamValues.size(); i++) {
            statement.setObject(i, jdbcParamValues.get(i - 1));
        }
        boolean hasResultSet = statement.execute();

        if (hasResultSet) {
            ResultSet rs = statement.getResultSet();
            int columnCount = rs.getMetaData().getColumnCount();

            List<String> columns = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = rs.getMetaData().getColumnLabel(i);
                columns.add(columnName);
            }
            List<Map<String,Object>> list = new ArrayList<>();
            while (rs.next()) {
                //JSONObject jo = new JSONObject();
                Map<String,Object> jo = new HashMap<>();
                columns.stream().forEach(t -> {
                    try {
                        Object value = rs.getObject(t);
                        jo.put(t, value);
                        if(value==null) {
                            jo.put(t, null);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
                list.add(jo);
            }
            connection.close();
            return list;
        } else {
            connection.close();
            throw new RuntimeException("修改脚本调用update方法");
        }
    }

}