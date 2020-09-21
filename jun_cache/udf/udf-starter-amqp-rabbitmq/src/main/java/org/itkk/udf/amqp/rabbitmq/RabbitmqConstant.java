/**
 * BaseMqConstant.java
 * Created at 2016-12-05
 * Created by Administrator
 * Copyright (C) 2016 egridcloud.com, All rights reserved.
 */
package org.itkk.udf.amqp.rabbitmq;

/**
 * 描述 : BaseMqConstant
 *
 * @author Administrator
 */
public class RabbitmqConstant {

    /**
     * 描述 : X_DEAD_LETTER_EXCHANGE
     */
    public static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";

    /**
     * 描述 : X_DEAD_LETTER_ROUTING_KEY
     */
    public static final String X_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    /**
     * (EXCHANGE)消息记录
     */
    public enum EXCHANGE_MESSAGE_LOG {
        /**
         * 发送消息
         */
        SEND,
        /**
         * 接收消息
         */
        RECEIVE,
        /**
         * 消息完成
         */
        COMPLETE,
        /**
         * 消息错误
         */
        ERROR
    }

    /**
     * (EXCHANGE)消息记录(死信)
     */
    public enum EXCHANGE_MESSAGE_LOG_DLX {
        /**
         * 发送消息
         */
        SEND_DLX,
        /**
         * 接收消息
         */
        RECEIVE_DLX,
        /**
         * 消息完成
         */
        COMPLETE_DLX,
        /**
         * 消息错误
         */
        ERROR_DLX
    }

    /**
     * 描述 : 私有化构造函数
     */
    private RabbitmqConstant() {
    }

}
