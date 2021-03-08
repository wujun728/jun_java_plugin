package com.zh.java8stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class StreamDistinctTest {

    /**
     * 去重，元素为引用类型的话需要重写equals和hashcode
     */
    @Test
    public void distinctTest() {
        List<String> strList = Arrays.asList("a","b","c","d",null,"a",null);
        List<String> strContainer = new ArrayList<>();
        strList.stream().distinct().forEach(e -> strContainer.add(e));
        log.info("==========================={}================================",strContainer.toString());
        List<User> userList = Arrays.asList(new User(1,"张三"),new User(2,"李四"),new User(1,"王五"));
        List<User> userContainer = new ArrayList<>();
        userList.stream().distinct().forEach(e -> userContainer.add(e));
        log.info("==========================={}================================",userContainer.toString());
    }


}
