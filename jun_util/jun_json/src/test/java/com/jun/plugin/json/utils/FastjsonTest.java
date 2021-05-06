package com.jun.plugin.json.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jun.plugin.json.utils.domain.Order;
import com.jun.plugin.json.utils.domain.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FastjsonTest {

    InputStream userUrl, listUrl, arrayUrl, userAddressUrl, userOrdersUrl;

    @Before
    public void setup() throws FileNotFoundException {
        userUrl = getClass().getClassLoader().getResourceAsStream("json/user.json");
        listUrl = getClass().getClassLoader().getResourceAsStream("json/list.json");
        arrayUrl = getClass().getClassLoader().getResourceAsStream("json/array.json");
        userAddressUrl = getClass().getClassLoader().getResourceAsStream("json/user-with-address.json");
        userOrdersUrl = getClass().getClassLoader().getResourceAsStream("json/user-with-orders.json");
    }

    @org.junit.Test
    public void testJsonToMap() throws IOException {
        Map<String, Object> result = JSON.parseObject(userUrl, Map.class);
        System.out.println("FastjsonTest.testParseJson: result ==> " + result);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.get("id"));
    }

    @org.junit.Test
    public void testJsonToList() throws IOException {
        List result = JSON.parseObject(arrayUrl, List.class);
        System.out.println("FastjsonTest.testJsonToList: result ==> " + result);
        Assert.assertNotNull(result);
    }

    @Test
    public void testJsonToArray() throws IOException {
        int[] result = JSON.parseObject(arrayUrl, int[].class);
        System.out.println("FastjsonTest.testJsonToArray: result ==> " + Arrays.toString(result));
        Assert.assertNotNull(result);
        String text = Arrays.toString(result);
        List<Integer> result2 = JSON.parseArray(text, Integer.class);
        System.out.println("FastjsonTest.testJsonToArray: result2 ==> " + result2);
    }

    @org.junit.Test
    public void testJsonToGenerifyList() throws IOException {
        TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {
        };
        List<User> result = JSON.parseObject(listUrl, Charset.defaultCharset(), typeReference.getType());
        System.out.println("FastjsonTest.testJsonToGenerifyList: result ==> " + result);
        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.size());
        for (int i = 0; i < result.size(); i++) {
            User user = result.get(i);
            System.out.println("FastjsonTest.testJsonToGenerifyList: user == > " + user);
        }

    }

    @org.junit.Test
    public void testJsonToPojo() throws IOException {
        User user = JSON.parseObject(userUrl, User.class);
        Assert.assertNotNull(user);
        Assert.assertEquals(1, user.getId());
    }

    @org.junit.Test
    public void testPojoToJson() throws IOException {
        User user = new User();
        user.setId(1);
        user.setUsername("yidasanqian");
        String userJson = JSON.toJSONString(user);
        System.out.println("FastjsonTest.testPojoToJson: userJson ==> " + userJson);
        Assert.assertNotNull(userJson);
    }

    @org.junit.Test
    public void testJsonToPojoWithReference() throws IOException {
        User user = JSON.parseObject(userAddressUrl, User.class);
        System.out.println("FastjsonTest.testJsonToPojoWithReference: user ==> " + user);
        Assert.assertNotNull(user);
        Assert.assertEquals(1, user.getId());
    }

    @org.junit.Test
    public void testJsonToPojoWithList() throws IOException {
        User user = JSON.parseObject(userOrdersUrl, User.class);
        System.out.println("JacksonTest.testJsonToPojoWithReference: orders ==> " + user.getOrders());
        Assert.assertNotNull(user.getOrders());
    }


    @Test
    public void testOrderToJson() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:dd");
        LocalDateTime now = LocalDateTime.now();
        String createAt = formatter.format(now);
        System.out.println("JacksonTest.testOrderToJson: createAt ==> " + createAt);
        Order order = new Order();
        order.setId(1);
        order.setTraceNo(110);
        order.setCreateAt(createAt);
        String orderJson = JSON.toJSONString(order);
        System.out.println("JacksonTest.testOrderToJson: orderJson ==> " + orderJson);
        Assert.assertNotNull(orderJson);
    }

    @Test
    public void testOrderToJson2() {
        Date updateAt = new Date();
        System.out.println("JacksonTest.testOrderToJson2: updateAt ==> " + updateAt);
        Order order = new Order();
        order.setId(1);
        order.setTraceNo(110);
        order.setUpdateAt(updateAt);
        String orderJson = JSON.toJSONString(order, true);
        //String orderJson = JSON.toJSONStringWithDateFormat(order, "yyyy年MM月dd日 HH时mm分ss秒");
        System.out.println("JacksonTest.testOrderToJson2: orderJson ==> " + orderJson);
        Assert.assertNotNull(orderJson);
    }
}
