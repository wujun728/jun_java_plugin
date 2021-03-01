package designpatterns.行为型模式.类的状态.状态模式;

/**
 * 状态模式的切换类
 * 
 * 作者: zhoubang 
 * 日期：2015年10月29日 上午9:43:38
 */
public class Context {

    //拥有状态类的核心类对象
    private State state;

    public Context(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    
    //不同的状态执行不同的逻辑
    public void method() {
        if (state.getValue().equals("state1")) {//第一种状态
            state.method1();
        } else if (state.getValue().equals("state2")) {//第二种状态
            state.method2();
        }
    }
}