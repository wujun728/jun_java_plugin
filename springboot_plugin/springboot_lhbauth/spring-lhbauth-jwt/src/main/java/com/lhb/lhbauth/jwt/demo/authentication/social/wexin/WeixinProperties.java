package com.lhb.lhbauth.jwt.demo.authentication.social.wexin;

import lombok.Data;
import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @author Wujun
 * @description 自定义微信的服务提供商ID
 * @date 2019/1/4 0004 9:47
 */
@Data
public class WeixinProperties extends SocialProperties {

    private String providerId = "weixin";

}
