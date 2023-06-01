package org.simple.web.security.jwt.demo;

import org.simple.web.jwt.annotation.EnableWebSecurityJwt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目名称：web-security-jwt
 * 类名称：DemoApplication
 * 类描述：DemoApplication
 * 创建时间：2018/9/12
 *
 * @author guihuo   (E-mail:1620657419@qq.com)
 * @version v1.0
 */
@EnableWebSecurityJwt
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

