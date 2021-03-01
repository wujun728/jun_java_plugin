package com.jun.base.thread;

public class One extends Thread{

    public One(String name) {
        super(name);
    }

    @Override
    public void run() {

        for (int i=1;i<=1000;i++){
            System.out.println(this.getName()+":"+i);
        }

    }
}
