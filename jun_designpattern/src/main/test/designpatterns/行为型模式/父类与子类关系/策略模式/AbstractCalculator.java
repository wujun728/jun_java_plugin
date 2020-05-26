package designpatterns.行为型模式.父类与子类关系.策略模式;

/**
 * 辅助类，对数值进行格式拆分，获取2个数值，交给ICalculator接口具体的实现去操作
 * 
 * 作者: zhoubang 
 * 日期：2015年10月28日 下午4:19:33
 */
public abstract class AbstractCalculator {

    //辅助方法，解析数值
    public int[] split(String exp, String opt) {
        String array[] = exp.split(opt);
        int arrayInt[] = new int[2];
        arrayInt[0] = Integer.parseInt(array[0]);
        arrayInt[1] = Integer.parseInt(array[1]);
        return arrayInt;
    }
}