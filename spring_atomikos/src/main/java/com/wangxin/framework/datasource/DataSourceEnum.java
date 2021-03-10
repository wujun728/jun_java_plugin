package com.wangxin.framework.datasource;

/**
 * 
 * 数据源枚举常量类
 */
public enum DataSourceEnum {

                            MASTER("master", "主数据库"), // 主数据库
                            SLAVE("slave", "从数据库");// 从数据库

    private DataSourceEnum(String key, String description) {
        this.key = key;
        this.description = description;
    }

    /** 数据源对应的key(用于在Spring配置文件中指定数据源Map中的key使用) */
    private String key;

    /** 说明 */
    private String description;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
