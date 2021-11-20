package com.jun.base.synchronizedTest;

/**
 * 1.synchronized (Sync2.class) , 2.synchronized (this)
 *
 *
 *
 * 1,输出：开始-结束 开始-结束 开始-结束
 *
 *      实现了全局锁 ,锁住代码段
 *
 *
 * 2,输出：开始-开始-开始 -  结束-结束-结束
 *
 *      只是锁住了当前对象
 *
 *
 * 观察输出打印时间
 */
public class SyT2 {

    public static void main(String[] args) {
        for(int i = 1;i<=1;i++){

            Sya2 sya2 = new Sya2(i);
            sya2.start();
        }

    }
}

class Sync2 {

     public void test(int num){

        System.err.println(System.currentTimeMillis()+"==begin "+num);

        // this  Sync2.class 输出效果不一样
        //锁开始
        synchronized (Sync2.class){
            System.out.println(System.currentTimeMillis()+ "==test开始.."+num);
            try {

                Thread.sleep(2000);

            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(System.currentTimeMillis()+ "==test结束.."+num);
        }
        //锁结束
        System.err.println(System.currentTimeMillis()+ "==end "+num);
    }
}

class Sya2 extends Thread{

    private int num = -1;

    public Sya2(int num){

        this.num = num;
    }

    @Override
    public void run() {
        Sync2 Sync2 = new Sync2();
        Sync2.test(num);
    }
}