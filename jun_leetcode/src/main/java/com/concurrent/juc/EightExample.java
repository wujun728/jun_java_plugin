package com.concurrent.juc;

/**
 * 八中不同情况锁的运行机制
 * 1.两个普通的同步（synchronized）方法，一个对象 //One Two
 * 2.在One中增加Thread.sleep方法 //One Two
 * 3.新增普通方法Three //Three One Two
 * 4.两个普通的同步（synchronized）方法，One中添加Sleep，两个对象 //Two One
 * 5.One为静态同步方法，一个对象//Two One
 * 6.设置One Two都为静态同步方法，一个对象 //经过Sleep的时间后 //One Two
 * 7.一个是静态同步方法，一个非静态同步方法，两个对象 //Two One
 * 8.两个是静态同步方法，两个对象//One Two 有延迟
 *
 * A.非静态方法的默认锁是this，静态方法的锁为对应的Class实例
 * B.在某一个时刻内 只有一个线程持有锁，无论几个方法
 * @author BaoZhou
 * @date 2018/7/29
 */
public class EightExample {
    public static void main(String[] args) {
        final Number number = new Number();
        final Number number2 = new Number();
        new Thread(new Runnable() {
            public void run() {
                number.One();
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
//                number.Two();
                number2.Two();
            }
        }).start();

//        new Thread(new Runnable() {
//            public void run() {
//                number.Three();
//            }
//        }).start();
    }


}

class Number
{
    public Number() {
    }

    static public synchronized void One()
    {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("One");
    }

    static public synchronized void Two()
    {
        System.out.println("Two");
    }

    public void Three()
    {
        System.out.println("Three");
    }
}

