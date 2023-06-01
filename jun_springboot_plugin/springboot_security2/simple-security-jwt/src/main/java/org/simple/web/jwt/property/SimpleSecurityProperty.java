package org.simple.web.jwt.property;

import lombok.Data;
import org.simple.web.jwt.config.PasswordEncoderFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 项目名称：simple-security-all
 * 类名称：SimpleSecurityProperty
 * 类描述：SimpleSecurityProperty
 * 创建时间：2018/9/13
 *
 * @author guihuo   (E-mail:1620657419@qq.com)
 * @version v1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "simple.security")
public class SimpleSecurityProperty {

    private String passwordEncoder = PasswordEncoderFactory.PWD_ENCODER_MD5;

}
