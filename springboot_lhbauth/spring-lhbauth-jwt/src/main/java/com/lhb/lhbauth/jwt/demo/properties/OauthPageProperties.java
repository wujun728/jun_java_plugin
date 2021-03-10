package com.lhb.lhbauth.jwt.demo.properties;

import lombok.Data;

/**
 * @author Wujun
 * @description
 * @date 2018/12/26 0026 18:27
 */
@Data
public class OauthPageProperties {

    private  String  oauthLogin = "/oauthLogin";

    private  String oauthGrant = "/oauthGrant";
}
