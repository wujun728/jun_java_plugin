package com.jun.plugin.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.alibaba.druid.pool.DruidDataSource;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;


@Slf4j
public class DataSourcePool {

    public static final String main = "main";
    private static Lock lock = new ReentrantLock();
    private static Lock deleteLock = new ReentrantLock();

    public static final String mysqlDriver5 = "com.mysql.jdbc.Driver";
    public static final String mysqlDriver6 = "com.mysql.cj.jdbc.Driver";
    public static final String postgresqlDriver6 = "org.postgresql.Driver";
    public static final String oracleDriver6 = "oracle.jdbc.driver.OracleDriver";
    public static final String sqlserverDriver6 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    //所有数据源的连接池存在map里
    static ConcurrentHashMap<String, DataSource> map = new ConcurrentHashMap<>();

    public static DataSource init(String dsname,String url,String username,String password,String driver) {
        if (map.containsKey(dsname)) {
            return map.get(dsname);
        } else {
            lock.lock();
            try {
                log.info(Thread.currentThread().getName() + "获取锁");
                if (!map.containsKey(dsname)) {
                    DruidDataSource druidDataSource = new DruidDataSource();
                    druidDataSource.setName(dsname);
                    druidDataSource.setUrl(url);
                    druidDataSource.setUsername(username);
                    druidDataSource.setPassword(password);
                    druidDataSource.setDriverClassName(driver);
                    druidDataSource.setConnectionErrorRetryAttempts(3);       //失败后重连次数
                    druidDataSource.setBreakAfterAcquireFailure(true);
                    map.put(dsname, druidDataSource);
                    log.info("创建Druid连接池成功：{}", dsname);

                }
                return map.get(dsname);
            } catch (Exception e) {
                return null;
            } finally {
                lock.unlock();
            }
        }
    }
    public static void add(String dsname,DataSource dataSource) {
            lock.lock();
            try {
                log.info(Thread.currentThread().getName() + "获取锁");
                map.put(dsname, dataSource);
                log.info("添加连接池成功：{}", dsname);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
    }
    public static DataSource get(String dsname) {
        if (map.containsKey(dsname)) {
            return map.get(dsname);
        } else {
            return null;
        }
    }

    //删除数据库连接池
    public static void remove(String dsname) {
        deleteLock.lock();
        try {
            DataSource druidDataSource = map.get(dsname);
            if (druidDataSource != null) {
                //druidDataSource.close();
                if(druidDataSource instanceof  DruidDataSource){
                    ((DruidDataSource)druidDataSource).close();
                }
                map.remove(dsname);
            }
        } catch (Exception e) {
            log.error(e.toString());
        } finally {
            deleteLock.unlock();
        }
    }

    public static Connection getConnection(String dsname) throws SQLException {
        return DataSourcePool.get(dsname).getConnection();
    }


}
