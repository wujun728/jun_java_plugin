/**
 * Copyright (c) 2015-2017, Winter Lau (javayou@gmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.oschina.j2cache.cluster;

import com.rabbitmq.client.*;
import net.oschina.j2cache.CacheException;
import net.oschina.j2cache.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * 使用 RabbitMQ 实现集群内节点的数据通知（用于对数据一致性要求特别严格的场景）
 * @author Wujun
 */
public class RabbitMQClusterPolicy implements ClusterPolicy, Consumer {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQClusterPolicy.class);

    private static final String EXCHANGE_TYPE = "fanout";

    private ConnectionFactory factory;
    private Connection conn_publisher;
    private Connection conn_consumer;
    private Channel channel_publisher;
    private Channel channel_consumer;
    private String exchange;

    /**
     * @param props RabbitMQ 配置信息
     */
    public RabbitMQClusterPolicy(Properties props){
        this.exchange = props.getProperty("exchange", "j2cache");
        factory = new ConnectionFactory();
        factory.setHost(props.getProperty("host" , "127.0.0.1"));
        factory.setPort(Integer.valueOf(props.getProperty("port", "5672")));
        factory.setUsername(props.getProperty("username" , null));
        factory.setPassword(props.getProperty("password" , null));
        //TODO 更多的 RabbitMQ 配置
    }

    @Override
    public void connect(Properties props) {
        try {
            long ct = System.currentTimeMillis();
            conn_publisher = factory.newConnection();
            channel_publisher = conn_publisher.createChannel();
            channel_publisher.exchangeDeclare(exchange, EXCHANGE_TYPE);
            publish(Command.join());

            conn_consumer = factory.newConnection();
            channel_consumer = conn_consumer.createChannel();
            channel_consumer.exchangeDeclare(exchange, EXCHANGE_TYPE);
            String queueName = channel_consumer.queueDeclare().getQueue();
            channel_consumer.queueBind(queueName, exchange, "");

            channel_consumer.basicConsume(queueName, true, this);

            log.info("Connected to RabbitMQ:" + conn_consumer + ", time " + (System.currentTimeMillis()-ct) + " ms.");
        } catch (Exception e) {
            throw new CacheException(String.format("Failed to connect to RabbitMQ (%s:%d)", factory.getHost(), factory.getPort()), e);
        }
    }

    /**
     * 发布消息
     * @param cmd 消息数据
     */
    @Override
    public void publish(Command cmd) {
        //失败重连
        if(!channel_publisher.isOpen() || !conn_publisher.isOpen()) {
            synchronized (RabbitMQClusterPolicy.class) {
                if(!channel_publisher.isOpen() || !conn_publisher.isOpen()) {
                    try {
                        conn_publisher = factory.newConnection();
                        channel_publisher = conn_publisher.createChannel();
                    } catch(Exception e) {
                        throw new CacheException("Failed to connect to RabbitMQ!", e);
                    }
                }
            }
        }
        try {
            channel_publisher.basicPublish(exchange, "", null, cmd.json().getBytes());
        } catch (IOException e ) {
            throw new CacheException("Failed to publish cmd to RabbitMQ!", e);
        }
    }

    @Override
    public void disconnect() {
        try {
            publish(Command.quit());
        } finally {
            try {
                channel_publisher.close();
                conn_publisher.close();
            } catch(Exception e){}

            try {
                channel_consumer.close();
                conn_consumer.close();
            } catch(Exception e){}
        }
    }

    @Override
    public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) {
        handleCommand(Command.parse(new String(bytes)));
    }

    @Override
    public void handleConsumeOk(String s) {

    }

    @Override
    public void handleCancelOk(String s) {

    }

    @Override
    public void handleCancel(String s) {

    }

    @Override
    public void handleShutdownSignal(String s, ShutdownSignalException e) {

    }

    @Override
    public void handleRecoverOk(String s) {

    }

}
