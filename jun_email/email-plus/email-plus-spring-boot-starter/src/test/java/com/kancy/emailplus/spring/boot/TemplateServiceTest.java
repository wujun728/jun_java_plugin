package com.kancy.emailplus.spring.boot;

import com.kancy.emailplus.spring.boot.config.EmailplusAutoConfiguration;
import com.kancy.emailplus.spring.boot.service.TemplateService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * TemplateServiceTest
 *
 * @author Wujun
 * @date 2020/2/21 0:31
 */
@ActiveProfiles({"test"})
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmailplusAutoConfiguration.class, RedisAutoConfiguration.class})
public class TemplateServiceTest {

    @Autowired
    private TemplateService templateService;

    @Test
    public void testRender() throws Exception {
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("name","kancy");
        Optional<String> render = templateService.render("hi.ftl", dataMap);
        Assert.assertTrue(render.isPresent());
        Assert.assertEquals("hi kancy", render.get());
        System.out.println(render.get());
    }

    @Test
    public void testRenderHtml() throws Exception {
        Map<String,Object> dataMap = new HashMap<>();
        Optional<String> render = templateService.renderHtml("index.html", dataMap);
        Assert.assertTrue(render.isPresent());
        Assert.assertTrue(!render.get().contentEquals(" "));
        Assert.assertTrue(!render.get().contentEquals("\r"));
        Assert.assertTrue(!render.get().contentEquals("\n"));
        Assert.assertTrue(!render.get().contentEquals("\t"));
        System.out.println(render.get());
    }
}
