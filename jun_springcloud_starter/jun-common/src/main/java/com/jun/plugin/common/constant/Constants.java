package com.jun.plugin.common.constant;

/**
 * 通用常量信息
 * 
 * @author ruoyi
 */
public class Constants
{
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 自动去除表前缀
     */
    public static final String AUTO_REOMVE_PRE = "true";

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";
    
    /** 字典类型是否唯一的返回结果码 */
    public final static String DICT_TYPE_UNIQUE = "0";
    public final static String DICT_TYPE_NOT_UNIQUE = "1";
    
    /** 字典正常状态 */
    public static final String DICT_NORMAL = "0";
    
    /** 数据库类型-MySQL */
    public static final String DATABASE_TYPE_MYSQL = "mysql";
    
    /** 数据库类型-Oracle */
    public static final String DATABASE_TYPE_ORACLE = "oracle";
    
    /** 数据库类型-PostgreSQL */
    public static final String DATABASE_TYPE_POSTGRESQL = "pgsql";
    
    /** 数据库类型-DB2 */
    public static final String DATABASE_TYPE_DB2 = "db2";
    
    /** 数据库类型-SQL Server */
    public static final String DATABASE_TYPE_SQLSERVER = "sqlserver";
    
    /** Oracle数据库连接类型-SID */
    public static final String ORACLE_CONN_TYPE_SID = "sid";
    
    /** Oracle数据库连接类型-服务名 */
    public static final String ORACLE_CONN_TYPE_SERVICE_NAME = "service_name";
    
}
