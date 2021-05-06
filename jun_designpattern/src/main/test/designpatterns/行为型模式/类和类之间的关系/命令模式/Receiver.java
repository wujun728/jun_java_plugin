package designpatterns.行为型模式.类和类之间的关系.命令模式;

/**
 * 被调用者，模拟士兵
 * 
 * 作者: zhoubang 
 * 日期：2015年10月29日 上午9:26:42
 */
public class Receiver {
    
    //执行命令
    public void action() {
        System.out.println("命令收到了，开始执行。");
    }
}