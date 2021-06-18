package org.typroject.tyboot.component.amqp;

import java.io.Serializable;
import java.util.Date;

public class AmqpMessage<T extends Serializable> implements Serializable {


    /**
     * 创建此消息的服务名称
     */
    private String serverName;

    /**
     * 触发此消息的用户
     */
    private String userId;

    /**
     * 触发此消息的请求
     */
    private String traceId;

    /**
     * 消息创建时间
     */
    private Date createTime;

    /**
     * 描述
     */
    private String description;

    /**
     * 消息队列名称
     */
    private String queue;


    /**
     * 消息处理器
     */
    private String messageHandler;

    //消息id
    private String messageId;

    T   body;    //消息内容


    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessageHandler() {
        return messageHandler;
    }

    public void setMessageHandler(String messageHandler) {
        this.messageHandler = messageHandler;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }
}


