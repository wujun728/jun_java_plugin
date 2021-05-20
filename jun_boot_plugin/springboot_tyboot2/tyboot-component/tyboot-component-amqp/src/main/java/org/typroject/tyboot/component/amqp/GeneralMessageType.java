package org.typroject.tyboot.component.amqp;

/**
 * Created by yaohelang on 2017/10/25.
 */
public class GeneralMessageType implements MessageType {


    private String queue;

    private String type;

    private String name;

    private String messageHandler;

    public GeneralMessageType(String queue, String type, String name, String messageHandler) {
        this.queue = queue;
        this.type = type;
        this.name = name;
        this.messageHandler = messageHandler;
    }

    public String getQueue()
    {
        return this.queue;
    }

    public String getType()
    {
        return this.type;
    }

    public String getName()
    {
        return this.name;
    }

    public String getMessageHandler()
    {
        return this.messageHandler;
    }
}
