package com.lf.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.commons.dbutils.QueryRunner;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * @ClassName: dbConnector
 * @Description:数据库连接
 * @Author: 李峰
 * @Date: 2021 年 01月 18 18:14
 * @Version 1.0
 */
public class dbConnector {
    private static QueryRunner queryRunner=new QueryRunner(getDateSource());
    public static DataSource getDateSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        InputStream resourceAsStream = dbConnector.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
            dataSource.setURL(properties.getProperty("jdbc.Url"));
            dataSource.setPassword(properties.getProperty("jdbc.Password"));
            dataSource.setUser(properties.getProperty("jdbc.UserName"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataSource;
    }
    public static QueryRunner getInstance(){
        return  queryRunner;
    }
}
