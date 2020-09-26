/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.net.server;

import com.redis.proxy.net.command.RedisCmd;
import com.redis.proxy.net.resps.RdsResp;
import com.redis.proxy.net.resps.ErrorRdsResp;
import com.redis.proxy.net.resps.StatusRdsResp;
import com.redis.proxy.net.invoke.RdsCmdInvoke;
import com.redis.proxy.net.invoke.RdsCmdInvokeImpl;
import com.redis.proxy.net.handler.RdsMsgHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * netty handler
 *
 * @author zhanggaofeng
 */
public class ServerHandler extends SimpleChannelInboundHandler<RedisCmd> {

        private final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
        private final ThreadPoolExecutor executor;
        private final Map<String, RdsCmdInvoke> methods;

        public ServerHandler(int minThreads, int maxThreads, RdsMsgHandler handler) {
                executor = new ThreadPoolExecutor(minThreads, maxThreads, 30, TimeUnit.MINUTES, new LinkedBlockingQueue(Short.MAX_VALUE));
                methods = new HashMap<>();
                Class<? extends RdsMsgHandler> clazz = handler.getClass();
                for (final Method method : clazz.getMethods()) {
                        Class<?>[] types = method.getParameterTypes();
                        RdsCmdInvoke invoke = new RdsCmdInvokeImpl(types, method, handler);
                        /**
                         * 方法匹配
                         */
                        methods.put(method.getName(), invoke);
                        /**
                         * 全大写匹配
                         */
                        methods.put(method.getName().toUpperCase(), invoke);
                        /**
                         * 全小写匹配
                         */
                        methods.put(method.getName().toLowerCase(), invoke);
                }
        }

        @Override
        protected void messageReceived(final ChannelHandlerContext ctx, final RedisCmd cmd) throws Exception {
                /**
                 * 异步执行
                 */
                executor.execute(new Runnable() {
                        @Override
                        public void run() {
                                try {
                                        msgHandler(ctx, cmd);
                                } catch (Exception e) {
                                        ctx.writeAndFlush(new ErrorRdsResp(e));
                                        e.printStackTrace();
                                }
                        }
                });
        }

        /**
         * 消息处理
         *
         * @param ctx
         * @param json
         */
        private void msgHandler(ChannelHandlerContext ctx, final RedisCmd cmd) throws Exception {
                String name = new String(cmd.getName(), Charset.forName("UTF-8"));
                RdsCmdInvoke invoke = methods.get(name);
                RdsResp<?> resp;
                if (invoke == null) {
                        resp = new ErrorRdsResp("unknown command '" + name + "'");
                } else {
                        resp = invoke.execute(name, cmd);
                }
                if (resp == StatusRdsResp.QUIT) {
                        ctx.close();
                } else {
                        if (resp == null) {
                                resp = ErrorRdsResp.NYI_RESP;
                        }
                        ctx.writeAndFlush(resp);
                }
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
                ctx.flush();
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                logger.info("远 程服务  " + ctx.channel().remoteAddress() + "  链路建立");
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                logger.info("远程服务  " + ctx.channel().remoteAddress() + "  链路断开");
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                logger.error(cause + "");
                ctx.close();
        }
}
