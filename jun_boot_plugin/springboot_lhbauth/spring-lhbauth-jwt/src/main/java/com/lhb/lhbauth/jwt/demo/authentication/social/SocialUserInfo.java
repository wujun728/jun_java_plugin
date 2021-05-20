package com.lhb.lhbauth.jwt.demo.authentication.social;

import lombok.Data;

/**
 * @author Wujun
 * @description
 * @date 2018/12/12 0012 16:45
 */
@Data
public class SocialUserInfo {

    private String providerId;

    private String providerUserId;

    private String nickname;

    private String headimg;

}
