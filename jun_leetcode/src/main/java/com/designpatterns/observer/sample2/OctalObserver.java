package com.designpatterns.observer.sample2;


/**
 * 自己编写
 * @author: BaoZhou
 * @date : 2019/1/2 20:09
 */
public class OctalObserver extends Observer{
 
   public OctalObserver(Subject subject){
      this.subject = subject;
      this.subject.attach(this);
   }
 
   @Override
   public void update() {
     System.out.println( "Octal String: " 
     + Integer.toOctalString( subject.getState() ) ); 
   }
}
