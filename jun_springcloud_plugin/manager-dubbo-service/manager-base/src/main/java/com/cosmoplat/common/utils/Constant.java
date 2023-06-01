package com.cosmoplat.common.utils;

/**
 * Constant
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public class Constant {

    private Constant() {
    }

    public static final String PERMISSIONS_KEY = "permissions-key";
    public static final String USERID_KEY = "userid-key";
    public static final String USERNAME_KEY = "username-key";
    public static final String ROLES_KEY = "roles-key";
    public static final String DEPT_TYPE = "D";

    //超级管理员角色
    public static final String SUPER_ROLE_ID = "1";
    public static final String DICT_ID_DEFAULT = "100001001";

    //启用
    public static final Integer UNABLE_FLAG_Y = 1;
    //禁用
    public static final Integer UNABLE_FLAG_N = 0;
    //未删除状态
    public static final Integer DATA_NOT_DELETED = 1;
    //删除状态
    public static final Integer DATA_DELETED = 0;
    //用户正常状态
    public static final Integer USER_STATUS_1 = 1;
    //用户禁用状态
    public static final Integer USER_STATUS_2 = 2;

    public static final Integer USER_CREATE_WHERE_WEB = 1;
    public static final Integer USER_CREATE_WHERE_UUC = 2;


    /**
     * 正常token
     */
    public static final String ACCESS_TOKEN = "authorization";
    /**
     * UUC的应用级access_token
     */
    public static final String UUC_APP_ACCESS_TOKEN = "access_token";
    /**
     * UUC的用户级access_token
     */
    public static final String UUC_USER_ACCESS_TOKEN = "access_token";

    /**
     * UUC获取用户信息接口返回的token的key
     */
    public static final String UUC_USER_INFO_TOKEN_ID = "tokenId";


}
