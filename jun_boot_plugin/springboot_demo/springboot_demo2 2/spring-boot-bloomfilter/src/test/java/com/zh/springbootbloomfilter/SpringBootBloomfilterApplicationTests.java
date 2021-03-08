package com.zh.springbootbloomfilter;

import cn.hutool.core.util.RandomUtil;
import com.zh.springbootbloomfilter.service.TestService;
import com.zh.springbootbloomfilter.service.impl.TestServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootBloomfilterApplicationTests {

    @Autowired
    private TestService testService;

    @Test
    public void checkExist() {
        int num = RandomUtil.randomInt(TestServiceImpl.size);
        boolean exist = this.testService.checkExist(num);
        if (exist){
            log.info("{}号老铁在名单中!",num);
        }else{
            log.info("{}号老铁不在名单中!",num);
        }
    }

    @Test
    public void printAccidentHit() {
        this.testService.printAccidentHit();
    }
}
