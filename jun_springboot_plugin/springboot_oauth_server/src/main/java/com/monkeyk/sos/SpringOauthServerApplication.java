package com.monkeyk.sos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 2017-12-05
 *
 * @author Wujun
 */
@SpringBootApplication
public class SpringOauthServerApplication {

    /**
     * 不能直接运行 main
     * 详细 请参考 others/how_to_use.txt 文件
     *
     * @param args args
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringOauthServerApplication.class, args);
    }
}
