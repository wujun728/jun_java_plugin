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

import net.ymate.platform.core.Version;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.module.annotation.Module;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.serv.annotation.Client;
import net.ymate.platform.serv.annotation.Server;
import net.ymate.platform.serv.handle.ClientHandler;
import net.ymate.platform.serv.handle.ServerHandler;
import net.ymate.platform.serv.impl.DefaultModuleCfg;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务模块管理器
 *
 * @author 刘镇 (suninformation@163.com) on 15/10/15 上午10:22
 * @version 1.0
 */
@Module
public class Servs implements IModule, IServ {

    public static final Version VERSION = new Version(2, 0, 3, Servs.class.getPackage().getImplementationVersion(), Version.VersionType.Release);

    private static final Log _LOG = LogFactory.getLog(Servs.class);

    private static volatile IServ __instance;

    private YMP __owner;

    private IServModuleCfg __moduleCfg;

    private boolean __inited;

    private Map<String, IServer> __servers;

    private Map<String, IClient> __clients;

    /**
     * @return 返回默认服务模块管理器实例对象
     */
    public static IServ get() {
        if (__instance == null) {
            synchronized (VERSION) {
                if (__instance == null) {
                    __instance = YMP.get().getModule(Servs.class);
                }
            }
        }
        return __instance;
    }

    /**
     * @param owner YMP框架管理器实例
     * @return 返回指定YMP框架管理器容器内的服务模块实例
     */
    public static IServ get(YMP owner) {
        return owner.getModule(Servs.class);
    }

    public Servs() {
        __servers = new ConcurrentHashMap<String, IServer>();
        __clients = new ConcurrentHashMap<String, IClient>();
    }

    @Override
    public String getName() {
        return IServ.MODULE_NAME;
    }

    @Override
    public void init(YMP owner) throws Exception {
        if (!__inited) {
            //
            _LOG.info("Initializing ymate-platform-serv-" + VERSION);
            //
            __owner = owner;
            __moduleCfg = new DefaultModuleCfg(owner);
            //
            __owner.registerExcludedClass(IServer.class);
            __owner.registerExcludedClass(IServerCfg.class);
            __owner.registerExcludedClass(IClient.class);
            __owner.registerExcludedClass(IClientCfg.class);
            __owner.registerExcludedClass(ICodec.class);
            __owner.registerExcludedClass(IListener.class);
            //
            __owner.registerHandler(Server.class, new ServerHandler(this));
            __owner.registerHandler(Client.class, new ClientHandler(this));
            //
            __inited = true;
        }
    }

    @Override
    public boolean isInited() {
        return __inited;
    }

    @Override
    public YMP getOwner() {
        return __owner;
    }

    @Override
    public IServModuleCfg getModuleCfg() {
        return __moduleCfg;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends IServer> T getServer(Class<? extends IListener> clazz) {
        return (T) __servers.get(clazz.getName());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends IClient> T getClient(Class<? extends IListener> clazz) {
        return (T) __clients.get(clazz.getName());
    }

    @Override
    public void registerServer(Class<? extends IListener> listenerClass) throws Exception {
        Server _annoServer = listenerClass.getAnnotation(Server.class);
        if (_annoServer == null) {
            throw new IllegalArgumentException("No Server annotation present on class");
        }
        registerServer(_annoServer.name(), _annoServer.implClass(), _annoServer.codec(), listenerClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void registerServer(String serverName, Class<? extends IServer> implClass, Class<? extends ICodec> codec, Class<? extends IListener> listenerClass) throws Exception {
        if (!__servers.containsKey(listenerClass.getName())) {
            IServer _server = ClassUtils.impl(implClass, IServer.class);
            _server.init(__moduleCfg, serverName, ClassUtils.impl(listenerClass, IListener.class), ClassUtils.impl(codec, ICodec.class));
            __servers.put(listenerClass.getName(), _server);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void registerClient(Class<? extends IListener> listenerClass) throws Exception {
        Client _annoClient = listenerClass.getAnnotation(Client.class);
        if (_annoClient == null) {
            throw new IllegalArgumentException("No Client annotation present on class");
        }
        registerClient(_annoClient.name(), _annoClient.implClass(), _annoClient.codec(), listenerClass, _annoClient.reconnectClass(), _annoClient.hearbeatClass());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void registerClient(String clientName, Class<? extends IClient> implClass, Class<? extends ICodec> codec, Class<? extends IListener> listenerClass, Class<? extends IReconnectService> reconnectClass, Class<? extends IHeartbeatService> hearbeatClass) throws Exception {
        if (!__clients.containsKey(listenerClass.getName())) {
            IClient _client = ClassUtils.impl(implClass, IClient.class);
            //
            IReconnectService _reconnectService = null;
            if (!IReconnectService.NONE.class.equals(reconnectClass)) {
                _reconnectService = ClassUtils.impl(reconnectClass, IReconnectService.class);
                _reconnectService.init(_client);
            }
            IHeartbeatService _heartbeatService = null;
            if (!IHeartbeatService.NONE.class.equals(hearbeatClass)) {
                _heartbeatService = ClassUtils.impl(hearbeatClass, IHeartbeatService.class);
                _heartbeatService.init(_client);
            }
            //
            _client.init(__moduleCfg, clientName, ClassUtils.impl(listenerClass, IListener.class), ClassUtils.impl(codec, ICodec.class), _reconnectService, _heartbeatService);
            __clients.put(listenerClass.getName(), _client);
        }
    }

    @Override
    public void startup() throws Exception {
        for (IServer _server : __servers.values()) {
            if (!_server.isStarted()) {
                _server.start();
            }
        }
        //
        for (IClient _client : __clients.values()) {
            if (!_client.isConnected()) {
                _client.connect();
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        if (__inited) {
            __inited = false;
            //
            for (IClient _client : __clients.values()) {
                if (_client.isConnected()) {
                    _client.close();
                }
            }
            __clients = null;
            //
            for (IServer _server : __servers.values()) {
                if (_server.isStarted()) {
                    _server.close();
                }
            }
            __servers = null;
            //
            __moduleCfg = null;
            __owner = null;
        }
    }
}
