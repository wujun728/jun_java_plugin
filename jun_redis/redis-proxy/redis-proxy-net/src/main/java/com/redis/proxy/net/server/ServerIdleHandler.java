/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.net.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 钝化时间超时处理
 *
 * @author zhanggaofeng
 */
public class ServerIdleHandler extends IdleStateHandler {

        private static final int SET_IDLE_TIME = 12;
        private final Logger logger = LoggerFactory.getLogger(ServerIdleHandler.class);

        public ServerIdleHandler() {
                super(0, 0, SET_IDLE_TIME, TimeUnit.HOURS);
        }

        @Override
        protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
                ctx.fireUserEventTriggered(evt);
                if (IdleState.ALL_IDLE == evt.state()) {
                        logger.error("idle timeout " + SET_IDLE_TIME + " minutes, close connection  " + ctx.toString());
                        ctx.close();
                }
        }

}
