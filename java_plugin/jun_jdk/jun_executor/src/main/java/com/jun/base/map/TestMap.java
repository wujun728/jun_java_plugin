package com.jun.base.map;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestMap {

    static HashMap<String,Integer> concurrentHashMap = new HashMap<>();

  //static ConcurrentHashMap<String,Integer> concurrentHashMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        concurrentHashMap.put("sz",1);
        add();

        //treeMap();

    }



    public static void add(){
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);


        for(int i = 0; i <=20; i++){

            final int j = i;
            fixedThreadPool.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        Integer i =  concurrentHashMap.get("sz");
                       System.out.println("i ="+ Thread.currentThread().getName()+"---" +i);
                        concurrentHashMap.put("sz",++i);
                       // System.err.println("i =="+ Thread.currentThread().getName()+"---" +concurrentHashMap.get("sz"));



                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     *
     *
     TreeMap 默认排序规则：按照key的字典顺序来排序（升序）

     当然，也可以自定义排序规则：要实现Comparator接口。


     TreeMap<String, String> map = new TreeMap<String, String>(new Comparator<String>() {

        @Override
        public int compare(String o1, String o2) {
            return o2.compareTo(o1);
        }

     });

     *
     */
    public  static void treeMap(){



        TreeMap<Integer, String> treeMap = new TreeMap<>();
        TreeMap<String, Integer> treeMap1 = new TreeMap<>();
        treeMap.put(7, "h");
        treeMap.put(8, "g");
        treeMap.put(9, "f");
        treeMap.put(10, "e");
        treeMap.put(14, "a");
        treeMap.put(1, "w");
        treeMap.put(2, "v");
        treeMap.put(3, "u");
        treeMap.put(11, "d");
        treeMap.put(12, "c");
        treeMap.put(13, "b");
        treeMap.put(4, "k");
        treeMap.put(5, "j");
        treeMap.put(6, "i");
        System.out.println("----------------------*------------------------------");
        while (treeMap.size() != 0) {
            //treemap自动按照key进行递增排序
            System.out.println(treeMap.firstEntry().getKey() + " - " + treeMap.firstEntry().getValue());
            treeMap1.put(treeMap.firstEntry().getValue(), treeMap.firstEntry().getKey());
            treeMap.remove(treeMap.firstKey());
        }
        System.out.println("----------------------*------------------------------");
        while (treeMap1.size() != 0) {
            //treemap自动按照key进行递增排序
            System.out.println(treeMap1.firstEntry().getKey() + " - " + treeMap1.firstEntry().getValue());
            treeMap1.remove(treeMap1.firstKey());
        }
        System.out.println("----------------------*------------------------------");
    }



}
