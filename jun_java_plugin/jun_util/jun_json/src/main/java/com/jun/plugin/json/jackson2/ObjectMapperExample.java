package com.jun.plugin.json.jackson2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jun.plugin.json.jackson2.pojo.MyValue;

/**
 * 简单的JSON转换示例
 * Created by luozhen on 2018/6/21.
 */
public class ObjectMapperExample {

    public static void log(String msg) {
        System.out.println(msg);
    }

    public static void simplePojo(ObjectMapper mapper)  throws Exception{
        //JSON转成JAVA-POJO对象
        //        MyValue value = mapper.readValue(new File("data.json"), MyValue.class);
        // or:
        //        MyValue value = mapper.readValue(new URL("http://some.com/api/entry.json"), MyValue.class);
        // or:
        String json = "{\"name\":\"Bob\", \"age\":13}";
        log("原始JSON： " + json);
        MyValue value = mapper.readValue(json, MyValue.class);
        log("转换成POJO： " + value);

        //对象转换成JSON
//        mapper.writeValue(new File("result.json"), myResultObject);
        // or:
//        byte[] jsonBytes = mapper.writeValueAsBytes(myResultObject);
        // or:
        String jsonString = mapper.writeValueAsString(value);
        log("转换成JSON： " + jsonString);
    }

    public static void jdkCollections(ObjectMapper mapper) throws Exception {

        Map<String, Integer> map = new HashMap<>();
        map.put("张三", 30);
        map.put("李四", 32);
        map.put("王五", 35);

        String json = mapper.writeValueAsString(map);

        log("Map对象转成JSON： " + json);

        map = mapper.readValue(json, Map.class);
        log("JSON对象转成Map对象： " + map);


        List<String> names = new ArrayList<>();
        names.add("张三");
        names.add("李四");
        names.add("王五");

        json = mapper.writeValueAsString(names);
        log("List对象转JSON： " + json);

        names = mapper.readValue(json, List.class);
        log("JSON转成LIST对象： " + names);
    }

    public static void objectNode(ObjectMapper mapper) throws Exception {

        //对象的转换
        MyValue value = new MyValue();
        value.setAge(30);
        value.setName("张三");
        String json = mapper.writeValueAsString(value);

        JsonNode jsonNode = mapper.readTree(json);
        String name = jsonNode.get("name").asText();
        int age = jsonNode.get("age").asInt();
        log("name: " + name);
        log("age: " + age);

        jsonNode.with("other");
        json = mapper.writeValueAsString(jsonNode);
        log("value to json: " + json);


        //集合的转换
        List<MyValue> list = new ArrayList<>();
        list.add(value);
        value = new MyValue();
        value.setName("李四");
        value.setAge(35);
        list.add(value);
        json = mapper.writeValueAsString(list);
        log("pojoList对象转换成json: " + json);

        //JSON转成List
        ArrayNode arrayNode = mapper.readValue(json, ArrayNode.class);
        list = new ArrayList<>();
        int size = arrayNode.size();
        for (int i=0; i<size; i++) {
            JsonNode node = arrayNode.get(i);
            MyValue v = new MyValue();
            v.setAge(node.get("age").asInt());
            v.setName(node.get("name").asText());
            list.add(v);
        }
        log("JSON转成pojoList对象： " + list);

    }


    public static void main(String[] args) throws Exception{
        //创建mapper
        ObjectMapper mapper = new ObjectMapper();

        simplePojo(mapper);

        jdkCollections(mapper);

        objectNode(mapper);
    }
}
