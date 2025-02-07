package io.github.wujun728.util;

import cn.hutool.log.StaticLog;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONObject;
import io.github.wujun728.sql.entity.ApiDataSource;
import io.github.wujun728.sql.utils.PoolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class JdbcUtil {

    private static Logger logger = LoggerFactory.getLogger(JdbcUtil.class);

    public static List<JSONObject> executeQuery(ApiDataSource datasource, String sql, List<Object> jdbcParamValues) {
        logger.debug(sql);
        DruidPooledConnection connection = null;
        try {

            connection = PoolManager.getPooledConnection(datasource);
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
    public static JSONObject executeQueryById(ApiDataSource datasource, String sql, List<Object> jdbcParamValues) {
        logger.debug(sql);
        DruidPooledConnection connection = null;
        try {

            connection = PoolManager.getPooledConnection(datasource);
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
        logger.debug(sql);
        DruidPooledConnection connection = null;
        try {
            connection = PoolManager.getPooledConnection(datasource);
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

    public static int executeUpdate(ApiDataSource datasource, String sql, List<Object> jdbcParamValues) {
        logger.debug(sql);
        DruidPooledConnection connection = null;
        try {
            connection = PoolManager.getPooledConnection(datasource);
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

    public static Object execute(ApiDataSource datasource, String sql, List<Object> jdbcParamValues) {
        logger.debug(sql);
        DruidPooledConnection connection = null;
        try {
            connection = PoolManager.getPooledConnection(datasource);
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


    public static Object execute(Connection connection, String sql, List<Object> jdbcParamValues) throws SQLException {
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


    }
}