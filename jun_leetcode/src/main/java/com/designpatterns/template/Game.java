package com.designpatterns.template;
/**
 * 模板模式
 * @author: BaoZhou
 * @date : 2019/1/2 15:00
 */
public abstract class Game {
   abstract void initialize();
   abstract void startPlay();
   abstract void endPlay();

   /**
    * 模板
    */
   public final void play(){
      System.out.println("公共部分");
      //初始化游戏
      initialize();
 
      //开始游戏
      if(needPlayGame()){
         startPlay();
      }

      //结束游戏
      endPlay();
   }

   protected boolean needPlayGame(){
      return true;
   }
}
