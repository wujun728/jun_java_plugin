package org.typroject.tyboot.component.activemq;

public interface MessageHandler {

   void  process(JmsMessage message);
}
