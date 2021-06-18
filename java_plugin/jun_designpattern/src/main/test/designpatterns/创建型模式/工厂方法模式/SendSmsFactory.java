package designpatterns.创建型模式.工厂方法模式;

/**
 * 发送短信工厂
 * 
 * 作者: zhoubang 
 * 日期：2015年10月26日 下午5:19:17
 */
public class SendSmsFactory implements Provider {

    /**
     * 生产，创建短信发送实例
     * 
     * 作者: zhoubang 
     * 日期：2015年10月26日 下午5:19:28
     * @return
     * (non-Javadoc)
     * @see com.demo.design_pattern.创建型模式.工厂方法模式.Provider#produce()
     */
    @Override
    public Sender produce() {
        return new SmsSender();
    }

}

