package com.jun.plugin.common.run;

import com.jun.plugin.common.base.interfaces.IPluginRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Slf4j
@Order(value = 100)
@Component
public class MyCommandLineRunner implements IPluginRunner {
    @Override
    public void run() throws Exception {
        System.out.println("Spring Boot应用程序启动时初始化执行的代码111");
        // 在这里可以做一些初始化的工作或其他需要在启动时执行的任务
    }


}
