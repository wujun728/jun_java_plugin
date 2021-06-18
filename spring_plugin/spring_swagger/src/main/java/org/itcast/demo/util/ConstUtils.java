package org.itcast.demo.util;

/**
 * @description
 * @auther: CDHong
 * @date: 2019/6/24-9:48
 **/
public class ConstUtils {
    /** 默认日期时间格式 */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /** 默认日期格式 */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /** 默认时间格式 */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    /** 当前登陆的用户*/
    public static final String CURRENT_LOGIN_USER = "loginUser";
    /** 用户登录状态*/
    public interface UserLoginStatus{
        int DISABLED=1;
        int ENABLED=0;
    }

}
