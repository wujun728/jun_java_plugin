package com.jun.plugin.fastnetty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.fastnetty.handler.MessageHandler;
import com.jun.plugin.fastnetty.handler.MessageSender;

/**
 * @author Wujun
 */
public class TestMessageHandler implements MessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(TestMessageHandler.class);

    @Override
    public void handler(byte[] bytes, MessageSender sender) {
        TestMessage message = new TestMessage().fromBytes(bytes);
        LOG.debug("收到信息:{}", message.toString());
    }
}
