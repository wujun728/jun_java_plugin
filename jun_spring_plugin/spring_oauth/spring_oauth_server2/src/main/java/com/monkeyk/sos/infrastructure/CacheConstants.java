package com.monkeyk.sos.infrastructure;

/**
 * 2016/7/22
 * <p/>
 * 定义系统中使用的CACHE的常量
 * 名称与 ehcache.xml 中对应
 * <p/>
 *
 * @author Shengzhao Li
 */
public interface CacheConstants {

    /**
     * client Details Cache, key is clientId
     */
    String CLIENT_DETAILS_CACHE = "clientDetailsCache";

    /**
     * access Token Cache, key is token
     */
    String ACCESS_TOKEN_CACHE = "accessTokenCache";

    /**
     * refresh Token Cache, key is token
     */
    String REFRESH_TOKEN_CACHE = "refreshTokenCache";

    /**
     * authorization Code Cache, key is code
     */
    String AUTHORIZATION_CODE_CACHE = "authorizationCodeCache";

    /**
     * user Cache, key is username
     */
    String USER_CACHE = "userCache";


}
