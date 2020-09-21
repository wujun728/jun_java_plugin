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

import java.util.Map;

/**
 * 客户端服务配置接口
 *
 * @author 刘镇 (suninformation@163.com) on 15/11/4 下午5:36
 * @version 1.0
 */
public interface IClientCfg {

    /**
     * @return 客户端名称
     */
    String getClientName();

    /**
     * @return 远程主机名称或IP地址
     */
    String getRemoteHost();

    /**
     * @return 远程服务监听端口
     */
    int getPort();

    /**
     * @return 字符编码
     */
    String getCharset();

    /**
     * @return 缓冲区大小
     */
    int getBufferSize();

    /**
     * @return 执行线程数量
     */
    int getExecutorCount();

    /**
     * @return 连接超时时间(秒)
     */
    int getConnectionTimeout();

    /**
     * @return 心跳包发送间隔(秒)
     */
    int getHeartbeatInterval();

    /**
     * @return 客户端自定义参数映射
     */
    Map<String, String> getParams();

    String getParam(String key);
}
