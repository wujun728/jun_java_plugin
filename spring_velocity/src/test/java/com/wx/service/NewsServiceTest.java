package com.wx.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wx.common.base.model.Page;
import com.wx.entity.simple.News;
import com.wx.service.simple.NewsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml", "classpath:/spring/applicationContext-dao.xml" })
public class NewsServiceTest {

    private static final Logger log = LoggerFactory.getLogger(NewsServiceTest.class);

    @Autowired
    private NewsService newsService;

    @Test
    public void findNewsByKeywords() {
        try {
            List<News> news = newsService.findNewsByKeywords(null);
            for (News n : news) {
                log.debug("# {} ,{} , {} ", n.getTitle(), n.getDescription(), n.getAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findNewsByPage() {
        try {
            Page<News> page = new Page<News>();
            page = newsService.findNewsByPage(page, null);
            for (News n : page.getResultList()) {
                log.debug("# {} ,{} , {} ", n.getTitle(), n.getDescription(), n.getAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
