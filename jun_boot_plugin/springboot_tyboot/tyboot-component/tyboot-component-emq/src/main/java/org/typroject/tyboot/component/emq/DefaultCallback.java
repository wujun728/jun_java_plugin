package org.typroject.tyboot.component.emq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.typroject.tyboot.core.foundation.context.SpringContextHelper;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;

public class DefaultCallback implements MqttCallback {

    private final Logger logger = LogManager.getLogger(EmqKeeper.class);


    private DeliveryComplete deliveryComplete;

    public DefaultCallback(DeliveryComplete deliveryComplete) {
        this.deliveryComplete = deliveryComplete;
    }

    public DefaultCallback() {
    }

    @Override
    public void connectionLost(Throwable throwable) {
        logger.info("EMQ连接断开.");

        //尝试重新连接，
        //10次
        for (int i = 0; i < 10; i++) {

            logger.info("第 " + i + " 次尝试重新连接.");
            EmqKeeper emqKeeper = (EmqKeeper) SpringContextHelper.getBean(EmqKeeper.class);

            if (!emqKeeper.getMqttClient().isConnected())
                emqKeeper.connetToServer();

            if (emqKeeper.getMqttClient().isConnected())
                break;
            try {
                Thread.sleep(10L * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        logger.debug("默认回调方法不处理消息。");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

        logger.debug("deliveryComplete---------" + token.isComplete());

        if(!ValidationUtil.isEmpty(deliveryComplete))
            deliveryComplete.complete(token);
    }
}
