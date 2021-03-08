package com.mycompany.myproject.base.collection.map;

import java.util.LinkedHashMap;

public class LinkedHashMapTest {

    public static void main(String[] args){

        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        linkedHashMap.put("1", "1");

        linkedHashMap.get("1");

        linkedHashMap.forEach((key,value) -> {


        });

    }
}
