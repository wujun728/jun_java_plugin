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
 * 服务模块配置接口
 *
 * @author 刘镇 (suninformation@163.com) on 15/10/15 上午10:26
 * @version 1.0
 */
public interface IServModuleCfg {

    /**
     * @param serverName 服务端名称
     * @return 返回指定名称的服务端配置
     */
    Map<String, String> getServerCfg(String serverName);

    /**
     * @param clientName 客户端名称
     * @return 返回指定名称的客户端配置
     */
    Map<String, String> getClientCfg(String clientName);
}
