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

import net.ymate.platform.serv.IServModuleCfg;
import net.ymate.platform.serv.IServer;
import net.ymate.platform.serv.nio.INioCodec;
import net.ymate.platform.serv.nio.INioServerCfg;
import net.ymate.platform.serv.nio.support.NioEventGroup;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/15 下午6:22
 * @version 1.0
 */
public class NioServer implements IServer<NioServerListener, INioCodec> {

    private static final Log _LOG = LogFactory.getLog(NioServer.class);

    protected INioServerCfg __serverCfg;

    protected NioEventGroup<NioServerListener> __eventGroup;

    protected NioServerListener __listener;

    protected INioCodec __codec;

    protected boolean __isStarted;

    public void init(IServModuleCfg moduleCfg, String serverName, NioServerListener listener, INioCodec codec) {
        __serverCfg = new NioServerCfg(moduleCfg, serverName);
        //
        __listener = listener;
        __codec = codec;
        __codec.init(__serverCfg.getCharset());
    }

    public synchronized void start() throws IOException {
        if (!__isStarted) {
            __isStarted = true;
            __eventGroup = new NioEventGroup<NioServerListener>(__serverCfg, __listener, __codec);
            __eventGroup.start();
            //
            _LOG.info("Server [" + __eventGroup.name() + "] started at " + __serverCfg.getServerHost() + ":" + __serverCfg.getPort());
        }
    }

    public boolean isStarted() {
        return __isStarted;
    }

    public INioServerCfg serverCfg() {
        return __serverCfg;
    }

    @SuppressWarnings("unchecked")
    public <T extends NioServerListener> T listener() {
        return (T) __listener;
    }

    public synchronized void close() throws IOException {
        if (__isStarted) {
            __isStarted = false;
            __eventGroup.close();
        }
    }
}
