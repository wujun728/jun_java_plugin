package com.jun.plugin.json.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jun.plugin.json.gson.json.JsonData;
import com.jun.plugin.json.gson.pojo.TestPojoSimple;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class GsonTestSimple {
    private Gson gson;

    @Before
    public void init() {
        gson = new Gson();
    }

    @Test
    public void testSimpleToJSON() {
        TestPojoSimple pojoSimple = new TestPojoSimple("name", 11,null);
        String jsonSimple = gson.toJson(pojoSimple);
        System.out.println(jsonSimple);
    }

    @Test
    public void testSimpleFromJson1() {
        String json = JsonData.getSIMPLE_JSON();
        TestPojoSimple pojoUtil = gson.fromJson(json, new TypeToken<TestPojoSimple>() {
        }.getType());
        System.out.println(pojoUtil.toString());
    }

    @Test
    public void testSimpleFromJson2() {
        String json = JsonData.getSIMPLE_JSON();
        TestPojoSimple pojoSimple = gson.fromJson(json, TestPojoSimple.class);
        System.out.println(pojoSimple.toString());
    }

    @Test
    public void testSimpleFromJson3() {
        String json = JsonData.getSIMPLE_JSON();
        Map pojoMap = gson.fromJson(json, new TypeToken<Map>() {
        }.getType());
        System.out.println(pojoMap.toString());
    }
}
