package com.mycompany.myproject.base.collection.list;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListTest {


    public static void main(String[] args) throws Exception{


        List<String> list =  new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");

        CopyOnWriteArrayList clist = new CopyOnWriteArrayList(list);

        Thread t = new Thread(new Runnable() {
            int count = -1;

            @Override
            public void run() {
                while (true) {
                    list.add(count++ + "");
                }
            }
        });
        t.setDaemon(true);
        t.start();

        Thread.currentThread().sleep(3);
        for (String s : list) {
            System.out.println(list.hashCode());
            System.out.println(s);
        }
    }
}
