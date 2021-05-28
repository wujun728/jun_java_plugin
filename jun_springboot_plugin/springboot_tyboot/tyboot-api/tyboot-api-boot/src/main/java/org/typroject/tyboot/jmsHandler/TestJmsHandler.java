package org.typroject.tyboot.jmsHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.typroject.tyboot.component.activemq.JmsMessage;
import org.typroject.tyboot.component.activemq.MessageHandler;


@Component("testJmsHandler")
public class TestJmsHandler  implements MessageHandler {


    private final Logger logger = LogManager.getLogger(TestJmsHandler.class);
    @Override
    public void process(JmsMessage message) {

        String body = String.valueOf(message.getBody());
        logger.info("接收到的消息."+body);
    }
}
