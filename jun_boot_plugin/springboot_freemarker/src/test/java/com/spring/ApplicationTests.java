package com.spring;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.springboot.Application;
import cn.springboot.model.simple.News;
import cn.springboot.service.simple.NewsService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(ApplicationTests.class);

    @Autowired
    private NewsService newsService;

    @Test
    public void addNews() {
        log.info("# 生产测试数据");
        News news = null;
        for (int i = 1; i < 1001; i++) {
            news = new News();
            news.setTitle("db_2_" + i);
            news.setDescription("db_2_" + i);
            news.setAddress("db_2_" + i);
            news.setNewsTime(Calendar.getInstance().getTime());
            newsService.addNews(news);
        }

    }

}
