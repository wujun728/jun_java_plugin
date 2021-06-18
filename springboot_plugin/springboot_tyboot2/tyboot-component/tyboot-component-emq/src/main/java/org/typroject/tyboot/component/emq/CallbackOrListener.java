package org.typroject.tyboot.component.emq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;
import org.typroject.tyboot.core.foundation.context.SpringContextHelper;

public abstract   class CallbackOrListener implements MqttCallback , IMqttMessageListener {

    private final Logger logger = LogManager.getLogger(CallbackOrListener.class);

    private String emqKeeperBeanName ;


    public CallbackOrListener(String emqKeeperBeanName)
    {
        this.emqKeeperBeanName = emqKeeperBeanName;
    }


    @Override
    public void connectionLost(Throwable throwable) {
        logger.info("EMQ连接断开.");
        try {
            EmqKeeper emqKeeper = (EmqKeeper)SpringContextHelper.getBean(this.emqKeeperBeanName);
            emqKeeper.connetToServer();

        }catch (Exception e)
        {
            logger.info("EMQ重新连接失败.");
            e.printStackTrace();
        }
    }


    public abstract void processMessage(String topic,MqttMessage message)throws Exception;



    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        try
        {
            // subscribe后得到的消息会执行到这里面
            logger.info("message id             : " + message.getId());
            logger.info("message topic          : " + topic);
            logger.info("message Qos            : " + message.getQos());
            byte [] messageContent                 = message.getPayload();
            logger.info("message Payload        : " + new String(messageContent));

            processMessage(topic,message);
        }catch (Exception e)
        {
            //报错后断掉的问题，临时将错误吃掉。
            e.printStackTrace();
        }

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {


        logger.info("deliveryComplete---------" + token.isComplete());

    }

}
