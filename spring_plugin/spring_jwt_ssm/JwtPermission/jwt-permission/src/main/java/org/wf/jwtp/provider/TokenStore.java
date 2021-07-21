package org.wf.jwtp.provider;

import java.util.List;

/**
 * 操作token的接口
 * Created by wangfan on 2018-12-28 上午 9:21.
 */
public interface TokenStore {

    /**
     * 获取生成token用的key
     *
     * @return 16进制的Key值
     */
    String getTokenKey();

    /**
     * 设置生成token用的key
     *
     * @param tokenKey 16进制的Key值
     */
    void setTokenKey(String tokenKey);

    /**
     * 创建新的token
     *
     * @param userId 用户id
     * @return Token
     */
    Token createNewToken(String userId);

    /**
     * 创建新的token
     *
     * @param userId 用户id
     * @param expire token过期时间,单位秒
     * @return Token
     */
    Token createNewToken(String userId, long expire);


    /**
     * 创建新的token
     *
     * @param userId   用户id
     * @param expire   token过期时间,单位秒
     * @param rtExpire refresh_token过期时间,单位秒
     * @return Token
     */
    Token createNewToken(String userId, long expire, long rtExpire);


    /**
     * 创建新的token
     *
     * @param userId      用户id
     * @param permissions 权限
     * @param roles       角色
     * @return Token
     */
    Token createNewToken(String userId, String[] permissions, String[] roles);

    /**
     * 创建新的token
     *
     * @param userId      用户id
     * @param permissions 权限
     * @param roles       角色
     * @param expire      token过期时间,单位秒
     * @return Token
     */
    Token createNewToken(String userId, String[] permissions, String[] roles, long expire);

    /**
     * 创建新的token
     *
     * @param userId      用户id
     * @param permissions 权限
     * @param roles       角色
     * @param expire      token过期时间,单位秒
     * @param rtExpire    refresh_token过期时间,单位秒
     * @return Token
     */
    Token createNewToken(String userId, String[] permissions, String[] roles, long expire, long rtExpire);

    /**
     * 刷新token
     *
     * @param refresh_token refresh_token
     * @return Token
     */
    Token refreshToken(String refresh_token);

    /**
     * 刷新token
     *
     * @param refresh_token refresh_token
     * @param expire        token过期时间,单位秒
     * @return Token
     */
    Token refreshToken(String refresh_token, long expire);

    /**
     * 刷新token
     *
     * @param refresh_token refresh_token
     * @param permissions   权限
     * @param roles         角色
     * @param expire        token过期时间,单位秒
     * @return Token
     */
    Token refreshToken(String refresh_token, String[] permissions, String[] roles, long expire);

    /**
     * 保存Token
     *
     * @param token Token
     * @return int
     */
    int storeToken(Token token);

    /**
     * 查询用户的某个token
     *
     * @param userId       用户id
     * @param access_token String
     * @return Token
     */
    Token findToken(String userId, String access_token);

    /**
     * 查询用户的全部token
     *
     * @param userId 用户id
     * @return List<Token>
     */
    List<Token> findTokensByUserId(String userId);

    /**
     * 查询用户的某个refresh_token
     *
     * @param userId        用户id
     * @param refresh_token String
     * @return int
     */
    Token findRefreshToken(String userId, String refresh_token);

    /**
     * 移除用户的某个token
     *
     * @param userId       用户id
     * @param access_token String
     * @return int
     */
    int removeToken(String userId, String access_token);

    /**
     * 移除用户的全部token
     *
     * @param userId 用户id
     * @return int
     */
    int removeTokensByUserId(String userId);

    /**
     * 修改某个用户的角色
     *
     * @param userId 用户id
     * @param roles  角色
     * @return int
     */
    int updateRolesByUserId(String userId, String[] roles);

    /**
     * 修改某个用户的权限
     *
     * @param userId      用户id
     * @param permissions 权限
     * @return int
     */
    int updatePermissionsByUserId(String userId, String[] permissions);

    /**
     * 查询用户的角色列表
     *
     * @param userId 用户id
     * @return 角色列表
     */
    String[] findRolesByUserId(String userId, Token token);

    /**
     * 查询用户的权限列表
     *
     * @param userId 用户id
     * @return 权限列表
     */
    String[] findPermissionsByUserId(String userId, Token token);

    /**
     * 设置单个用户最大token数量
     *
     * @param maxToken 最大token数
     */
    void setMaxToken(Integer maxToken);

    /**
     * 自定义查询角色sql
     *
     * @param findRolesSql 查询角色sql
     */
    void setFindRolesSql(String findRolesSql);

    /**
     * 自定义查询权限sql
     *
     * @param findPermissionsSql 查询权限的sql
     */
    void setFindPermissionsSql(String findPermissionsSql);

    /**
     * 获取单个用户最大token数量
     */
    Integer getMaxToken();

    /**
     * 获取自定义查询角色的sql
     */
    String getFindRolesSql();

    /**
     * 获取自定义查询权限的sql
     */
    String getFindPermissionsSql();

}
