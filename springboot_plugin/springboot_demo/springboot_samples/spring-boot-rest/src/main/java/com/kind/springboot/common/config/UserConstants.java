package com.kind.springboot.common.config;

/**
 * Function: 用户常量. <br/>
 * Date:     2016年8月11日 上午11:31:57 <br/>
 *
 * @author Wujun
 * @see
 * @since JDK 1.7
 */
public class UserConstants {

    /**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    /**
     * token有效期（小时）
     */
    public static final int TOKEN_EXPIRES_HOUR = 72;

    /**
     * 存放Authorization的header字段
     */
    public static final String AUTHORIZATION = "authorization";

}
