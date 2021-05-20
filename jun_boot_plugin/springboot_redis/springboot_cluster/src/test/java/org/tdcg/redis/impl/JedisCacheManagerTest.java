package org.tdcg.redis.impl;

import com.google.common.collect.Maps;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tdcg.Application;
import org.tdcg.entity.User;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;


/**
 * @Title: JedisCacheManagerTest
 * @Package: org.tdcg.redis.impl
 * @Description: 测试类，测试方法有顺序要求
 * 添加@FixMethodOrder(MethodSorters.NAME_ASCENDING) 以使执行方法按名称顺序执行
 * @Author: 二东 <zwd_1222@126.com>
 * @date: 2016/10/24
 * @Version: V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JedisCacheManagerTest {

    private final int expiration = 3600;

    @Resource
    private JedisCacheManager jedisCacheManager;

    @Test()
    public void testAPutCache() throws Exception {
        boolean test = jedisCacheManager.putCache("test", "welocme redis cluster! created by tdcg!", expiration);
        assert(test);
    }

    @Test
    public void testBGetCache() throws Exception {
        Object test = jedisCacheManager.getCache("test");
        System.out.println(test);
        assert(test.equals("welocme redis cluster! created by tdcg!"));
    }


    @Test
    public void testCRemoveCache() throws Exception {
        Long test = jedisCacheManager.removeCache("test");
        assert(test == 1L);
    }

    @Test
    public void testDPutListCache() throws Exception {
        User user = new User("1","zhangsan",15);
        boolean userList = jedisCacheManager.putListCache("userList", user);
        assert(userList);
    }

    @Test
    public void testEPutListCache1() throws Exception {
        User user = new User("2","lisi",18);
        boolean userList = jedisCacheManager.putListCache("userList", user, 0);
        assert(userList);
    }

    @Test
    public void testFGetListCache() throws Exception {
        List<Object> userList = jedisCacheManager.getListCache("userList");
        User u = (User)userList.get(0);
        assert (u.getId().equals("2"));
    }

    @Test
    public void testGGetListCache1() throws Exception {
        List<Object> userList = jedisCacheManager.getListCache("userList", 0, 2);
        User u = (User)userList.get(0);
        assert (u.getId().equals("2"));
    }

    @Test
    public void testHTrimListCache() throws Exception {
        boolean userList = jedisCacheManager.trimListCache("userList", 0, 2);
        assert(userList);
    }

    @Test
    public void testIPutMapCache() throws Exception {
        Map<Object, Object> map = Maps.newHashMap();
        map.put("1",new User("1","zhangsan",15));
        map.put("2",new User("2","lisi",20));
        boolean userMap = jedisCacheManager.putMapCache("userMap", map);
        assert (userMap);
    }

    @Test
    public void testJGetMapValueCache() throws Exception {
        User user = (User)jedisCacheManager.getMapValueCache("userMap", "2");
        String id = user.getId();
        assert (id.equals("2"));
    }

    @Test
    public void testKDeleteMapCache() throws Exception {
        boolean userMap = jedisCacheManager.deleteMapCache("userMap", "1");
        assert (userMap);
    }

    @Test
    public void testL(){
        Long userMap = jedisCacheManager.removeCache("userMap");
        assert(userMap == 1L);
        Long userList = jedisCacheManager.removeCache("userList");
        assert(userList == 1L);
    }

}