/*
 * Copyright 2007-2017 the original author or authors.
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
 */
package net.ymate.platform.serv;

import java.io.Closeable;
import java.io.IOException;

/**
 * 客户端接口
 *
 * @param <CODEC> 客户端编解码器接口类型
 * @author 刘镇 (suninformation@163.com) on 15/10/15 上午10:21
 * @version 1.0
 */
public interface IClient<LISTENER extends IListener, CODEC extends ICodec> extends Closeable {

    /**
     * 初始化客户端服务
     *
     * @param moduleCfg        服务模块配置
     * @param clientName       服务配置名称
     * @param listener         事件适配器
     * @param codec            解码器
     * @param reconnectService 断线重连服务
     * @param heartbeatService 链路维护(心跳)服务
     */
    void init(IServModuleCfg moduleCfg,
              String clientName,
              LISTENER listener,
              CODEC codec,
              IReconnectService reconnectService,
              IHeartbeatService heartbeatService);

    /**
     * 连接远程服务端
     *
     * @throws IOException 可能产生的异常
     */
    void connect() throws IOException;

    /**
     * 重连远程服务端
     *
     * @throws IOException 可能产生的异常
     */
    void reconnect() throws IOException;

    /**
     * @return 是否已连接
     */
    boolean isConnected();

    /**
     * @return 客户端配置对象
     */
    IClientCfg clientCfg();

    /**
     * @param <T> 监听器类型
     * @return 返回监听器接口实现类对象
     */
    <T extends LISTENER> T listener();

    /**
     * 向远程服务发送消息
     *
     * @param message 消息对象
     * @throws IOException 可能产生的异常
     */
    void send(Object message) throws IOException;

    /**
     * 关闭客户端服务
     *
     * @throws IOException 可能产生的异常
     */
    void close() throws IOException;
}
