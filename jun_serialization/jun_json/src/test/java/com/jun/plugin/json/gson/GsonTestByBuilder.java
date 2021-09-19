package com.jun.plugin.json.gson;

import com.google.gson.*;
import com.jun.plugin.json.gson.json.JsonData;
import com.jun.plugin.json.gson.pojo.TestPojoSimple;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Date;

public class GsonTestByBuilder {
    private GsonBuilder builder;
    private Gson gson;

    @Before
    public void init() {
        builder = new GsonBuilder();
    }

    /**
     * 在json转换为对象的过程中通过定义GsonBuilder过滤字段
     */
    @Test
    public void testBuilderFromJson() {
        gson = builder.addDeserializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getName().contains("name");
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();
        TestPojoSimple pojoSimple = gson.fromJson(JsonData.getSIMPLE_JSON(), TestPojoSimple.class);
        System.out.println(pojoSimple.toString());
    }

    /**
     * 在对象转换成json的过程中通过定义GsonBuilder过滤字段
     */
    @Test
    public void testBuilderToJson() {
        gson = builder.addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getName().contains("name");
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();
        TestPojoSimple pojoSimple = new TestPojoSimple("bill", 50, new Date());
        System.out.println(gson.toJson(pojoSimple));
    }

    /**
     * 序列化的时候null也显示为null而不是不显示，默认是null值不会被序列化
     */
    @Test
    public void testBuilderEnableNull() {
        gson = builder.serializeNulls().create();
        TestPojoSimple pojoSimple = new TestPojoSimple("bill", null, new Date());
        System.out.println(gson.toJson(pojoSimple));
    }

    /**
     * 更改在反序列化的时候的key值
     * builder.setFieldNamingStrategy(new FieldNamingStrategy() {
     * /@Override
     * public String translateName(Field f) {
     * return f.getName().replace("name", "newName");
     * }
     * }).create();
     * 其中replace的target为对象中的字段名，replacement为json中的字段名
     * 在序列化和反序列化的时候都是如此
     */
    @Test
    public void testBuilderNaming() {
        gson = builder.setFieldNamingStrategy(new FieldNamingStrategy() {
            @Override
            public String translateName(Field f) {
                return f.getName().replace("name", "newName");
            }
        }).create();
        TestPojoSimple pojoSimple = new TestPojoSimple("bill", 44, new Date());
        String json = gson.toJson(pojoSimple);
        System.out.println("json =" + json);
        pojoSimple = gson.fromJson(json, TestPojoSimple.class);
        System.out.println("pojo =" + pojoSimple);
    }

    /**
     * 序列化的时候剔除字段，可以用变量名，也可以用class进行删选
     * 如果想只对序列化或者反序列起作用，可以使用
     * addSerializationExclusionStrategy()
     * 和
     * addDeserializationExclusionStrategy()
     */
    @Test
    public void testBuilderExclusion() {
        gson = builder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getName().contains("name");
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return clazz == Date.class;
            }
        }).create();
        TestPojoSimple pojoSimple = new TestPojoSimple("bill", 50, new Date());
        System.out.println(gson.toJson(pojoSimple));
    }

}
