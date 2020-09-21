/*   
 * Project: OSMP
 * FileName: DataSourceMonitor.java
 * version: V1.0
 */
package com.osmp.jdbc.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.alibaba.druid.pool.DruidDataSource;
import com.osmp.jdbc.define.DBType;

/**
 * 数据源监控
 * @author heyu
 *
 */
public class DataSourceMonitor implements InitializingBean,DisposableBean {
    private Logger logger = LoggerFactory.getLogger(DataSourceMonitor.class);
    private final String osmp_DATASOURCE_NAME = "osmp.jdbc.name";
    private final String osmp_DATASOURCE_DRIVERCLASSNAME = "osmp.jdbc.driverClassName";
    private final String osmp_DATASOURCE_URL = "osmp.jdbc.url";
    private final String osmp_DATASOURCE_USERNAME = "osmp.jdbc.username";
    private final String osmp_DATASOURCE_PASSWORD = "osmp.jdbc.password";
    private final String osmp_DATASOURCE_INITIALSIZE = "osmp.jdbc.initialSize";
    private final String osmp_DATASOURCE_MAXACTIVE = "osmp.jdbc.maxActive";
    private final String osmp_DATASOURCE_MINIDLE = "osmp.jdbc.minIdle";
    private final String osmp_DATASOURCE_MAXWAIT = "osmp.jdbc.maxWait";
    private final String osmp_DATASOURCE_VALIDATIONQUERY = "osmp.jdbc.validationQuery";
    private final String osmp_DATASOURCE_TIMEBETWEENEVICTIONRUNSMILLIS = "osmp.jdbc.timeBetweenEvictionRunsMillis";
    private final String osmp_DATASOURCE_MINEVICTABLEIDLETIMEMILLIS = "osmp.jdbc.minEvictableIdleTimeMillis";
    private final String osmp_DATASOURCE_REMOVEABANDONEDTIMEOUT = "osmp.jdbc.removeAbandonedTimeout";
    
    //datasource文件目录
    private String dataSourceDir;
    
    private DataSourceManager dataSourceManager;
    //文件名称对应数据源名称
    private Map<String, String> filepath2datasourceNameMap = new HashMap<String, String>();
    //数据源信息
    private Map<String, DruidDataSource> dataSources = new HashMap<String,DruidDataSource>();
    //datasource文件目录监控服务
    private WatchService watchService;
    private boolean running = true;
    
