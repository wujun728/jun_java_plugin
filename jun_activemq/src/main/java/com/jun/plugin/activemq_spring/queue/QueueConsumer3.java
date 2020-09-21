package com.jun.plugin.activemq_spring.queue;

import javax.jms.JMSException;

import com.jun.plugin.activemq_spring.PersonInfo;

/**
 * 点对点消息消费者3 —— 未实现MessageListener接口，通过在spring-activemq-ptp.xml文件中配置进行相应的处理
 * 
 * 作者: zhoubang 
 * 日期：2015年9月28日 上午10:34:46
 */
public class QueueConsumer3 {

    /**
     * receive方法的参数类型是PersonInfo，为何呢？
     *  因为：UserMessageConverter类中的toMessage方法，已经将消息转换为PersonInfo类型了，所以，这里只需要直接指定消息实体bean的类型即可.与QueueMessageReceiver2、QueueMessageReceiver还是有些不同的.
     * 
     * 作者: zhoubang 
     * 日期：2015年9月28日 上午11:15:27
     * @param personInfo
     * @throws JMSException
     */
    public void receive(PersonInfo personInfo) throws JMSException {
        System.out.println();
        System.out.println("【消费者QueueReceiver3】收到queue的消息—->personInfo的用户名是:"  + personInfo.getUserName());
    }

}
