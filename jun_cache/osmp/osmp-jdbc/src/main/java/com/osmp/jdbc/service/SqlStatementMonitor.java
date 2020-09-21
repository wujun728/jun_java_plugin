/*   
 * Project: OSMP
 * FileName: SqlStatementMonitor.java
 * version: V1.0
 */
package com.osmp.jdbc.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.osmp.jdbc.parse.SqlParserUtils;
import com.osmp.tools.commons.ExecutorWrapper;

/**
 * 文件目录监控(要求JDK1.7及以上版本)
 * @author heyu
 *
 */
public class SqlStatementMonitor implements InitializingBean,DisposableBean{
    private Logger logger = LoggerFactory.getLogger(SqlStatementMonitor.class);
    
    //sql xml文件目录
    private String sqlDir;
    
    private SqlStatementManager sqlStatementManager;
    //任务执行线程池
    private ExecutorWrapper executor;
    
    //sql文件目录监控服务
    private WatchService watchService;
    private final Map<WatchKey, Path> directories = new HashMap<WatchKey, Path>();
    private boolean running = true;
    
    public void setSqlDir(String sqlDir){
        this.sqlDir = sqlDir;
    }
    public void setSqlStatementManager(SqlStatementManager sqlStatementManager){
        this.sqlStatementManager = sqlStatementManager;
    }
    public void setExecutor(ExecutorWrapper executor) {
        this.executor = executor;
    }
    
    //对目录注册监听
    private void registerPath(Path path) throws IOException { 
        //注册监听目录 
        WatchKey key = path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,  
                  StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE); 
        directories.put(key, path); 
    } 
    
    //对目录树注册监听
    private void registerTree(final Path start) { 
        try{
            Files.walkFileTree(start, new SimpleFileVisitor<Path>() { 
                @Override 
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) 
                        throws IOException { 
                    logger.info("Registering:" + dir); 
                    updateSqlFile(start.toFile(),false);
                    registerPath(dir); 
                    return FileVisitResult.CONTINUE; 
                }
            }); 
        }catch(IOException e){
            logger.error("目录:"+start.toAbsolutePath()+"注册监听失败",e);
        }
    } 
    
    @Override
    public void destroy() throws Exception {
        logger.error("sql文件监控服务关闭");
        running = false;
        if(watchService != null){
            watchService.close();
        }
    }
    @Override
    public void afterPropertiesSet() throws Exception{
        Assert.notNull(sqlDir, "sql文件目录不能为空...");
        Assert.notNull(sqlStatementManager, "sqlStatementManager未初始化...");
        Assert.notNull(executor, "executor未初始化...");
        final File resFile = new File(sqlDir);
        Assert.isTrue(resFile.exists(), "sql文件目录不存在...");
        new Thread(){
            public void run(){
                try {
                    monitor(resFile.toPath());
                } catch (Exception e) {
                    logger.error("监控sql文件目录出错",e);
                }
            }
        }.start();
        
        logger.info("开始监控sql文件目录"+sqlDir);
    }
    
    private void monitor(Path path) throws Exception{
        watchService = FileSystems.getDefault().newWatchService();
        
        registerTree(path);
        while(running){
            WatchKey watchKey = watchService.take();
            List<WatchEvent<?>> events = watchKey.pollEvents();
            
             
            if(events != null){
                for(WatchEvent<?> e : events){
                    //事件处理
                    final WatchEvent<Path> watchEventPath = (WatchEvent<Path>)e; 
                    final Path filename = watchEventPath.context(); 
                    
                    final Path directory_path = directories.get(watchKey); 
                    final Path child = directory_path.resolve(filename); 
                    boolean isChildDirectory = Files.isDirectory(child, LinkOption.NOFOLLOW_LINKS);
                    
                    if(e.kind() == StandardWatchEventKinds.ENTRY_CREATE){
                        if(isChildDirectory){
                            registerTree(child); 
                        }else{
                            updateSqlFile(child.toFile(),false);
                        }
                        logger.info("新增文件:"+child.toAbsolutePath());
                    }else if(e.kind() == StandardWatchEventKinds.ENTRY_MODIFY){
                        boolean isExsist = child.toFile().exists(); 
                        if(isExsist && !isChildDirectory){
                            updateSqlFile(child.toFile(),false);
                            logger.info("更新sql文件:"+child.toAbsolutePath());
                        }
                    }else if(e.kind() == StandardWatchEventKinds.ENTRY_DELETE){
                        updateSqlFile(child.toFile(),true);
                        logger.info("删除文件:"+child.toAbsolutePath());
                    }
                }
            }
            if(!watchKey.reset()){
                Path pth = directories.remove(watchKey);
                watchKey.cancel();
                logger.error("移除sql文件目录"+pth.toString()+"监控服务");
                if(directories.isEmpty()){
                    break;
                }
            }
        }
        running = false;
        watchService.close();
    }
    
    //更新sql文件
    private void updateSqlFile(File file,boolean isDelete){
        if(file == null) return;
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File fileItem : files){
                updateSqlFile(fileItem,isDelete);
            }
        }else{
            executor.execute(new SqlUpdateTask(file,isDelete));
        }
    }
    
    
    //sql文件更新任务
    private class SqlUpdateTask implements Runnable {
        private File file;
        private boolean isDelete;
        public SqlUpdateTask(File file,boolean isDelete){
            this.file = file;
            this.isDelete = isDelete;
        }
        public void run() {
            if(file == null) return;
            String filename = file.getAbsolutePath();
            if(!filename.endsWith(".xml")){
                return;
            }
            sqlStatementManager.removeSqls(filename);
            if(isDelete) return;
            sqlStatementManager.putSql(filename, SqlParserUtils.parse(file));
        }
    }
}
