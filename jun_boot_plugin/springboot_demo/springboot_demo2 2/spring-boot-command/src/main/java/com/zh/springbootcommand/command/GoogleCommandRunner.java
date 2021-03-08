package com.zh.springbootcommand.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 项目启动浏览器自动访问配置
 * @author Wujun
 * @date 2019/5/31
 */
@Slf4j
@Component
public class GoogleCommandRunner implements CommandLineRunner {
    @Value("${spring.web.indexUrl}")
    private String indexUrl;

    @Value("${spring.web.googleexcute}")
    private String googleExcutePath;

    @Value("${spring.auto.openurl}")
    private boolean isOpen;

    @Override
    public void run(String... args){
        if(isOpen){
            String cmd = googleExcutePath +" "+ indexUrl;
            Runtime run = Runtime.getRuntime();
            try{
                run.exec(cmd);
                log.info("================启动浏览器打开项目成功===============");
            }catch (Exception e){
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
    }
}
