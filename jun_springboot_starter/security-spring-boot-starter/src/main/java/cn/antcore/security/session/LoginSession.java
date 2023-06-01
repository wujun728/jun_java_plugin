package cn.antcore.security.session;

import cn.antcore.security.entity.UserDetails;

/**
 * 用户登录Session
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/25</p>
 **/
public interface LoginSession {

    /**
     * 登录成功
     *
     * @param userDetails 用户数据
     * @return SessionId
     */
    String loginSuccess(UserDetails userDetails);

    /**
     * 退出登录
     */
    void logout();
}
