package com.jun.plugin.tool.test.gson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.jun.plugin.tool.gson.GsonTool;

public class GsonToolTest {

    public static void main(String[] args) {
        Map<String, Object> result = new HashMap<>();
        result.put("int", 200);
        result.put("str", "success");
        result.put("arr", Arrays.asList("111","222"));
        result.put("float", 1.11f);

        String json = GsonTool.toJson(result);
        System.out.println(json);

        Object objectMap = GsonTool.fromJson(json, Map.class);
        System.out.println(objectMap);
    }

}
