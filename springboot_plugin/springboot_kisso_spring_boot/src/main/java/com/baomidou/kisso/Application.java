package com.baomidou.kisso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * kisso 演示<br>
 * https://github.com/baomidou/kisso
 * https://gitee.com/baomidou/kisso
 * </p>
 *
 * @author Wujun
 * @since 2017-08-08
 */
@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     * <p>
     * 1、启动执行<br>
     * 2、访问 http://localhost:8080/token 提示登录<br>
     * 3、登录 访问 http://localhost:8080/login 成功再去 2 步骤查看<br>
     * </p>
     * <p>
     * 退出登录：http://localhost:8080/logout
     * </p>
     */
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.run(args);
        logger.info("kisso start!");
    }

}
