package com.lhb.lhbauth.jwt.demo.properties;


import com.lhb.lhbauth.jwt.demo.authentication.mobile.ValidateCodeProperties;
import com.lhb.lhbauth.jwt.demo.authentication.social.SocialProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Wujun
 * @description
 * @date 2018/11/22 0022 11:54
 */
@ConfigurationProperties(prefix = "system")
@Data
public class SecurityProperties {


    private OauthPageProperties oauthLogin = new OauthPageProperties();

    private ValidateCodeProperties code = new ValidateCodeProperties();

    private SocialProperties social = new SocialProperties();

}
