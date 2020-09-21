/*   
 * Project: OSMP
 * FileName: JdbcTemplateManager.java
 * version: V1.0
 */
package com.osmp.jdbc.service;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.osmp.jdbc.define.DBType;
import com.osmp.jdbc.support.JdbcTemplate;
import com.osmp.jdbc.support.template.BaseTemplate;
import com.osmp.jdbc.support.template.MSSQLTemplate;
import com.osmp.jdbc.support.template.MysqlTemplate;
import com.osmp.jdbc.support.template.OracleTemplate;

/**
 * 管理数据源
 * @author heyu
 *
 */
public class JdbcTemplateManager implements InitializingBean{
    private Map<String,JdbcTemplate> templates = new HashMap<String,JdbcTemplate>();
    
    @Override
    public void afterPropertiesSet() throws Exception {
    }
    //添加数据源信息
    public void addDataSource(DataSource ds,String catalog,DBType dbType){
        if(ds == null || catalog == null || dbType == null) return;
        BaseTemplate jdbcTemplate = null;
        
        if(dbType.equals(DBType.SQLSERVER)){
            jdbcTemplate = new MSSQLTemplate();
        }else if(dbType.equals(DBType.MYSQL)){
            jdbcTemplate = new MysqlTemplate();
        }else{
            jdbcTemplate = new OracleTemplate();
        }
        jdbcTemplate.setOwnJdbcTemplate(new NamedParameterJdbcTemplate(ds));
        jdbcTemplate.setTransactionManager(new DataSourceTransactionManager(ds));
        //dsTransactionManager.setGlobalRollbackOnParticipationFailure(false); //指定主事务决定回滚
        
        templates.put(catalog.toUpperCase(), jdbcTemplate);
        
    }
    
    //删除数据源信息
    public void removeDataSource(String catalog){
        if(catalog == null) return;
        templates.remove(catalog.toUpperCase());
    }
    
    public JdbcTemplate getJdbcTemplate(String catalog){
        if(catalog == null) return null;
        return templates.get(catalog.toUpperCase());
    }
    
}
