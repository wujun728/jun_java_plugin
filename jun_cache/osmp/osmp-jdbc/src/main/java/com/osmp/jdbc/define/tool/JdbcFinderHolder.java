/*   
 * Project: OSMP
 * FileName: JdbcFinderHolder.java
 * version: V1.0
 */
package com.osmp.jdbc.define.tool;

import com.osmp.jdbc.support.JdbcFinderManager;

/**
 * 提供jdbcFinderManager
 * @author heyu
 *
 */
public class JdbcFinderHolder {
    private static JdbcFinderManager jdbcFinderManager;
    
    public static void set(JdbcFinderManager jdbcFinderManager){
        JdbcFinderHolder.jdbcFinderManager = jdbcFinderManager;
    }
    
    public static JdbcFinderManager get(){
        return JdbcFinderHolder.jdbcFinderManager;
    }
}
