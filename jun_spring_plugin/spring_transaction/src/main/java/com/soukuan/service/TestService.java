package com.soukuan.service;

import com.soukuan.domain.Test;
import com.soukuan.test01.Test1Mapper;
import com.soukuan.test02.Test2Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Random;

@Service  
public class TestService {

    @Resource
    private Test1Mapper test1Mapper;
      
    @Resource
    private Test2Mapper test2Mapper;
      
    @Transactional  
     public void test(Test test){
        Random random = new Random();
        int index = random.nextInt(10);
        test.setName("test1"+index);
        test1Mapper.insert(test);
        test.setName("test2"+index);
        test2Mapper.insert(test);
        int x = 100/0;
     }
}  