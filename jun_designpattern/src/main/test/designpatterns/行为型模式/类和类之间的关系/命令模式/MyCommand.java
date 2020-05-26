package designpatterns.行为型模式.类和类之间的关系.命令模式;

/**
 * 
 * 作者: zhoubang 
 * 日期：2015年10月29日 上午9:28:42
 */
public class MyCommand implements Command {

    //拥有士兵对象
    private Receiver receiver;

    public MyCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    //实现执行命令方法
    @Override
    public void exe() {
        receiver.action();
    }
}