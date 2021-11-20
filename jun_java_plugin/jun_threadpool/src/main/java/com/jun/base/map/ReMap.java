package com.jun.base.map;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/*
https://blog.csdn.net/lijizhi19950123/article/details/78209278
 */
public class ReMap {

    public static void main(String[] args) {

        Map hashMap = new HashMap<>();
        Map hashtable= new Hashtable();
        Map weakHashMap = new WeakHashMap();
        Map treeMap = new TreeMap();
        Set set = new  HashSet();
        set.add("a");
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        System.out.printf("");
    }
}
