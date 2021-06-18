package net.oschina.j2cache.hibernate4;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.oschina.j2cache.J2Cache;
import net.oschina.j2cache.hibernate4.bean.Article;
import net.oschina.j2cache.hibernate4.service.ArticleService;

/**
 * 测试前置条件 有疑问连接QQ:253161354
 * 1.修改 jdbc.properties 并建立数据库
 * 2.修改 redis.properties 并启动服务
 * 3.hibernate自动生成测试表。所以不用建表
 *
 * 注：原来的方式不支持级联加载对象的缓存，只适用于 query
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class J2CacheRegionFactoryTest {

    private final static Logger LOG = LoggerFactory.getLogger(J2CacheRegionFactoryTest.class);

    @Resource
    private ArticleService articleService;

    @Before
    public void setUp() throws Exception {
        this.tearDown();

        Article article = new Article();
        article.setTitle("测试缓存");
        article.setSummary("测试数据摘要");
        this.articleService.save(article);

        Article article1 = new Article();
        article1.setTitle("测试缓存1");
        article1.setSummary("测试数据摘要1");
        this.articleService.save(article1);
    }

    @After
    public void tearDown() throws Exception {
        for (Article art : this.articleService
                .find(Restrictions.like("title", "测试缓存", MatchMode.START))) {
            this.articleService.delete(art.getId());
        }
    }

    @Test
    public void findUnique() {

        // 查询单个对象
        Article article = this.articleService
                .findUnique(Restrictions.like("title", "测试缓存1", MatchMode.START));
        LOG.debug("第一次查询,缓存查询结果:" + article);

        article = this.articleService
                .findUnique(Restrictions.like("title", "测试缓存1", MatchMode.START));
        LOG.debug("第二次查询，从缓存中获取:" + article);

        // 直接从缓存中读取数据

        Map<String, Object> narticle =
                getCacheValue("net.oschina.j2cache.hibernate4.bean.Article", article.getId());
        LOG.debug("直接从缓存中读取数据:" + narticle);

        // 验证结果
        assert article.getTitle()
                .equals(narticle.get("title"));

        // 修改数据后
        Article saveartice = new Article();
        saveartice.setId(article.getId());
        saveartice.setTitle("测试缓存1");
        saveartice.setSummary("修改数据摘要");
        articleService.save(saveartice);

        // 缓存数据会删除
        narticle = getCacheValue("net.oschina.j2cache.hibernate4.bean.Article", article.getId());
        LOG.debug("修改后直接从缓存中读取数据:" + narticle);
        // 发现还是原来的值，因为hibernate的缓存是存在于三个位置的，第一个为查询sql及条件作为的key。第二个为缓存单个对象的过期时间。第三个才是缓存的对象

        article = this.articleService
                .findUnique(Restrictions.like("title", "测试缓存1", MatchMode.START));
        LOG.debug("再次查询,缓存查询结果:" + article);// 会重新缓存结果

        narticle = getCacheValue("net.oschina.j2cache.hibernate4.bean.Article", article.getId());
        LOG.debug("再次直接从缓存中读取数据:" + narticle);

        // 验证结果
        assert article.getTitle()
                .equals(narticle.get("title"));
    }

    @Test
    public void find() {
        // 查询list
        List<Article> articleList = this.articleService.find();
        LOG.debug("第一次查询,缓存查询结果:" + articleList);

        articleList = this.articleService.find();
        LOG.debug("第二次查询，从缓存中获取:" + articleList);

        // 修改数据后
        Article saveartice = new Article();
        saveartice.setId(articleList.get(0)
                .getId());
        saveartice.setTitle("测试缓存1");
        saveartice.setSummary("修改数据摘要");
        articleService.save(saveartice);
        // 缓存数据会删除

        articleList = this.articleService.find();
        LOG.debug("再次查询,缓存查询结果:" + articleList);// 会重新缓存结果

        // 测试新增数据
        Article newartice = new Article();
        newartice.setTitle("测试缓存2");
        newartice.setSummary("修改数据摘要");
        articleService.save(newartice);
        // 缓存数据会删除

        articleList = this.articleService.find();
        LOG.debug("再次查询,缓存查询结果:" + articleList);// 会重新缓存结果
        assert articleList.size() == 3;

    }

    private Map getCacheValue(String region, String key) {
        try {
            Object item = J2Cache.getChannel()
                    .get(region, key)
                    .getValue();
            if (item != null)
                return (Map) BeanUtils.getPropertyDescriptor(item.getClass(), "value")
                        .getReadMethod()
                        .invoke(item);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }
}
