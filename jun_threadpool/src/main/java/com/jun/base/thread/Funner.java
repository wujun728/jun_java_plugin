package com.jun.base.thread;

public class Funner {

    public void fun(){
        for (int i=1;i<=1000;i++){
            System.out.println(Thread.currentThread().getName()+":"+i);
        }
    }
}
