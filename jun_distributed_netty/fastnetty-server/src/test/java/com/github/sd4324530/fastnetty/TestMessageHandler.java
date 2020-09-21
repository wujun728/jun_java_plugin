package com.github.sd4324530.fastnetty;

import com.github.sd4324530.fastnetty.handler.MessageHandler;
import com.github.sd4324530.fastnetty.handler.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author peiyu
 */
public class TestMessageHandler implements MessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(TestMessageHandler.class);

    @Override
    public void handler(byte[] bytes, MessageSender sender) {
        TestMessage message = new TestMessage().fromBytes(bytes);
        LOG.debug("收到信息:{}", message.toString());
    }
}
