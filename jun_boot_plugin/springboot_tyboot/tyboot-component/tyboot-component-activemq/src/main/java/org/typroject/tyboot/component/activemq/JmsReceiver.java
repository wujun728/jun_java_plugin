package org.typroject.tyboot.component.activemq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.typroject.tyboot.core.foundation.context.SpringContextHelper;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;



public class JmsReceiver {


    private  static final Logger logger = LogManager.getLogger(JmsReceiver.class);


    //监听队列，queue类型
    @JmsListener(destination=ActiveMqConfig.DEFAULT_QUEUE)
    public void receiveQueue(JmsMessage message){
        paresMessage(message);
    }

    //监听队列，topic类型，这里containerFactory要配置为jmsTopicListenerContainerFactory
    @JmsListener(destination = ActiveMqConfig.DEFAULT_TOPIC,
            containerFactory = ActiveMqConfig.DEFAULT_TOPIC_LISTENERCONTAINER
    )
    public void receiveTopic(JmsMessage message)  {
        paresMessage(message);
    }


    @JmsListener(destination=ActiveMqConfig.DEFAULT_QUEUEREPLY)
    @SendTo(ActiveMqConfig.OUT_REPLYTO_QUEUE) //消费者应答后通知生产者
    public String receiveQueueReply(JmsMessage message){
        System.out.println(this.getClass().getName()+ "收到的报文为:"+message);
        return "out.replyTo.queue receiveQueueReply";
    }


    public static void paresMessage(JmsMessage message)
    {
        try
        {
            MessageHandler messageHandler = (MessageHandler)SpringContextHelper.getBean(message.getMessageHandler());
            if(!ValidationUtil.isEmpty(messageHandler))
                messageHandler.process(message);
        }catch (Exception e)
        {
            logger.error("消息处理失败."+message.getMessageId());
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        }

    }

}
