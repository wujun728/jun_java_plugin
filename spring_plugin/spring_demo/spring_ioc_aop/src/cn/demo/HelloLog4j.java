package cn.demo;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class HelloLog4j {
	
	private static Logger logger = Logger.getLogger(HelloLog4j.class); 
	
    public static void main(String[] args) { 
    	PropertyConfigurator.configure("src/log4j.properties");
        logger.debug("This is debug message.");  
        // 记录info级别的信息  
        logger.info("This is info message.");  
        // 记录error级别的信息  
        logger.error("This is error message.");  
    }  
}
