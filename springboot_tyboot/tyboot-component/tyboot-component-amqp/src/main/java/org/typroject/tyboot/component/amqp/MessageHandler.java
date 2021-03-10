package org.typroject.tyboot.component.amqp;

public interface MessageHandler {

   void  process(AmqpMessage message) throws Exception;
}
