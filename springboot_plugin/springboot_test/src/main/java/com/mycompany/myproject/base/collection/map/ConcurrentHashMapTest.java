package com.mycompany.myproject.base.collection.map;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {

    public static void main(String[] args){


        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();

        Random ra =new Random();
        for(int i=0; i< 1000; i++){
            concurrentHashMap.put("key_" + ra.nextInt(1000)+ i, "value_" + i);
        }


        concurrentHashMap.get("1");

        concurrentHashMap.remove("1");
    }

}
