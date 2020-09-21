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
 * 服务端配置接口
 *
 * @author 刘镇 (suninformation@163.com) on 15/11/4 下午5:35
 * @version 1.0
 */
public interface IServerCfg {

    /**
     * @return 服务名称
     */
    String getServerName();

    /**
     * @return 主机名称或IP地址
     */
    String getServerHost();

    /**
     * @return 服务监听端口
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
     * @return 服务端自定义参数映射
     */
    Map<String, String> getParams();
}
