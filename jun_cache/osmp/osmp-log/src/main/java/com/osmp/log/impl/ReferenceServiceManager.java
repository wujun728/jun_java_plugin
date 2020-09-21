/*   
 * Project: OSMP
 * FileName: ReferenceServiceManager.java
 * version: V1.0
 */
package com.osmp.log.impl;

import java.util.Map;

import com.osmp.jdbc.support.JdbcFinderManager;

public class ReferenceServiceManager {
    private static JdbcFinderManager jdbcFinederManager;

    public static JdbcFinderManager getJdbcFinederManager() {
        return jdbcFinederManager;
    }
    
    public void bindJdbcFinederManager(JdbcFinderManager jdbcFinederManager,Map<Object, Object> props) {
        ReferenceServiceManager.jdbcFinederManager = jdbcFinederManager;
    }
    
    public void unbindJdbcFinederManager(JdbcFinderManager jdbcFinederManager,Map<Object, Object> props) {
        ReferenceServiceManager.jdbcFinederManager = null;
    }
    
}
