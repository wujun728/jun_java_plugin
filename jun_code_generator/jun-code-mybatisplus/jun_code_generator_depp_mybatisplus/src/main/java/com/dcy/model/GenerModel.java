package com.dcy.model;

import lombok.Data;

/**
 * @author dcy
 */
@Data
public class GenerModel {

    /**
     * 打开的路径
     */
    private String pack;
    /**
     * db的url
     */
    private String dbUrl;
    /**
     * 驱动
     */
    private String driverName;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 模块名（英文）
     */
    private String modules;
    /**
     * 表前缀
     */
    private String prefix;
}
