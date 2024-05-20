package com.jun.plugin.groovy.util;

import com.alibaba.fastjson2.JSONObject;
import com.jun.plugin.common.Result;
import com.jun.plugin.db.DataSourcePool;
import com.jun.plugin.groovy.common.model.ApiDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JdbcUtil {

    public static Connection getConnection(ApiDataSource ds) throws Exception {
        try {
            Class.forName(ds.getDriver());
            String password = ds.getPassword();
            // String password = ds.isEdit_password() ? ds.getPassword() :
            // DESUtils.decrypt(ds.getPassword());
            Connection connection = DriverManager.getConnection(ds.getUrl(), ds.getUsername(), password);
            log.info("successfully connected");
            return connection;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    "Please check whether the jdbc driver jar is missing, if missed copy the jdbc jar file to lib dir. "
                            + e.getMessage());
        }
    }


    public static Connection getConnectionByDBType(ApiDataSource ds) throws SQLException, ClassNotFoundException {
        String url = ds.getUrl();
        switch (ds.getType()) {
            case "mysql":
                Class.forName("com.mysql.jdbc.Driver");
                break;
            case "postgresql":
                Class.forName("org.postgresql.Driver");
                break;
            case "hive":
                Class.forName("org.apache.hive.jdbc.HiveDriver");
                break;
            case "sqlserver":
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                break;
            default:
                break;
        }

        Connection connection = DriverManager.getConnection(url, ds.getUsername(), ds.getPassword());
        log.info("获取连接成功");
        return connection;
    }

    public static Result executeSql(int isSelect, ApiDataSource ds, String sql, List<Object> jdbcParamValues) {
        log.info("sql:\n" + sql);
        Connection connection = null;
        try {

            connection = DataSourcePool.init(ds.getName(),ds.getUrl(),ds.getUsername(),ds.getPassword(),ds.getDriver()).getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            //参数注入
            for (int i = 1; i <= jdbcParamValues.size(); i++) {
                statement.setObject(i, jdbcParamValues.get(i - 1));
            }

            if (isSelect == 1) {
                ResultSet rs = statement.executeQuery();

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
                return Result.success(list);
            } else {
                int rs = statement.executeUpdate();
                return Result.success("sql修改数据行数： " + rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
