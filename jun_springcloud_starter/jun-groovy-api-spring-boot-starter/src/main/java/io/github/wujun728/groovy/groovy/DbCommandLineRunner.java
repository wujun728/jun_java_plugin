package io.github.wujun728.groovy.groovy;//package io.github.wujun728.common.run;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.github.wujun728.db.record.Db;
import io.github.wujun728.db.record.DbPool;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import static io.github.wujun728.db.record.Db.main;

@Component
public class DbCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Custom CommandLineRunner :  MyCommandLineRunner.run");
        String enable = SpringUtil.getProperty("db.api.enable");
        enable = DbPool.TRUE.equals(enable)?DbPool.TRUE:DbPool.FALSE;
        if(Boolean.valueOf(enable)){
            init();
        }

    }

    void init(){
        String ds = SpringUtil.getProperty("db.main.ds");
        DataSource dataSource = SpringUtil.getBean(ds);
        if(ObjectUtil.isEmpty(dataSource)){
            dataSource = SpringUtil.getBean(DataSource.class);
        }
        if(ObjectUtil.isNotEmpty(dataSource)){
            Db.init(main,dataSource,true);
            GroovyDynamicLoader groovyDynamicLoader = SpringUtil.getBean(GroovyDynamicLoader.class);
            groovyDynamicLoader.initNew();
            System.out.println("Custom CommandLineRunner :  MyCommandLineRunner.run");
        }
    }

}
