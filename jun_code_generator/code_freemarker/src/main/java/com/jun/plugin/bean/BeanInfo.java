package com.jun.plugin.bean;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Bean表对象
 * @author Wujun
 */
public class BeanInfo implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * bean
     */
    private String beanName;

    /**
     *
     */
    private String entityName;

    /**
     *
     */
    private String entityObj;

    /**
     *
     */
    private String entityPackage;

    /**
     *
     */
    private String entityDir;


    /**
     *
     */
    private String entityFileName;

    /**
     *
     */
    private String entityFilePath;

    /**
     *
     */
    private String daoPackage;


    /**
     *
     */
    private String servicePackage;


    /**
     *
     */
    private String serviceImplPackage;

    /**
     *
     */
    private String controllerPackage;


    /**
     *
     */
    private String serviceTestPackage;

    /**
     *
     */
    private Config conf;

    /**
     *
     */
    private TableInfo tableInfo;

    public BeanInfo(Config conf, TableInfo tableInfo) {
        this.conf = conf;
        this.tableInfo = tableInfo;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getEntityPackage() {
        if (StringUtils.isNoneBlank(getPrefix())) {
            return entityPackage + Constants.PACKAGE_SEPARATOR + getPrefix();
        }
        return entityPackage;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public String getDaoPackage() {
        if (StringUtils.isNoneBlank(getPrefix())) {
            return daoPackage + Constants.PACKAGE_SEPARATOR + getPrefix();
        }
        return daoPackage;
    }

    public void setDaoPackage(String daoPackage) {
        this.daoPackage = daoPackage;
    }

    public String getServicePackage() {
        if (StringUtils.isNoneBlank(getPrefix())) {
            return servicePackage + Constants.PACKAGE_SEPARATOR + getPrefix();
        }
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getControllerPackage() {
        if (StringUtils.isNoneBlank(getPrefix())) {
            return controllerPackage + Constants.PACKAGE_SEPARATOR + getPrefix();
        }
        return controllerPackage;
    }

    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    public String getServiceImplPackage() {
        if (StringUtils.isNoneBlank(getPrefix())) {
            return serviceImplPackage + Constants.PACKAGE_SEPARATOR + getPrefix();
        }
        return serviceImplPackage;
    }

    public void setServiceImplPackage(String serviceImplPackage) {
        this.serviceImplPackage = serviceImplPackage;
    }

    public String getServiceTestPackage() {
        if (StringUtils.isNoneBlank(getPrefix())) {
            return serviceTestPackage + Constants.PACKAGE_SEPARATOR + getPrefix();
        }
        return serviceTestPackage;
    }

    public void setServiceTestPackage(String serviceTestPackage) {
        this.serviceTestPackage = serviceTestPackage;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityObj() {
        return entityObj;
    }

    public void setEntityObj(String entityObj) {
        this.entityObj = entityObj;
    }

    public String getEntityDir() {
        return entityDir;
    }

    public void setEntityDir(String entityDir) {
        this.entityDir = entityDir;
    }

    public String getEntityFileName() {
        return entityFileName;
    }

    public void setEntityFileName(String entityFileName) {
        this.entityFileName = entityFileName;
    }

    public String getEntityFilePath() {
        return entityFilePath;
    }

    public void setEntityFilePath(String entityFilePath) {
        this.entityFilePath = entityFilePath;
    }
}
