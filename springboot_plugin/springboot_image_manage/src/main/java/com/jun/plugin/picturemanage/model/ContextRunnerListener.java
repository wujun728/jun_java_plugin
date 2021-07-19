package com.jun.plugin.picturemanage.model;
/*
package cn.yustart.picturemanage.model;

import cn.yustart.picturemanage.conf.Constant;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;

*/
/**
 * @Author YuChen
 * @Email 835033913@qq.com
 * @Create 2019/11/7 14:55
 *//*

@Component
@Order(2)
@Log4j2
public class ContextRunnerListener implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        String path;
        if (args != null && args.length > 0 && args[0].startsWith("root")) {
            path = args[0].split("=")[1].trim();
        } else {
            log.error("程序没有初始化Path 采用默认的配置 \n --Info {}", args);
            path = "/home";
        }
        log.info("初始化Path为:{}", path);
        Constant.ROOT_DIR = new File(path).getAbsolutePath();
    }
}
*/
