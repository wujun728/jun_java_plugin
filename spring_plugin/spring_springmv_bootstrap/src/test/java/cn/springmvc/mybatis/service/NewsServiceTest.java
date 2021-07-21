package cn.springmvc.mybatis.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.springmvc.mybatis.common.base.model.Page;
import cn.springmvc.mybatis.entity.simple.News;
import cn.springmvc.mybatis.service.simple.NewsService;

/**
 * @author Wujun
 *
 *         production为生产环境，development为测试环境
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml", "classpath:/spring/applicationContext-dao.xml", "classpath:/spring/applicationContext-shiro.xml", "classpath:/spring/applicationContext-activiti.xml" })
@ActiveProfiles("development")
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
