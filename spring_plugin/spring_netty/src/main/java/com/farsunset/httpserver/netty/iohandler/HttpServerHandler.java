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


import com.farsunset.httpserver.dto.Response;
import com.farsunset.httpserver.netty.annotation.NettyHttpHandler;
import com.farsunset.httpserver.netty.exception.IllegalMethodNotAllowedException;
import com.farsunset.httpserver.netty.exception.IllegalPathDuplicatedException;
import com.farsunset.httpserver.netty.exception.IllegalPathNotFoundException;
import com.farsunset.httpserver.netty.handler.IFunctionHandler;
import com.farsunset.httpserver.netty.http.NettyHttpRequest;
import com.farsunset.httpserver.netty.http.NettyHttpResponse;
import com.farsunset.httpserver.netty.path.Path;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.stream.Stream;

@ChannelHandler.Sharable
@Component
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServerHandler.class);

    private HashMap<Path, IFunctionHandler> functionHandlerMap = new HashMap<>();

    private ExecutorService executor = Executors.newCachedThreadPool(runnable -> {
        Thread thread = Executors.defaultThreadFactory().newThread(runnable);
        thread.setName("NettyHttpHandler-" + thread.getName());
        return thread;
    });

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        FullHttpRequest copyRequest = request.copy();
        executor.execute(() -> onReceivedRequest(ctx,new NettyHttpRequest(copyRequest)));
    }


    private void onReceivedRequest(ChannelHandlerContext context, NettyHttpRequest request){
        FullHttpResponse response = handleHttpRequest(request);
        context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        ReferenceCountUtil.release(request);
    }

    private FullHttpResponse handleHttpRequest(NettyHttpRequest request) {

        IFunctionHandler functionHandler = null;

        try {
            functionHandler = matchFunctionHandler(request);
            Response response =  functionHandler.execute(request);
            return NettyHttpResponse.ok(response.toJSONString());
        }
        catch (IllegalMethodNotAllowedException error){
            return NettyHttpResponse.make(HttpResponseStatus.METHOD_NOT_ALLOWED);
        }
        catch (IllegalPathNotFoundException error){
            return NettyHttpResponse.make(HttpResponseStatus.NOT_FOUND);
        }
        catch (Exception error){
            LOGGER.error(functionHandler.getClass().getSimpleName() + " Error",error);
            return NettyHttpResponse.makeError(error);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        Map<String, Object> handlers =  applicationContext.getBeansWithAnnotation(NettyHttpHandler.class);
        for (Map.Entry<String, Object> entry : handlers.entrySet()) {
            Object handler = entry.getValue();
            Path path = Path.make(handler.getClass().getAnnotation(NettyHttpHandler.class));
            if (functionHandlerMap.containsKey(path)){
                LOGGER.error("IFunctionHandler has duplicated :" + path.toString(),new IllegalPathDuplicatedException());
                System.exit(0);
            }
            functionHandlerMap.put(path, (IFunctionHandler) handler);
        }
    }

    private IFunctionHandler matchFunctionHandler(NettyHttpRequest request) throws IllegalPathNotFoundException, IllegalMethodNotAllowedException {

        AtomicBoolean matched = new AtomicBoolean(false);

        Stream<Path> stream = functionHandlerMap.keySet().stream()
                .filter(((Predicate<Path>) path -> {
                    /**
                     *过滤 Path URI 不匹配的
                     */
                    if (request.matched(path.getUri(), path.isEqual())) {
                        matched.set(true);
                        return matched.get();
                    }
                    return false;

                }).and(path -> {
                    /**
                     * 过滤 Method 匹配的
                     */
                    return request.isAllowed(path.getMethod());
                }));

        Optional<Path> optional = stream.findFirst();

        stream.close();

        if (!optional.isPresent() && !matched.get()){
            throw  new IllegalPathNotFoundException();
        }

        if (!optional.isPresent() && matched.get()){
            throw  new IllegalMethodNotAllowedException();
        }

        return functionHandlerMap.get(optional.get());
    }

}
