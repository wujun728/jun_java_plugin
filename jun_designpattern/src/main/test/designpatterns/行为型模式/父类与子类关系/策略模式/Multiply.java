package designpatterns.行为型模式.父类与子类关系.策略模式;

/**
 * 数值相乘
 * 
 * 作者: zhoubang 
 * 日期：2015年10月28日 下午4:21:31
 */
public class Multiply extends AbstractCalculator implements ICalculator {

    @Override
    public int calculate(String exp) {
        int arrayInt[] = split(exp, "\\*");
        return arrayInt[0] * arrayInt[1];
    }
}