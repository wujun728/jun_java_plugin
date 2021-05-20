package org.typroject.tyboot.component.emq;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.typroject.tyboot.component.emq.message.EmqMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class EmqKeeper {

    private final Logger logger = LogManager.getLogger(EmqKeeper.class);


    private List<SubscriptTopic> topics = new ArrayList<>();
    private MqttClient client;
    private MqttConnectOptions options;

    @Autowired
    private EmqProperties emqProperties;


    public EmqKeeper(EmqProperties emqProperties) throws Exception {
        this.emqProperties = emqProperties;
        client = new MqttClient(emqProperties.getBroker(), emqProperties.getClientId(), new MemoryPersistence());// host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
        options = new MqttConnectOptions();// MQTT的连接设置
        options.setUserName(emqProperties.getUserName());
        options.setPassword(emqProperties.getPassword().toCharArray());
        options.setAutomaticReconnect(true);
        options.setCleanSession(emqProperties.getCleanSession());// 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
        options.setConnectionTimeout(0);// 设置超时时间 单位为秒
        options.setKeepAliveInterval(10);// 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        client.setCallback(new DefaultCallback());// 设置回调
        client.setTimeToWait(5000);
        connetToServer();
    }


    public boolean connetToServer() {
        try {
            if (!client.isConnected())
                client.connect(options);

            if (client.isConnected()) {
                logger.info("成功连接到EMQ;" + emqProperties.getBroker());

                //重新订阅主题
                for (SubscriptTopic subscriptTopic : topics)
                    this.getMqttClient().subscribe(subscriptTopic.getTopic(), subscriptTopic.getQos(), subscriptTopic.getEmqxListener());

                return client.isConnected();
            }
        } catch (MqttException e) {
            logger.info("连接到emq失败;" + emqProperties.getBroker());
            e.printStackTrace();
            return false;
        }
        return false;
    }


    public MqttClient getMqttClient() {
        return client;
    }


    /**
     * 订阅主题，并保存订阅记录，重连的时候，会自动订阅之前的主题。
     *
     * @param topic
     * @param qos
     * @param emqxListener
     * @throws Exception
     */
    public void subscript(String topic, int qos, EmqxListener emqxListener) throws Exception {
        if(getMqttClient().isConnected())
        {
            getMqttClient().subscribe(topic, qos, emqxListener);
            SubscriptTopic subscriptTopic = new SubscriptTopic();
            subscriptTopic.setEmqxListener(emqxListener);
            subscriptTopic.setQos(qos);
            subscriptTopic.setTopic(topic);
            this.topics.add(subscriptTopic);
        }else{
            logger.error("还未链接mqtt服务");
        }

    }


    /**
     * 构建消息实体
     *
     * @param body        消息内容
     * @param topic       主题
     * @param messageType 消息类型
     * @return
     */
    public <T> EmqMessage<T> buildMessage(T body, String topic, String messageType) throws Exception {
        EmqMessage<T> emqMessage = new EmqMessage();
        emqMessage.setBody(body);
        emqMessage.setMessageId(UUID.randomUUID().toString());
        emqMessage.setMessageType(messageType);
        emqMessage.setTopic(topic);
        emqMessage.setSourceClientId(this.getMqttClient().getClientId());
        return emqMessage;
    }


    class SubscriptTopic {
        private String topic;
        private int qos;
        private EmqxListener emqxListener;

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public int getQos() {
            return qos;
        }

        public void setQos(int qos) {
            this.qos = qos;
        }

        public EmqxListener getEmqxListener() {
            return emqxListener;
        }

        public void setEmqxListener(EmqxListener emqxListener) {
            this.emqxListener = emqxListener;
        }
    }

}
