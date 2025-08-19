package io.github.wujun728.sql;//package io.github.wujun728.common.run;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.github.wujun728.db.record.Db;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DbCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Custom CommandLineRunner :  MyCommandLineRunner.run");
        init();

    }

    void init(){
        DataSource dataSource = SpringUtil.getBean(DataSource.class);
        if(ObjectUtil.isNotEmpty(dataSource)){
            Db.init("main",dataSource);
        }
    }

}
