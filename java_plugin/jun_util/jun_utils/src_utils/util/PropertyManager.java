/**
 * Program  : PropertyManager.java
 * Author   : huangfei
 * Create   : 2009-6-25 上午09:27:24
 *
 * Copyright 2006 by ipanel Technologies Ltd.,
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of ipanel Technologies Ltd.("Confidential Information").  
 * You shall not disclose such Confidential Information and shall 
 * use it only in accordance with the terms of the license agreement 
 * you entered into with ipanel Technologies Ltd.
 *
 */

package cn.ipanel.apps.portalBackOffice.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件
 * @author tianwl
 * @datetime 2010-9-1 上午11:45:32
 */
public class PropertyManager {
	
	//Maintain one instance of a TagPropertyManager object
    private static PropertyManager manager = null;
    // synchronize uses this to lock on
    private static Object managerLock = new Object();
    
    private static String propsconfig = "config.properties";
    
    private static Properties config = null;
    
    PropertyManager(){
    	if(config == null)
        	config = loadProps(propsconfig);
    }
    
    /**
     * 获取配置信息（存放在Config.properties中）
     * @author huangfei
     * @create 2009-6-25 上午09:42:50
     * @since 
     * @param name
     * @return
     */
    public final static String getConfigProperty(String name) {
    	if (manager == null) {
            synchronized(managerLock) {
                if (manager == null) {
                    manager = new PropertyManager();
                }
            }
        }
        String value = config.getProperty(name);
        return value==null?"not find "+name+" in config.properties":value;
    }
    
    private Properties loadProps(String name) {
        Properties props = new Properties();
        InputStream in = null;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
			in = classLoader.getResource(name)
					.openStream();
            props.load(in);
        }
        catch (IOException ioe) {
            System.err.println("Error reading Portal properties file " +
		name + " " + ioe);
            ioe.printStackTrace();
        }
        finally {
            try {
            	  if( in != null )
            	  in.close();
            } catch (IOException e) { }
        }
        return props;
    }
    
    
    public final static Properties getConfig() {
        //leigq 修改
    	if (manager == null) {
            synchronized(managerLock) {
                if (manager == null) {
                    manager = new PropertyManager();
                }
            }
        }
		return config;
	}

	public static void setConfig(Properties config) {
		PropertyManager.config = config;
	}

	public static void main(String[] args){
    	System.out.println(PropertyManager.getConfigProperty("zipDir"));
    }
}

