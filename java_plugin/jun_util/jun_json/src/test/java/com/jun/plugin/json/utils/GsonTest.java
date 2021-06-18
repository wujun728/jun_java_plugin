package com.jun.plugin.json.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jun.plugin.json.utils.domain.Order;
import com.jun.plugin.json.utils.domain.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GsonTest {

    Gson gson;
    FileReader userUrl, listUrl, arrayUrl, userAddressUrl, userOrdersUrl;

    @Before
    public void setup() throws FileNotFoundException {
        gson = new GsonBuilder()
                .setLenient()
                .create();

        userUrl = new FileReader(getClass().getClassLoader().getResource("json/user.json").getFile());
        listUrl = new FileReader(getClass().getClassLoader().getResource("json/list.json").getFile());
        arrayUrl = new FileReader(getClass().getClassLoader().getResource("json/array.json").getFile());
        userAddressUrl = new FileReader(getClass().getClassLoader().getResource("json/user-with-address.json").getFile());
        userOrdersUrl = new FileReader(getClass().getClassLoader().getResource("json/user-with-orders.json").getFile());
    }

    @org.junit.Test
    public void testJsonToMap() throws IOException {
        Map<String, Object> result = gson.fromJson(userUrl, Map.class);
        System.out.println("GsonTest.testParseJson: result ==> " + result);
        Assert.assertNotNull(result);
        Assert.assertEquals(1.0, result.get("id"));
    }

    @org.junit.Test
    public void testJsonToList() {
        List result = gson.fromJson(arrayUrl, List.class);
        System.out.println("GsonTest.testJsonToList: result ==> " + result);
        Assert.assertNotNull(result);
    }

    /**
     * gson解析数值类型会解析成小数，需要使用TypeToken指定泛型解决
     */
    @org.junit.Test
    public void testJsonToList2() {
        TypeToken<List<Integer>> typeToken = new TypeToken<List<Integer>>() {
        };
        List<Integer> result = gson.fromJson(arrayUrl, typeToken.getType());
        System.out.println("GsonTest.testJsonToList2: result ==> " + result);
        Assert.assertNotNull(result);
    }

    @Test
    public void testJsonToArray() {
        int[] result = gson.fromJson(arrayUrl, int[].class);
        System.out.println("GsonTest.testJsonToArray: result ==> " + Arrays.toString(result));
        Assert.assertNotNull(result);
    }

    @org.junit.Test
    public void testJsonToGenerifyList() {
        TypeToken<List<User>> typeToken = new TypeToken<List<User>>() {
        };
        List<User> result = gson.fromJson(listUrl, typeToken.getType());
        System.out.println("GsonTest.testJsonToGenerifyList: result ==> " + result);
        Assert.assertEquals(2, result.size());
        for (int i = 0; i < result.size(); i++) {
            User user = result.get(i);
            System.out.println("JacksonTest.testJsonToList: user == > " + user);
        }
    }

    @org.junit.Test
    public void testJsonToPojo() {
        User user = gson.fromJson(userUrl, User.class);
        Assert.assertNotNull(user);
        Assert.assertEquals(1, user.getId());
    }

    @org.junit.Test
    public void testPojoToJson() {
        User user = new User();
        user.setId(1);
        user.setUsername("yidasanqian");
        String userJson = gson.toJson(user);
        System.out.println("GsonTest.testPojoToJson: userJson ==> " + userJson);
        Assert.assertNotNull(userJson);
    }

    @org.junit.Test
    public void testJsonToPojoWithReference() {
        User user = gson.fromJson(userAddressUrl, User.class);
        System.out.println("GsonTest.testJsonToPojoWithReference: user ==> " + user);
        Assert.assertNotNull(user);
        Assert.assertEquals(1, user.getId());
    }

    @org.junit.Test
    public void testJsonToPojoWithList() {
        User user = gson.fromJson(userOrdersUrl, User.class);
        System.out.println("GsonTest.testJsonToPojoWithReference: orders ==> " + user.getOrders());
        Assert.assertNotNull(user.getOrders());
    }

    @Test
    public void testOrderToJson() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:dd");
        LocalDateTime now = LocalDateTime.now();
        String createAt = formatter.format(now);
        System.out.println("GsonTest.testOrderToJson: createAt ==> " + createAt);
        Order order = new Order();
        order.setId(1);
        order.setTraceNo(110);
        order.setCreateAt(createAt);
        String orderJson = gson.toJson(order);
        System.out.println("GsonTest.testOrderToJson: orderJson ==> " + orderJson);
        Assert.assertNotNull(orderJson);
    }

    @Test
    public void testOrderToJson2() {
        Date updateAt = new Date();
        System.out.println("GsonTest.testOrderToJson2: updateAt ==> " + updateAt);
        Order order = new Order();
        order.setId(1);
        order.setTraceNo(110);
        order.setUpdateAt(updateAt);
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:dd")
                .create();
        String orderJson = gson.toJson(order);
        System.out.println("GsonTest.testOrderToJson2: orderJson ==> " + orderJson);
        Assert.assertNotNull(orderJson);
    }
}
