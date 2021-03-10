package com.demo.weixin.constants;

/**
 * 微信接口 url常量
 * @author Wujun
 */
public class WeixinUrlConstants {

    /**
     * 公众号、服务号
     */

    // 微信公众号获取用户信息的授权url
    public static final String WX_AUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
    // 公众号网页授权，用于获取用户信息的token url
    public static final String WX_USER_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";
    // 获取用户信息
    public static final String WX_USER_INFO = "https://api.weixin.qq.com/sns/userinfo";
    // 获取公众号accessToken
    public static final String WX_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";
    //
    public static final String WX_DOWNLOAD_PHOTO = "http://file.api.weixin.qq.com/cgi-bin/media/get";
    //
    public static final String WX_JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

    public static final String WX_CREATE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create";

    // 微信客户端中授权地址
    public static final String WX_M_AUTH_LOCATION = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";
    public static final String WX_PC_AUTH_LOCATION = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";


    /**
     * 企业号 *
     */
    // 获取企业号accessTOken
    public static final String QY_ACCESS_TOKEN = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
    //创建部门
    public static final String QY_CREATE_DEPT = "https://qyapi.weixin.qq.com/cgi-bin/department/create";
    //获取部门列表
    public static final String QY_GET_DEPT = "https://qyapi.weixin.qq.com/cgi-bin/department/list";


    // 创建应用菜单
    public static final String QY_CREATE_MENU = "https://qyapi.weixin.qq.com/cgi-bin/menu/create";
    // 获取应用菜单
    public static final String QY_GET_MENU = "https://qyapi.weixin.qq.com/cgi-bin/menu/get";
    // 删除应用菜单
    public static final String QY_DEL_MENU = "https://qyapi.weixin.qq.com/cgi-bin/menu/delete";
    // 发送消息
    public static final String QY_SEND_MESSAGE = "https://qyapi.weixin.qq.com/cgi-bin/message/send";

    // 获取成员信息 ？？？
    public static final String QY_USER_INFO = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";

    // 创建成员
    public static final String QY_CREATE_USER = "https://qyapi.weixin.qq.com/cgi-bin/user/create";
    // 读取成员
    public static final String QY_GET_USER = "https://qyapi.weixin.qq.com/cgi-bin/user/get";
    // 更新成员
    public static final String QY_UPDATE_USER = "https://qyapi.weixin.qq.com/cgi-bin/user/update";
    // 删除成员
    public static final String QY_DELETE_USER = "https://qyapi.weixin.qq.com/cgi-bin/user/delete";
    // 批量删除成员
    public static final String QY_BATCH_DELETE_USER = "https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete";
    // 获取部门成员列表
    public static final String QY_DEPT_USER_LIST = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist";
    // 获取部门成员详情列表
    public static final String QY_DEPT_USER_DETAIL_LIST = "https://qyapi.weixin.qq.com/cgi-bin/user/list";

    // userid转换成openid
    public static final String QY_CONVERT_TO_OPENID = "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid";
    // openid 转换成userid
    public static final String QY_CONVERT_TO_USERID = " https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_userid";

    // 企业微信登录授权地址
    public static final String QY_PC_AUTH_LOCATION = "https://open.work.weixin.qq.com/wwopen/sso/qrConnect";


    // 获取企业号登录用户信息
    public static final String QY_LOGIN_USER_INFO = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";
    // 获取企业号登陆用户的详细信息
    public static final String QY_LOGIN_USER_DETAIL = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserdetail";

    //企业号jssdk ticket地址
    public static final String QY_JSAPI_TICKET = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket";
}
