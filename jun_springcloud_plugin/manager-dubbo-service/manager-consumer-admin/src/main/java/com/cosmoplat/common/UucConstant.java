package com.cosmoplat.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 用户中心 常量
 *
 * @author 01514109
 */
@Data
@Component
public class UucConstant {

    private static final String ACCESS_TOKEN = "access_token";

    @Value("${uuc.host}")
    private String host;
    @Value("${uuc.client.id}")
    private String clientId;
    @Value("${uuc.client.secret}")
    private String clientSecret;
    @Value("${uuc.bind.defaultRoleId}")
    private String defaultRoleId;
    private String uucLogin = "/service/oauth/authorize";
    //获取用户级 access_token
    private String uucToken = "/service/oauth/token";
    //用户信息的获取与修改
    private String uucUserInfo = "/service/api/v1/userinfo";
    //注销授权登录
    private String uucLogout = "/service/uuc/v1/logout";
    //idm 工号或姓名查用户
    private String uucIdmEmployee = "/service/idm/v1/listuser/employee";
    //uuc 8位授权码换用户级access_token
    private String uucTokenByAuthCode = "/service/user/v1/get-token";
    //idm code 换idm access_token
    private String uucTokenByIdmAuthCode = "/service/idm/v1/access_token";
    //idm access_token 换内部工号用户信息
    private String uucUserInfoByIdmToken = "/service/idm/v2/userinfo";
    //发起方单点登录到被跳入方
    private String uucSsoJump = "/service/sso/v1/uuc/jump/uuc";
    //根据手机号、邮箱、用户名获取用户id
    private String uucGetUserid = "/service/api/v1/userid";

    //根据工号登陆
    private String uucIdmLogin = "/service/idm/v2/login";
    //根据工号登录后，获取返回的tokenId： 根据 tokenId 换内部工号用户信息
    private String uucUserInfoByIdmTokenId = "/service/idm/v1/userinfo";
}
