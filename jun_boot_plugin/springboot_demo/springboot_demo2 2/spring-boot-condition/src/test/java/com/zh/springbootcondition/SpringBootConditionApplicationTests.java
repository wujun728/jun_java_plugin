package com.zh.springbootcondition;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringBootConditionApplicationTests {

    @Autowired(required = false)
    private CatBean catBean;

    @Autowired(required = false)
    private DogBean dogBean;

    @Autowired(required = false)
    private BirdBean birdBean;

    @Test
    public void conditionTest() {
        if (this.catBean != null){
            log.info("-----------------------容器里存在CatBean----------------------------");
            this.catBean.say();
        }else{
            log.info("-----------------------容器里不存在CatBean----------------------------");
        }
        if (this.dogBean != null){
            log.info("-----------------------容器里存在DogBean----------------------------");
            this.dogBean.say();
        }else{
            log.info("-----------------------容器里不存在DogBean----------------------------");
        }
        if (this.birdBean != null){
            log.info("-----------------------容器里存在BirdBean----------------------------");
            this.birdBean.say();
        }else{
            log.info("-----------------------容器里不存在BirdBean----------------------------");
        }
    }

}
