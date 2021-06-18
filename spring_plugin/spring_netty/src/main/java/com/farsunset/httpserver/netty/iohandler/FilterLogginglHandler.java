/**
 * Copyright 2013-2033 Xia Jun(3979434@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ***************************************************************************************
 *                                                                                     *
 *                        Website : http://www.farsunset.com                           *
 *                                                                                     *
 ***************************************************************************************
 */
package com.farsunset.httpserver.netty.iohandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.SocketAddress;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;

public class FilterLogginglHandler extends LoggingHandler {
    public FilterLogginglHandler() {
        super(LogLevel.INFO);
    }

    public void channelRegistered(ChannelHandlerContext ctx) {
        ctx.fireChannelRegistered();
    }

    public void channelUnregistered(ChannelHandlerContext ctx) {
        ctx.fireChannelUnregistered();
    }

    public void channelActive(ChannelHandlerContext ctx) {
        ctx.fireChannelActive();
    }

    public void channelInactive(ChannelHandlerContext ctx) {
        ctx.fireChannelInactive();
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        ctx.fireUserEventTriggered(evt);
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise){
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel,ctx.channel().toString() + " WRITE \n" + msg.toString());
        }

        ctx.write(msg, promise);
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg)   {
        if (this.logger.isEnabled(this.internalLevel)) {
            HttpRequest request = (HttpRequest) msg;
            String log = request.method() + " " + request.uri() + " " + request.protocolVersion() + "\n" +
                    CONTENT_TYPE + ": " + request.headers().get(CONTENT_TYPE) + "\n" +
                    CONTENT_LENGTH + ": " + request.headers().get(CONTENT_LENGTH) + "\n";
            this.logger.log(this.internalLevel,ctx.channel().toString() + " READ \n" + log);
        }
        ctx.fireChannelRead(msg);
    }




    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) {
        ctx.bind(localAddress, promise);
    }

    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
        ctx.connect(remoteAddress, localAddress, promise);
    }

    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) {
        ctx.disconnect(promise);
    }

    public void close(ChannelHandlerContext ctx, ChannelPromise promise) {
        ctx.close(promise);
    }

    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) {
        ctx.deregister(promise);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.fireChannelReadComplete();
    }

    public void channelWritabilityChanged(ChannelHandlerContext ctx) {
        ctx.fireChannelWritabilityChanged();
    }

    public void flush(ChannelHandlerContext ctx) {
        ctx.flush();
    }
}