    public void setDataSourceDir(String dataSourceDir) {
        this.dataSourceDir = dataSourceDir;
    }
    public void setDataSourceManager(DataSourceManager dataSourceManager) {
        this.dataSourceManager = dataSourceManager;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(dataSourceDir, "dataSource文件目录不能为空...");
        Assert.notNull(dataSourceManager, "dataSourceManager未初始化...");
        final File resFile = new File(dataSourceDir);
        Assert.isTrue(resFile.exists(), "dataSource文件目录不存在...");
        filepath2datasourceNameMap.clear();
        for(File file : resFile.listFiles()){
            addDataSource(file.getName());
        }
        new Thread(){
            public void run(){
                try {
                    monitor(resFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    
    private void monitor(File file) throws Exception{
        watchService = FileSystems.getDefault().newWatchService();
        file.toPath().register(watchService, StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_DELETE); 
        while(running){
            WatchKey watchKey = watchService.take();
            List<WatchEvent<?>> events = watchKey.pollEvents();
            
            if(events != null){
                for(WatchEvent<?> e : events){
                    //事件处理
                    final WatchEvent<Path> watchEventPath = (WatchEvent<Path>)e; 
                    final Path filename = watchEventPath.context(); 
                    
                    if(e.kind() == StandardWatchEventKinds.ENTRY_CREATE){
                        addDataSource(filename.toString());
                    }else if(e.kind() == StandardWatchEventKinds.ENTRY_DELETE){
                        removeDataSource(filename.toString());
                    }
                }
            }
            if(!watchKey.reset()){
                watchKey.cancel();
                logger.error("移除dataSource文件目录监控服务");
                break;
            }
        }
        running = false;
        watchService.close();
    }
    
    private void addDataSource(String filename){
        logger.info("添加数据库配置文件:"+filename);
        if(filename == null || "".equals(filename)) return;
        String path = dataSourceDir + File.separator + filename;
        File file = new File(path);
        if(!file.exists()){
            logger.info("数据库配置文件:"+filename+" not found");
            return;
        }
        Properties props = new Properties();
        try {
            logger.info("开始加载数据库配置文件:"+filename);
            props.load(new FileInputStream(file));
            String name = props.getProperty(osmp_DATASOURCE_NAME);
            String driverClassName = props.getProperty(osmp_DATASOURCE_DRIVERCLASSNAME);
            String url = props.getProperty(osmp_DATASOURCE_URL);
            String username = props.getProperty(osmp_DATASOURCE_USERNAME);
            String pwd = props.getProperty(osmp_DATASOURCE_PASSWORD);
            int initialsize = String2Int(props.getProperty(osmp_DATASOURCE_INITIALSIZE),5);
            int maxactive = String2Int(props.getProperty(osmp_DATASOURCE_MAXACTIVE),10);
            int minidle = String2Int(props.getProperty(osmp_DATASOURCE_MINIDLE),5);
            int maxwait = String2Int(props.getProperty(osmp_DATASOURCE_MAXWAIT),3000);
            String validationquery = props.getProperty(osmp_DATASOURCE_VALIDATIONQUERY);
            int timebetweenevictionrunsmillis = String2Int(props.getProperty(osmp_DATASOURCE_TIMEBETWEENEVICTIONRUNSMILLIS),10000);
            int minevictableidletimemillis = String2Int(props.getProperty(osmp_DATASOURCE_MINEVICTABLEIDLETIMEMILLIS),30000);
            int removeabandonedtimeout = String2Int(props.getProperty(osmp_DATASOURCE_REMOVEABANDONEDTIMEOUT),30000);
            DruidDataSource ds = new DruidDataSource();
            ds.setName(name);
            ds.setDriverClassName(driverClassName);
            ds.setUrl(url);
            ds.setUsername(username);
            ds.setPassword(pwd);
            ds.setInitialSize(initialsize);
            ds.setMaxActive(maxactive);
            ds.setMinIdle(minidle);
            ds.setMaxWait(maxwait);
            ds.setValidationQuery(validationquery);
            ds.setTimeBetweenEvictionRunsMillis(timebetweenevictionrunsmillis);
            ds.setMinEvictableIdleTimeMillis(minevictableidletimemillis);
            ds.setRemoveAbandonedTimeoutMillis(removeabandonedtimeout);
            ds.setFilters("stat");
            ds.init();
            dataSourceManager.addDs(ds, name,DBType.getDBType(url));
            dataSources.put(name, ds);
            filepath2datasourceNameMap.put(filename, name);
            logger.info("添加数据库:"+name);
        } catch (IOException | SQLException e) {
            logger.info("添加数据库出错",e);
        }
    }
    
    private void removeDataSource(String filename){
        if(filename == null) return;
        String name = filepath2datasourceNameMap.get(filename);
        dataSourceManager.removedDs(name);
        filepath2datasourceNameMap.remove(filename);
        DruidDataSource ds = dataSources.get(name);
        if(ds != null){
            ds.close();
        }
        dataSources.remove(name);
        logger.info("移除数据库:"+name);
    }
    
    private int String2Int(String str,int defaultInt){
        if(str == null || "".equals(str)) return defaultInt;
        try{
            return Integer.valueOf(str);
        }catch(NumberFormatException e){
        }
        return defaultInt;
    }
    
    @Override
    public void destroy() throws Exception {
        filepath2datasourceNameMap.clear();
        for(DruidDataSource ds : dataSources.values()){
            if(ds != null){
                ds.close();
            }
        }
    }
    
}
