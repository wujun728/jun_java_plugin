package com.xia.utils;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private DataSource dataSource = null;

    private ConnectionFactory() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File("D:\\ideawork\\test_log4j2\\src\\main\\resources\\db.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataSource = new DruidDataSource();
        ((DruidDataSource) dataSource).configFromPropety(properties);
    }

    public static Connection getDatabaseConnection() throws IOException, SQLException {
        return Singleton.INSTANCE.dataSource.getConnection();
    }

    private static interface Singleton {
        final ConnectionFactory INSTANCE = new ConnectionFactory();
    }


}