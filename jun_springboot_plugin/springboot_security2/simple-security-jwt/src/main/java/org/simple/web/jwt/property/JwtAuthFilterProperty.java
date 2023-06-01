package org.simple.web.jwt.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 项目名称：moguding-components
 * 类名称：JwtAuthFilterProperty
 * 类描述：JwtAuthFilterProperty
 * 创建时间：2018/4/23 9:46
 *
 * @author guihuo   (E-mail:1620657419@qq.com)
 * @version v1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jwt.filter")
public class JwtAuthFilterProperty {

    /**
     * 请求header内的key
     */
    private String header = "Authorization";
    /**
     * 请求header内的key对应value 的默认开头
     */
    private String tokenHead = "Bearer ";
    /**
     * 过滤路径
     * 如 登录接口不需要走校验过滤器
     */
    private String exceptUrl = "";


}
