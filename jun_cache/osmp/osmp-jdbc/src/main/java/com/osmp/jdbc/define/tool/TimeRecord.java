/*   
 * Project: OSMP
 * FileName: TimeRecord.java
 * version: V1.0
 */
package com.osmp.jdbc.define.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 耗时记录
 * @author heyu
 *
 */
public class TimeRecord {
    private final static Logger logger = LoggerFactory.getLogger(TimeRecord.class);
    private static ThreadLocal<INFO> timeLocal = new ThreadLocal<INFO>();
    
    public final static void start(String desc){
        timeLocal.remove();
        timeLocal.set(new INFO(desc, System.currentTimeMillis()));
    }
    
    public final static long over(){
        long nowMills = System.currentTimeMillis();
        INFO info = timeLocal.get();
        if(info == null) {
            timeLocal.remove();
            return -1;
        }
        Long startMills = info.getMills();
        long cost = nowMills - startMills;
        logger.warn(info.getDesc() + " 时间消耗:"+cost+"ms");
        timeLocal.remove();
        return cost;
    }
    
}
class INFO {
    private String desc;
    private long mills;
    
    public String getDesc() {
        return desc;
    }
    public long getMills() {
        return mills;
    }
    public INFO(String desc, long mills) {
        super();
        this.desc = desc;
        this.mills = mills;
    }
    
}
