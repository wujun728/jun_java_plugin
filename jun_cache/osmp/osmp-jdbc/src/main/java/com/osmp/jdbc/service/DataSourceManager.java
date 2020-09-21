/*   
 * Project: OSMP
 * FileName: DataSourceManager.java
 * version: V1.0
 */
package com.osmp.jdbc.service;

import java.util.Map;

import javax.sql.DataSource;

import com.osmp.jdbc.define.DBType;

/**
 * Description:数据源服务添加监听
 * @author: wangkaiping
 * @date: 2016年8月9日 上午10:14:28上午10:51:30
 */
public class DataSourceManager {
    
    private JdbcTemplateManager templateManager;
    
    public void setTemplateManager(JdbcTemplateManager templateManager) {
        this.templateManager = templateManager;
    }
    
    //移除数据源
    public void removedDs(String catalog){
        if(catalog == null) return;
        templateManager.removeDataSource(catalog);
    }
    
    //添加数据源
    public void addDs(DataSource ds , String catalog , DBType dbType){
        if(ds != null && catalog != null){
            templateManager.addDataSource(ds,catalog,dbType);
        }
    }

}
