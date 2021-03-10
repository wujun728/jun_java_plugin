package org.typroject.tyboot.component.emq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;
import org.typroject.tyboot.core.foundation.context.SpringContextHelper;

public abstract class EmqxListener implements  IMqttMessageListener {

    private final Logger logger = LogManager.getLogger(EmqxListener.class);

    public abstract void processMessage(String topic, MqttMessage message) throws Exception;


    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        try {
            // subscribe后得到的消息会执行到这里面
            logger.debug("message id             : " + message.getId());
            logger.debug("message topic          : " + topic);
            logger.debug("message Qos            : " + message.getQos());
            byte[] messageContent = message.getPayload();
            logger.debug("message Payload        : " + new String(messageContent));

            processMessage(topic, message);
        } catch (Exception e) {
            //报错后断掉的问题，临时将错误吃掉。
            e.printStackTrace();
        }

    }

}
