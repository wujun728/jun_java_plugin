package com.jun.plugin.framework.datasource;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.druid.pool.DruidDataSource;
import com.jun.plugin.common.utils.spring.SpringUtils;

/**
 * 动态数据源工具
 * 
 * @author ruoyi
 */
public class DynamicDataSourceUtil 
{
    public static Map<Object, Object> dataSourceMap = new HashMap<>();//其他数据源
    
    public static void flushDataSource() {
        //获取spring管理的dynamicDataSource
        DynamicDataSource dynamicDataSource = (DynamicDataSource) SpringUtils.getBean("dynamicDataSource");
        //将数据源设置到 targetDataSources
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        //将 targetDataSources 中的连接信息放入 resolvedDataSources 管理
        dynamicDataSource.afterPropertiesSet();
    }
 
    /**
     * 删除数据源
     * @param key
     */
    public static void deleteTargetDataSource(Object key) {
        dataSourceMap.remove(key);
    }
    
    /**
     * 替换数据源
     * @param key
     * @param dataSource
     */
    public static void replaceTargetDataSource(Object key,DruidDataSource dataSource) {
        dataSourceMap.replace(key, dataSource);
    }
    
    /**
     * 添加数据源
     * @param key
     * @param dataSource
     */
    public static void addTargetDataSource(Object key, DruidDataSource dataSource) {
        dataSourceMap.put(key, dataSource);
    }
    
    /**
     * 设置数据源
     * @param targetDataSources
     */
    public static void setTargetDataSources(Map<Object, Object> targetDataSources) {
    	dataSourceMap = targetDataSources;
    }
}