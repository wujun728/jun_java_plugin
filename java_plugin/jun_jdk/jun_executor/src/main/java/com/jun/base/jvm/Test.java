package com.jun.base.jvm;

import java.util.LinkedList;

public class Test {

    public static void run() {
        LinkedList xttblog=new LinkedList();//作为GC Root
        while(true){
            xttblog.add(new HeapOOMTest());//疯狂创建对象
        }
    }

}

