package com.mycompany.myproject.base.collection.queue;

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueTest {


    public static void main(String[] args) throws Exception{

        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(2);


        Thread thread1 = new Thread(new Runnable() {

            public void run(){

                try {
                    arrayBlockingQueue.put(new String("1"));
                    arrayBlockingQueue.put(new String("1"));

                    boolean ret = arrayBlockingQueue.offer(new String("1"));
                    System.out.println(ret);

                    arrayBlockingQueue.put(new String("1"));

                    System.out.println("take success");
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }


            }

        });

        Thread thread2 = new Thread(new Runnable() {
            public  void run(){

                try{

                    String ret = arrayBlockingQueue.take();
                    System.out.println(ret);

//                    ret = arrayBlockingQueue.take();
//                    System.out.println(ret);
//
//                    arrayBlockingQueue.take();

                }catch (InterruptedException ex){
                    ex.printStackTrace();;
                }

            }
        });

        thread1.start();

        thread2.start();
    }

}
