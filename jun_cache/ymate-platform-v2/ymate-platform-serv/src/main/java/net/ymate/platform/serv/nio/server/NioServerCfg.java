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
package net.ymate.platform.serv.nio.server;

import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.serv.IServModuleCfg;
import net.ymate.platform.serv.impl.DefaultServerCfg;
import net.ymate.platform.serv.nio.INioServerCfg;
import org.apache.commons.lang.StringUtils;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/15 下午7:55
 * @version 1.0
 */
public class NioServerCfg extends DefaultServerCfg implements INioServerCfg {

    private int __selectorCount;

    public NioServerCfg(IServModuleCfg moduleCfg, String serverName) {
        super(moduleCfg, serverName);
        //
        __selectorCount = BlurObject.bind(StringUtils.defaultIfBlank(moduleCfg.getServerCfg(getServerName()).get("selector_count"), "1")).toIntValue();
    }

    public int getSelectorCount() {
        return __selectorCount;
    }
}
