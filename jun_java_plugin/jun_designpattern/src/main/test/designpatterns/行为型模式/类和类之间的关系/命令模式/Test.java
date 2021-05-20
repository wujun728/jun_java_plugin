package designpatterns.行为型模式.类和类之间的关系.命令模式;

public class Test {
    public static void main(String[] args) {
        
        //创建士兵对象
        Receiver receiver = new Receiver();
        
        Command cmd = new MyCommand(receiver);
        
        //创建司令对象
        Invoker invoker = new Invoker(cmd);
        
        //发起命令
        invoker.action();
    }
}
