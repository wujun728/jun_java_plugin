package com.ivy.email.constant;

/**
 * Created by fangjie04 on 2016/12/1.
 */
public class EmailConstant {
    // 设置email超时时间
    public static final int MAIL_SMTP_TIMEOUT = 25000;

    // 邮件发送协议
    public static final String SMTP = "smtp";

    public static final String CLASSPATH = "classpath";

    public static final String CLASSPATH_RESOURCE_LOADER_CLASS = "classpath.resource.loader.class";


    // --------------- thread pool configuration --------------
    public static final int CORE_POOL_SIZE = 3;

    public static final int MAX_POOL_SIZE = 10;

    public static final int KEEP_ALIVE_SECONDS = 600;
}
