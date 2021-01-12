package com.concurrent.yeild;

public class JoinExample
{
   public static void main(String[] args) throws InterruptedException
   {
      Thread t = new Thread(() -> {
         System.out.println("First task started");
         System.out.println("Sleeping for 2 seconds");
         try
         {
            Thread.sleep(2000);
         } catch (InterruptedException e)
         {
            e.printStackTrace();
         }
         System.out.println("First task completed");
      });
      Thread t1 = new Thread(() -> System.out.println("Second task completed"));
      //在t执行完毕后t1执行
      t.start(); // Line 15
      t.join(); // Line 16
      t1.start();
   }
}