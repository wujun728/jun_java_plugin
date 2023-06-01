package cn.antcore.security.session;

import cn.antcore.security.entity.UserDetails;

import java.io.Serializable;

/**
 * 用户Session
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/25</p>
 **/
public interface UserSession {

    /**
     * 是否登录
     *
     * @return true or false
     */
    boolean isLogin();

    /**
     * 获取用Id
     *
     * @return 用户ID
     */
    Serializable getUserId();

    /**
     * 获取登录用户名
     *
     * @return 用户名
     */
    String getUsername();

    /**
     * 获取用户登录数据
     *
     * @return 用户数据
     */
    UserDetails getUserDetails();
}
