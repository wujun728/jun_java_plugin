package com.jun.base.synchronizedTest;

/**
 * 锁一般分为对象锁和类锁(锁住代码块)。
 *
 * 这里是对象锁，一个对象对应一个单独的锁，多个对象之间互相不影响。
 *
 * 类锁，一般是对应在静态方法上
 */
public class SyT1 {

    public static void main(String[] args) {

        /**
         *
         一个对象对应一个锁
         *
         */
       /* Sync1 sync = new Sync1();

        Sya1 sya1 = new Sya1(sync);
        sya1.start();

        Syb1 syb1 = new Syb1(sync);
        syb1.start();*/


        /**
         *
         * 因为是一个对象一个锁，2个对象就是2个锁，所以去掉Sync2hronized不影响。
         *
         *
         */

        Sync1 sync1 = new Sync1();

        Sync1 sync2 = new Sync1();


        Sya1 sya1 = new Sya1(sync1);
        sya1.run();

        Syb1 syb1 = new Syb1(sync2);
        syb1.run();

    }


}

class Sync1 {

    private int num = 0;

    public synchronized void add(String name){

        try {
            if("a".equals(name)){
                num = 100;
                System.out.println("a set over");
                Thread.sleep(2000);
            }else{
                num = 200;
                System.out.println("b set over");
            }

            System.err.println(name + " num = "+ num);


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class Sya1 extends  Thread{

    private Sync1 numRef;

    public Sya1(Sync1 numRef){

        this.numRef =  numRef;
    }

    @Override
    public void run() {

        numRef.add("a");

    }
}

class Syb1 extends  Thread{

    private Sync1 numRef;

    public Syb1(Sync1 numRef){

        this.numRef =  numRef;
    }

    @Override
    public void run() {

        numRef.add("b");

    }
}
