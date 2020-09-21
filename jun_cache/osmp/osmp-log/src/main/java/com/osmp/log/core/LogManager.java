/*   
 * Project: OSMP
 * FileName: LogManager.java
 * version: V1.0
 */
package com.osmp.log.core;

import java.util.Map;

import org.osgi.service.log.LogReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogManager {
    private Logger logger = LoggerFactory.getLogger(LogManager.class);
    
    private LogReaderService logReader;
    private OsmpLogListener logListener;
    
    private boolean isLogStart = false;
    

    public void setLogListener(OsmpLogListener logListener) {
        this.logListener = logListener;
        startLog();
    }

    public void bind(LogReaderService logReader,Map<String,String> props){
        this.logReader = logReader;
        startLog();
    }
    
    public void unbind(LogReaderService logReader,Map<String,String> props){
        this.logReader = null;
        isLogStart = false;
    }
    
    private void startLog(){
        if(isLogStart) return;
        if(logListener != null && logReader != null){
            logReader.removeLogListener(logListener);
            logReader.addLogListener(logListener);
            isLogStart = true;
            logger.info("日志记录开始...");
        }
    }
}
