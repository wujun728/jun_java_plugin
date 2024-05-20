package com.jun.plugin.common.constant;

/**
 * Constant
 *
 * @author wujun
 * @version V1.0
 * @date 2020年3月18日
 */
public class Constant {

    /**
     * redis的token相关
     */
    public static final String ACCESS_TOKEN = "authorization";
    public static final String PERMISSIONS_KEY = "permissions-key";
    public static final String USERID_KEY = "userid-key";
    public static final String USERNAME_KEY = "username-key";
    public static final String ROLES_KEY = "roles-key";
	    public static final String USERPHONE_KEY = "userphone-key";
    public static final String USERREALNAME_KEY = "userrealname-key";
    public static final String USERDEPTID_KEY = "userdeptid-key";
    public static final String USERDEPTNO_KEY = "userdeptno-key";
    public static final String USERDEPTNAME_KEY = "userdeptname-key";
    public static final String USER_KEY = "user-key";

    /**
     * 未删除值
     */
    public static final Integer DATA_NOT_DELETED = 1;

    /**
     * 数据库类型
     */
    public static final String DB_TYPE_MYSQL = "mysql";
    public static final String DB_TYPE_ORACLE = "oracle";
    public static final String DB_TYPE_SQL_SERVER = "sqlServer";

    /**
     * 定时任务状态
     */
    public static final Integer SCHEDULER_STATUS_NORMAL = 0;
    public static final Integer SCHEDULER_STATUS_PAUSE = 1;

    /**
     * 数据范围类型 1:所有/2:自定义/3:本部门及一下/4:仅本部门/5:自己
     */
    public static final Integer DATA_SCOPE_ALL = 1;
    public static final Integer DATA_SCOPE_CUSTOM = 2;
    public static final Integer DATA_SCOPE_DEPT_AND_CHILD = 3;
    public static final Integer DATA_SCOPE_DEPT = 4;
    public static final Integer DATA_SCOPE_DEPT_SELF = 5;


    //***********************************************************************************************


    /**
     * redis-OK
     */
    public static final String OK = "OK";

    /**
     * redis过期时间，以秒为单位，一分钟
     */
    public static final int EXRP_MINUTE = 60;

    /**
     * redis过期时间，以秒为单位，一小时
     */
    public static final int EXRP_HOUR = 60 * 60;

    /**
     * redis过期时间，以秒为单位，一天
     */
    public static final int EXRP_DAY = 60 * 60 * 24;

    /**
     * redis-key-前缀-shiro:cache:
     */
    public static final String PREFIX_SHIRO_CACHE = "shiro:cache:";

    /**
     * redis-key-前缀-shiro:access_token:
     */
    public static final String PREFIX_SHIRO_ACCESS_TOKEN = "shiro:access_token:";

    /**
     * redis-key-前缀-shiro:refresh_token:
     */
    public static final String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";

    /**
     * JWT-account:
     */
    public static final String ACCOUNT = "authorization";
    public static final String CURRENT_TIME_MILLIS = "currentTimeMillis";
    public static final Integer PASSWORD_MAX_LEN = 8;
}
