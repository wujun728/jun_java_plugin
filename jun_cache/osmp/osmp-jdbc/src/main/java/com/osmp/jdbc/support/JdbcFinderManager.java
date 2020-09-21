/*   
 * Project: OSMP
 * FileName: JdbcFinderManager.java
 * version: V1.0
 */
package com.osmp.jdbc.support;

import java.util.Map;



/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年9月10日 上午11:18:52
 */

public interface JdbcFinderManager {

    JdbcTemplate findJdbcTemplate(String catalog);
    /**
     * 为ol web特制方法,根据数据库ip,name查询OL负载
     * @param ip
     * @param dbName
     * @param user
     * @param pass
     * @return
     */
    JdbcTemplate findJdbcTemplate(String ip, String dbName, String user, String pass);
    String getQueryStringByStatementKey(String statementName,Map<String, Object> parasMap);
    String getQueryStringByStatementKey(String statementName);
}
