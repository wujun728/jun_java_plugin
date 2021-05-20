package designpatterns.行为型模式.通过中间类.解释器模式;

/**
 * 上下文环境类
 * 
 * 作者: zhoubang 
 * 日期：2015年10月29日 上午10:11:42
 */
public class Context {

    private int num1;
    private int num2;

    public Context(int num1, int num2) {
        this.num1 = num1;
        this.num2 = num2;
    }

    public int getNum1() {
        return num1;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public int getNum2() {
        return num2;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }

}