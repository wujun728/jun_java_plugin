package cn.springmvc.jpa.service;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.springmvc.jpa.entity.News;
import cn.springmvc.jpa.entity.User;
import cn.springmvc.jpa.service.NewsService;

/**
 * @author Wujun
 * 
 *         production为生产环境，development为测试环境
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml", "classpath:/spring/applicationContext-dao.xml" })
@ActiveProfiles("development")
public class NewsServiceTest {

    private static final Logger log = LoggerFactory.getLogger(NewsServiceTest.class);

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @Test
    public void addNews() {
        try {
            News news = new News();
            news.setAddress("xxx");
            news.setDescription("XXXX");
            news.setNewsTime(Calendar.getInstance().getTime());
            news.setTitle("TT");
            User user = userService.findLocalUserByName("wangxin");
            newsService.addNews(news, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findNews() {
        try {
            List<News> news = newsService.findNews();
            for (News n : news) {
                log.warn("# {} , {}", n.getTitle(), n.getDescription());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
