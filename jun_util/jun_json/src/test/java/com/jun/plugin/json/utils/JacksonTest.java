package com.jun.plugin.json.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jun.plugin.json.utils.domain.Order;
import com.jun.plugin.json.utils.domain.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JacksonTest {

    ObjectMapper objectMapper;
    URL userUrl, listUrl, arrayUrl, userAddressUrl, userOrdersUrl;


    @Before
    public void setup() {
        objectMapper = new ObjectMapper();
        userUrl = getClass().getClassLoader().getResource("json/user.json");
        listUrl = getClass().getClassLoader().getResource("json/list.json");
        arrayUrl = getClass().getClassLoader().getResource("json/array.json");
        userAddressUrl = getClass().getClassLoader().getResource("json/user-with-address.json");
        userOrdersUrl = getClass().getClassLoader().getResource("json/user-with-orders.json");
    }

    @org.junit.Test
    public void testJsonToMap() throws URISyntaxException, IOException {
        Map<String, Object> result = objectMapper.readValue(userUrl, Map.class);
        System.out.println("JacksonTest.testParseJson: result ==> " + result);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.get("id"));

    }


    @org.junit.Test
    public void testJsonToList() throws URISyntaxException, IOException {
        List result = objectMapper.readValue(arrayUrl, List.class);
        System.out.println("JacksonTest.testJsonToList: result ==> " + result);
        Assert.assertNotNull(result);
    }


    @org.junit.Test
    public void testJsonToGenerifyList() throws URISyntaxException, IOException {
        TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {
        };
        List<User> result = objectMapper.readValue(listUrl, typeReference);
        System.out.println("JacksonTest.testJsonToGenerifyList: result ==> " + result);
        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.size());
        for (int i = 0; i < result.size(); i++) {
            User user = result.get(i);
            System.out.println("JacksonTest.testJsonToGenerifyList: user == > " + user);
        }

    }

    @org.junit.Test
    public void testJsonToPojo() throws IOException {
        User user = objectMapper.readValue(new FileReader(userUrl.getPath()), User.class);
        Assert.assertNotNull(user);
        Assert.assertEquals(1, user.getId());
    }

    @org.junit.Test
    public void testPojoToJson() throws IOException {
        User user = new User();
        user.setId(1);
        user.setUsername("yidasanqian");
        String userJson = objectMapper.writeValueAsString(user);
        System.out.println("JacksonTest.testPojoToJson: userJson ==> " + userJson);
        Assert.assertNotNull(userJson);
    }

    @org.junit.Test
    public void testJsonToPojoWithReference() throws IOException {
        User user = objectMapper.readValue(new FileReader(userAddressUrl.getPath()), User.class);
        System.out.println("JacksonTest.testJsonToPojoWithReference: user ==> " + user);
        Assert.assertNotNull(user);
        Assert.assertEquals(1, user.getId());
    }

    @org.junit.Test
    public void testJsonToPojoWithList() throws IOException {
        User user = objectMapper.readValue(new FileReader(userOrdersUrl.getPath()), User.class);
        System.out.println("JacksonTest.testJsonToPojoWithReference: orders ==> " + user.getOrders());
        Assert.assertNotNull(user.getOrders());
    }

    @org.junit.Test
    public void testConvertFromList() throws IOException {
        List sourceList = objectMapper.readValue(arrayUrl, List.class);
        // Convert from List<Integer> to int[]
        int[] ints = objectMapper.convertValue(sourceList, int[].class);
        System.out.println("JacksonTest.testConversions: ints ==> " + Arrays.toString(ints));
        Assert.assertNotNull(ints);

    }

    @org.junit.Test
    public void testConvertFromPojo() throws IOException {
        User pojoValue = objectMapper.readValue(new FileReader(userUrl.getPath()), User.class);
        // Convert a POJO into Map!
        Map<String, Object> propertyMap = objectMapper.convertValue(pojoValue, Map.class);
        System.out.println("JacksonTest.testConvertFromPojo: propertyMap ==> " + propertyMap);
        Assert.assertNotNull(propertyMap);
    }

    @org.junit.Test
    public void testConvertFromMap() throws IOException {
        Map<String, Object> propertyMap = objectMapper.readValue(userUrl, Map.class);
        // Convert a Map into POJO!
        User user = objectMapper.convertValue(propertyMap, User.class);
        System.out.println("JacksonTest.testConvertFromMap: user ==> " + user);
        Assert.assertNotNull(user);
    }

    @Test
    public void testOrderToJson() throws JsonProcessingException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:dd");
        LocalDateTime now = LocalDateTime.now();
        String createAt = formatter.format(now);
        System.out.println("JacksonTest.testOrderToJson: createAt ==> " + createAt);
        Order order = new Order();
        order.setId(1);
        order.setTraceNo(110);
        order.setCreateAt(createAt);
        String orderJson = objectMapper.writeValueAsString(order);
        System.out.println("JacksonTest.testOrderToJson: orderJson ==> " + orderJson);
        Assert.assertNotNull(orderJson);
    }

    @Test
    public void testOrderToJson2() throws JsonProcessingException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
        Date updateAt = new Date();
        System.out.println("JacksonTest.testOrderToJson2: updateAt ==> " + updateAt);
        Order order = new Order();
        order.setId(1);
        order.setTraceNo(110);
        order.setUpdateAt(updateAt);
        ObjectWriter writer = objectMapper.writer(sdf);
        String orderJson = writer.writeValueAsString(order);
        System.out.println("JacksonTest.testOrderToJson2: orderJson ==> " + orderJson);
        Assert.assertNotNull(orderJson);
    }
}
