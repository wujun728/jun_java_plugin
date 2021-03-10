package com.lhb.lhbauth.jwt.demo.properties;

import lombok.Data;

/**
 * Created by ksm on 2018/4/27.
 */
@Data
public class ClientProperties {
    /** 授权客户端ID */
    private String clientId;
    /** 授权客户端密钥 */
    private String clientSecret;
}
