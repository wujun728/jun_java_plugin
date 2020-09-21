/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.persistence.jdbc;

import net.ymate.platform.core.support.IPasswordProcessor;

/**
 * 数据源配置接口
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-29 下午3:06:52
 * @version 1.0
 */
public class DataSourceCfgMeta {

    /**
     * 当前数据源名称
     */
    private String name;

    /**
     * 数据库表前缀名称，默认为空
     */
    private String tablePrefix;

    /**
     * 数据源适配器，可选值为已知适配器名称或自定义适配置类名称，默认为default
     * <br>目前支持已知适配器名称：default|dbcp|c3p0|jndi
     */
    private Class<? extends IDataSourceAdapter> adapterClass;

    /**
     * 数据库类型，可选参数，默认值将通过连接字符串分析获得
     * <br>目前支持：mysql|oracle|sqlserver|db2|sqlite|postgresql|hsqldb|h2
     */
    private JDBC.DATABASE type;

    /**
     * 数据库方言，可选参数，自定义方言将覆盖默认配置
     */
    private String dialectClass;

    /**
     * 数据库连接驱动，可选参数，框架默认将根据数据库类型进行自动匹配
     */
    private String driverClass;

    /**
     * 数据库连接字符串，必填参数
     */
    private String connectionUrl;

    /**
     * 数据库访问用户名称，必填参数
     */
    private String username;

    /**
     * 数据库访问密码，可选参数
     */
    private String password;

    /**
     * 数据库访问密码是否已加密，默认为false
     */
    private boolean isPasswordEncrypted;

    /**
     * 是否显示执行的SQL语句，默认为false
     */
    private boolean isShowSQL;

    /**
     * 是否开启堆栈跟踪，默认为false
     */
    private boolean isStackTraces;

    /**
     * 堆栈跟踪层级深度，默认为0(即全部)
     */
    private int stackTraceDepth;

    /**
     * 堆栈跟踪包名前缀过滤，默认为空
     */
    private String stackTracePackage;

    /**
     * 数据库密码处理器，可选参数，用于对已加密码数据库访问密码进行解密，默认为空
     */
    private Class<? extends IPasswordProcessor> passwordClass;

    public DataSourceCfgMeta() {
    }

    public DataSourceCfgMeta(String name,
                             Class<? extends IDataSourceAdapter> adapterClass,
                             String connectionUrl,
                             String username,
                             String password,
                             boolean isPasswordEncrypted) {
        this.name = name;
        this.adapterClass = adapterClass;
        this.connectionUrl = connectionUrl;
        this.username = username;
        this.password = password;
        this.isPasswordEncrypted = isPasswordEncrypted;
    }

    public DataSourceCfgMeta(String name,
                             String tablePrefix,
                             Class<? extends IDataSourceAdapter> adapterClass,
                             JDBC.DATABASE type,
                             String connectionUrl,
                             String username,
                             String password,
                             boolean isPasswordEncrypted,
                             Class<? extends IPasswordProcessor> passwordClass,
                             boolean isShowSQL) {
        this.name = name;
        this.tablePrefix = tablePrefix;
        this.adapterClass = adapterClass;
        this.type = type;
        this.connectionUrl = connectionUrl;
        this.username = username;
        this.password = password;
        this.isPasswordEncrypted = isPasswordEncrypted;
        this.passwordClass = passwordClass;
        this.isShowSQL = isShowSQL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShowSQL() {
        return isShowSQL;
    }

    public void setIsShowSQL(boolean isShowSQL) {
        this.isShowSQL = isShowSQL;
    }

    public boolean isStackTraces() {
        return isStackTraces;
    }

    public void setIsStackTraces(boolean isStackTraces) {
        this.isStackTraces = isStackTraces;
    }

    public int getStackTraceDepth() {
        return stackTraceDepth;
    }

    public void setStackTraceDepth(int stackTraceDepth) {
        this.stackTraceDepth = stackTraceDepth;
    }

    public String getStackTracePackage() {
        return stackTracePackage;
    }

    public void setStackTracePackage(String stackTracePackage) {
        this.stackTracePackage = stackTracePackage;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public Class<? extends IDataSourceAdapter> getAdapterClass() {
        return adapterClass;
    }

    public void setAdapterClass(Class<? extends IDataSourceAdapter> adapterClass) {
        this.adapterClass = adapterClass;
    }

    public JDBC.DATABASE getType() {
        return type;
    }

    public void setType(JDBC.DATABASE type) {
        this.type = type;
    }

    public String getDialectClass() {
        return dialectClass;
    }

    public void setDialectClass(String dialectClass) {
        this.dialectClass = dialectClass;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPasswordEncrypted() {
        return isPasswordEncrypted;
    }

    public void setIsPasswordEncrypted(boolean isPasswordEncrypted) {
        this.isPasswordEncrypted = isPasswordEncrypted;
    }

    public Class<? extends IPasswordProcessor> getPasswordClass() {
        return passwordClass;
    }

    public void setPasswordClass(Class<? extends IPasswordProcessor> passwordClass) {
        this.passwordClass = passwordClass;
    }
}
