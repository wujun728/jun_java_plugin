/*   
 * Project: OSMP
 * FileName: SqlStatementManager.java
 * version: V1.0
 */
package com.osmp.jdbc.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.osmp.jdbc.parse.SqlStatement;

/**
 * Description: SQL解析保存
 * 
 * @author: wangkaiping
 * @date: 2014年9月10日 上午11:32:37
 */

public class SqlStatementManager {
	private Map<String, SqlStatement> statementMap = new HashMap<String, SqlStatement>();
	private Map<String, Set<String>> fileSqlNameMap = new HashMap<String, Set<String>>();

	public void putSql(String fileName,Map<String, SqlStatement> sqlMap){
	    if(sqlMap != null){
	        statementMap.putAll(sqlMap);
	        fileSqlNameMap.put(fileName, sqlMap.keySet());
	    }
	}
	
	
	public void removeSqls(String fileName){
	    if(fileName != null){
	        Set<String> sqlNames = fileSqlNameMap.get(fileName);
	        if(sqlNames == null) return;
	        for(String name : sqlNames) {
	            statementMap.remove(name);
	        }
	    }
	}

	public String getStatementByName(String statementName,Map<String, Object> parasMap) {
		if (statementMap.containsKey(statementName)) {
		    SqlStatement statement = statementMap.get(statementName);
		    if(statement != null){
		        return statement.generateSql(parasMap);
		    }
		}
		return null;
	}
	
	public String getStatementByName(String statementName) {
        if (statementMap.containsKey(statementName)) {
            SqlStatement statement = statementMap.get(statementName);
            if(statement != null){
                return statement.generateSql(null);
            }
        }
        return null;
    }
}
