package org.simple.web.jwt.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 项目名称：web-web-jwt
 * 类名称：JwtPayloadProperty
 * 类描述：JwtPayloadProperty jwt配置属性
 * 创建时间：2018/4/11 18:41
 *
 * @author guihuo   (E-mail:1620657419@qq.com)
 * @version v1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jwt.payload")
public class JwtPayloadProperty {

    /**
     * 密钥
     */
    private String secret = "simple";
    /**
     * jwt签发者名称
     */
    private String issuer = "simple-security-jwt";
    /**
     * 接收jwt的一方
     */
    private String audience = "foo";
    /**
     * 过期时间 ( 分钟 )
     * 默认是一天
     */
    private int expirationMinute = 24 * 60 * 60;
    /**
     * NotBefore ( 分钟 )
     * 默认是15分钟
     */
    private int notBeforeMinute = 15;

}
