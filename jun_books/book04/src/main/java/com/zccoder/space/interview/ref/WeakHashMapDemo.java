package com.zccoder.space.interview.ref;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * WeakHashMap示例
 *
 * @author zc
 * @date 2020/05/04
 */
public class WeakHashMapDemo {

    public static void main(String[] args) {
        myHashMap();
        System.out.println("======================");
        myWeakHashMap();
    }

    private static void myWeakHashMap() {
        WeakHashMap<Integer, String> map = new WeakHashMap<>(16);

        Integer key = new Integer(2);
        String value = "WeakHashMap";
        map.put(key, value);
        System.out.println(map);

        key = null;
        System.out.println(map);

        System.gc();
        System.out.println(map);
    }

    private static void myHashMap() {
        HashMap<Integer, String> map = new HashMap<>(16);

        Integer key = 1;
        String value = "HashMap";
        map.put(key, value);
        System.out.println(map);

        key = null;
        System.out.println(map);

        System.gc();
        System.out.println(map);
    }
}
