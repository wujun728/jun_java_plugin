package com.lhb.lhbauth.jwt.demo.authentication.social.qq;

import lombok.Data;
import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @author Wujun
 * @description 自定义QQ的服务提供商ID
 * @date 2019/1/3 0003 10:58
 */
@Data
public class QQProperties extends SocialProperties {

    private String providerId = "qq";
}
