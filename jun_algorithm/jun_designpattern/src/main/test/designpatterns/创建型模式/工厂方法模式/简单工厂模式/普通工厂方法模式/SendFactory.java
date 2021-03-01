package designpatterns.创建型模式.工厂方法模式.简单工厂模式.普通工厂方法模式;

/**
 * 
 * 作者: zhoubang 日期：2015年10月26日 下午4:47:19
 */
public class SendFactory {
    
    /**
     * 根据输入的参数，判断创建不同的实现类
     * 
     * 作者: zhoubang 
     * 日期：2015年10月26日 下午4:47:59
     * @param type
     * @return
     */
    public Sender produce(String type) {
        if ("mail".equals(type)) {
            return new MailSender();
        } else if ("sms".equals(type)) {
            return new SmsSender();
        } else {
            System.out.println("请输入正确的类型!");
            return null;
        }
    }
}
