package com.designpatterns.singleton.code;

public class T implements Runnable {

        @Override
        public void run() {
            ContainerSingleton.putInstance("object", new Object());
            Object instance = ContainerSingleton.getInstance("object");
            System.out.println(Thread.currentThread().getName() + " " + instance);
        }
    }