package com.mycompany.myproject.base.collection.map;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {
    
    public static void main(String[] args){

        Map map = null;
        HashMap hashMap = new HashMap<String,String>();
        for (int i=0; i<1000; i++){
            hashMap.put("key" + i, "value" + i);
        }

        //Set<Map.Entry<String,String>> entrys = hashMap.entrySet();

        long start = System.currentTimeMillis();
        hashMap.entrySet().forEach(en ->{
            //System.out.println(en);
        });
        System.out.println("hashMap.entrySet().forEach : " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        hashMap.entrySet().iterator().forEachRemaining(entry ->{
            //System.out.println(entry);
        });
        System.out.println("hashMap.entrySet().iterator().forEachRemaining : " + (System.currentTimeMillis() - start));


        start = System.currentTimeMillis();
        hashMap.keySet().forEach(key ->{
            //System.out.println(key);
        });
        System.out.println("hashMap.keySet().forEach : " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        hashMap.values().forEach(value -> {
            //System.out.println(value);
        });
        System.out.println("hashMap.values().forEach : " + (System.currentTimeMillis() - start));


        start = System.currentTimeMillis();
        hashMap.forEach((key,value) -> {
            //System.out.println(key + ":" + value);
        });
        System.out.println("hashMap.forEach : " + (System.currentTimeMillis() - start));

    }


}
