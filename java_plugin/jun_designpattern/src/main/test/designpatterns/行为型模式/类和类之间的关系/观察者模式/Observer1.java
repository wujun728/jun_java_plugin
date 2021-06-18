package designpatterns.行为型模式.类和类之间的关系.观察者模式;

public class Observer1 implements Observer {

    @Override
    public void update() {
        System.out.println("观察者1接收到通知...");
    }
}