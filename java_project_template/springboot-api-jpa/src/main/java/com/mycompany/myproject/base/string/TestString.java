package com.mycompany.myproject.base.string;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

public class TestString {

    public static void main(String[] args) throws Exception{

        LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap<String,String>();
        linkedHashMap.put("1", "1");

        String[] key_values = ",50:Event,80:Test".split(",");

        for(String key_value : key_values){
            int position = key_value.indexOf(":");
            if(position <= 0) continue ;

            String name = key_value.substring(0, position);
            String value = key_value.substring(position, key_value.length()-1);

            System.out.println(name + "-" + value);

        }


        HashMap myMap =  new HashMap<String ,String >();
        myMap.put("1", "1");
//        for(int i=0; i < 1000; i++){
//            map.put(i+"", i+"");
//        }
        System.out.println(myMap);

        Set<Map.Entry<String,String>> set = myMap.entrySet();

        HashMap map2 = new HashMap<Integer,Integer>();


        WeakHashMap<String, String> map = new WeakHashMap<String, String>();
        for(int i = 0;i < 5;i++){
            map.put(new String("字符串"+i), new String("串串"+i));
        }
        System.gc();
        TimeUnit.SECONDS.sleep(2l);
        System.out.println(map.size());
        map.put(new String("字符串"+6), new String("串串"+6));


        IdentityHashMap ihm= new IdentityHashMap();
        //IdentityHashMap通过==来判断两个new String对象是不相等的，所以作为两个不同的对象加入
        ihm.put(new String("语文"), 89);
        ihm.put(new String("语文"), 93);
        System.out.println(ihm);
        //由于java是一个字符串，直接将它放在常量池中，故认为是两个相同的对象，。。所以就只会作为一个对象加入
        ihm.put("java", 88);
        ihm.put("java",90);
        System.out.println("第二次的ihm"+ihm);

        String language = "English";
        ihm.put(language, 88);
        language = "French";
        ihm.put(language, 89);
        System.out.println("第3次的ihm"+ihm);


        Hashtable<String, String> hashtable = new Hashtable<String, String>();

        ArrayList<String> arrayList = new ArrayList<String>();
        for(int i = 0;i < 11 ;i++){
            arrayList.add(new String("字符串"+i));
        }

        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
        linkedHashSet.add("1");


        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        copyOnWriteArrayList.add("1");
        copyOnWriteArrayList.add("1");
        System.out.println(copyOnWriteArrayList.size());
        CopyOnWriteArraySet copyOnWriteArraySet = new CopyOnWriteArraySet();
        copyOnWriteArraySet.add("1");
        copyOnWriteArraySet.add("1");

        System.out.println(copyOnWriteArraySet.size());


        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue<String>(20);
    }

}
