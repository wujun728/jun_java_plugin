package org.coody.framework.core.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.log4j.Logger;
import org.coody.framework.core.logger.BaseLogger;

public class PrintException {

    /**
     * 获取Exception的堆栈新息。用于显示出错来源时使用。
     * 
     * @param e Exception对象
     * @param length 需要的信息长度，如果 <=0,表示全部信息
     * @return String 返回该Exception的堆栈新息
     * @author AIXIANG
     */
    public static String getErrorStack(Throwable e ) {
        String error = null;
        if (e != null) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(baos);
                e.printStackTrace(ps);
                error = baos.toString();
                baos.close();
                ps.close();
            } catch (Exception e1) {
                error = e.toString();
            }
        }
        return error;
    }
    
    public static void printException(BaseLogger logger,Throwable e){
    	String error=getErrorStack(e);
    	logger.info(error);
    	logger.error(error);
    }
    
    public static void printException(Logger logger,Throwable e){
    	String error=getErrorStack(e);
    	logger.info(error);
    	logger.error(error);
    }
}
