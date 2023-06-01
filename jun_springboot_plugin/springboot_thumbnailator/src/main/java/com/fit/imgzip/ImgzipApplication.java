package com.fit.imgzip;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 图片上传公有插件启动入口
 */
@SpringBootApplication
public class ImgzipApplication {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        SpringApplication springApplication = new SpringApplication(ImgzipApplication.class);
        springApplication.setBannerMode(Banner.Mode.CONSOLE);
        springApplication.run(args);
    }
}
