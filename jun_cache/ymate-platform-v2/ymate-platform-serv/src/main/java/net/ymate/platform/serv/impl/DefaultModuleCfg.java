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
package net.ymate.platform.serv.impl;

import net.ymate.platform.core.YMP;
import net.ymate.platform.serv.IServ;
import net.ymate.platform.serv.IServModuleCfg;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务模块配置类
 *
 * @author 刘镇 (suninformation@163.com) on 15/10/15 上午10:27
 * @version 1.0
 */
public class DefaultModuleCfg implements IServModuleCfg {

    private Map<String, Map<String, String>> __serverCfgs;

    private Map<String, Map<String, String>> __clientCfgs;

    public DefaultModuleCfg(YMP owner) throws Exception {
        Map<String, String> _moduleCfgs = owner.getConfig().getModuleConfigs(IServ.MODULE_NAME);
        //
        String[] _serverNames = StringUtils.split(StringUtils.defaultIfBlank(_moduleCfgs.get("server.name_list"), "default"), "|");
        __serverCfgs = new HashMap<String, Map<String, String>>(_serverNames.length);
        for (String _name : _serverNames) {
            __doConfigMapLoad("server", _name, _moduleCfgs, __serverCfgs);
        }
        //
        String[] _clientNames = StringUtils.split(StringUtils.defaultIfBlank(_moduleCfgs.get("client.name_list"), "default"), "|");
        __clientCfgs = new HashMap<String, Map<String, String>>(_clientNames.length);
        for (String _name : _clientNames) {
            __doConfigMapLoad("client", _name, _moduleCfgs, __clientCfgs);
        }
    }

    private void __doConfigMapLoad(String prefix, String name, Map<String, String> sources, Map<String, Map<String, String>> dst) {
        Map<String, String> _cfgs = new HashMap<String, String>();
        String _key = prefix.concat(".").concat(name).concat(".");
        for (Map.Entry<String, String> _entry : sources.entrySet()) {
            if (_entry.getKey().startsWith(_key)) {
                String _paramKey = StringUtils.substring(_entry.getKey(), _key.length());
                _cfgs.put(_paramKey, _entry.getValue());
            }
        }
        dst.put(name, _cfgs);
    }

    public Map<String, String> getServerCfg(String serverName) {
        Map<String, String> _serverMap = __serverCfgs.get(serverName);
        if (_serverMap != null) {
            return Collections.unmodifiableMap(__serverCfgs.get(serverName));
        }
        return Collections.emptyMap();
    }

    public Map<String, String> getClientCfg(String clientName) {
        Map<String, String> _clientMap = __clientCfgs.get(clientName);
        if (_clientMap != null) {
            return Collections.unmodifiableMap(__clientCfgs.get(clientName));
        }
        return Collections.emptyMap();
    }
}
