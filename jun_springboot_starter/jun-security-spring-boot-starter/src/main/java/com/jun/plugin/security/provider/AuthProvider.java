package com.jun.plugin.security.provider;


import javax.servlet.http.HttpServletRequest;

/**
 * 操作token和会话的接口
 *
 * @version 2023-01-06-9:33
 **/
public interface AuthProvider {

    /*============================操作token开始=============================*/

    /**
     * 获取token
     * @return
     */
    String getToken();

    /**
     * 获取token
     * @param request HttpServletRequest
     * @return
     */
    String getToken(HttpServletRequest request);

    /**
     * 刷新token
     * @param token
     * @return
     */
    boolean refreshToken(String token);

    /**
     * 检查token是否失效
     * @param token
     * @return
     */
    boolean checkToken(String token);

    /**
     * 创建一个新的token
     * @param loginId 会话登录：参数填写要登录的账号id，建议的数据类型：long | int | String， 不可以传入复杂类型，如：User、Admin 等等
     * @return
     */
    String createToken(Object loginId);

    /**
     * 根据token，获取loginId
     * @param token
     * @return
     */
    Object getLoginId(String token);

    /**
     * 删除token
     * @param token
     * @return
     */
    boolean deleteToken(String token);

    /**
     * 通过loginId删除token---常用于主动让某人下线
     * @param loginId
     * @return
     */
    boolean deleteTokenByLoginId(Object loginId);

    /*============================操作token结束=============================*/


    /*============================操作会话开始，此部分在AbstractAuthProvider里予以实现=============================*/

    /**
     * 执行登录操作
     * @param loginId 会话登录：参数填写要登录的账号id，建议的数据类型：long | int | String， 不可以传入复杂类型，如：User、Admin 等等
     */
    String login(Object loginId);

    /**
     * 退出登录
     */
    void logout();

    /**
     * HttpServletRequest request
     * @param request
     */
    void logout(HttpServletRequest request);

    /**
     * 获取当前登录用户的loginId
     * @return
     */
    Object getLoginId();

    /**
     * 校验当前会话是否登录
     * @return true已登录，false未登录
     */
    boolean isLogin();
    /*============================操作会话结束=============================*/
}
