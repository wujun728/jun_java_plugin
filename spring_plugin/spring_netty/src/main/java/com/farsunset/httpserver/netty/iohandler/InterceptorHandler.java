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

import com.farsunset.httpserver.netty.http.NettyHttpResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.ReferenceCountUtil;
import org.springframework.stereotype.Component;


@ChannelHandler.Sharable
@Component
/**
 * 在这里可以做拦截器，验证一些请求的合法性
 */
public class InterceptorHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext context, Object msg)   {
        if (isPassed((FullHttpRequest) msg)){
            context.fireChannelRead(msg);
            return;
        }

        ReferenceCountUtil.release(msg);
        context.writeAndFlush(NettyHttpResponse.make(HttpResponseStatus.UNAUTHORIZED)).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 修改实现来验证合法性
     * @param request
     * @return
     */
    private boolean isPassed(FullHttpRequest request){
        return true;
    }
}
