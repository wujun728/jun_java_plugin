package com.github.sd4324530.fastnetty;

import com.github.sd4324530.fastnetty.handler.MessageHandler;
import com.github.sd4324530.fastnetty.server.ServerManager;
import com.github.sd4324530.fastnetty.server.impl.NettyTCPServer;
import com.github.sd4324530.fastnetty.server.impl.ServerManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author peiyu
 */
public class FastnettyTest {

    private static final Logger LOG = LoggerFactory.getLogger(FastnettyTest.class);

    public static void main(String[] args) {
        ServerManager serverManager = new ServerManagerImpl();
        NettyTCPServer server = new NettyTCPServer();
        server.setWorkThreadSize(4);
        server.setPort(49151);
        TestMessageHandler messageHandler = new TestMessageHandler();
        Set<MessageHandler> handlers = new HashSet<>();
        handlers.add(messageHandler);
        server.setMessageHandlers(handlers);
        serverManager.addServer(server);
        try {
            serverManager.startServers();
            LOG.debug("启动监听成功......");
        } catch (Exception e) {
            LOG.error("启动监听异常", e);
        }
    }
}
