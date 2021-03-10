package com.mycompany.myproject.service;

import com.mycompany.myproject.jooq.Tables;
import com.mycompany.myproject.jooq.tables.pojos.Welcome;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("jooqService")
public class JooqServiceImpl implements  JooqService {

    @Autowired
    private DSLContext dslContext ;


    @Cacheable("jooqWelcomeList")
    public List<Welcome> findWelcomeAll() {

        List<Welcome> welcomeList = new ArrayList<>();

        dslContext.selectFrom(Tables.WELCOME).stream().forEach(record -> {
            Welcome welcome = new Welcome();
            welcome.setId(record.getId());
            welcome.setWelcomeMsg(record.getWelcomeMsg());
            welcome.setCreateBy(record.getCreateBy());
            welcome.setCreateDate(record.getCreateDate());
            welcome.setUpdateBy(record.getUpdateBy());
            welcome.setUpdateDate(record.getUpdateDate());
            welcomeList.add(welcome);
        });

        return welcomeList;
    }

}
