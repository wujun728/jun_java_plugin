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
package net.ymate.platform.serv.nio.datagram;

import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.serv.IServModuleCfg;
import net.ymate.platform.serv.IServer;
import net.ymate.platform.serv.nio.INioCodec;
import net.ymate.platform.serv.nio.INioServerCfg;
import net.ymate.platform.serv.nio.INioSession;
import net.ymate.platform.serv.nio.server.NioServerCfg;
import net.ymate.platform.serv.nio.support.NioEventGroup;
import net.ymate.platform.serv.nio.support.NioEventProcessor;
import net.ymate.platform.serv.nio.support.NioSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/17 下午2:23
 * @version 1.0
 */
public class NioUdpServer implements IServer<NioUdpListener, INioCodec> {

    private static final Log _LOG = LogFactory.getLog(NioUdpServer.class);

    protected INioServerCfg __serverCfg;

    protected NioEventGroup<NioUdpListener> __eventGroup;

    protected NioUdpListener __listener;

    protected INioCodec __codec;

    protected boolean __isStarted;

    public void init(IServModuleCfg moduleCfg, String serverName, NioUdpListener listener, INioCodec codec) {
        __serverCfg = new NioServerCfg(moduleCfg, serverName);
        //
        __listener = listener;
        __codec = codec;
        __codec.init(__serverCfg.getCharset());
    }

    public synchronized void start() throws IOException {
        if (!__isStarted) {
            __isStarted = true;
            __eventGroup = new NioEventGroup<NioUdpListener>(__serverCfg, __listener, __codec) {
                @Override
                protected SelectableChannel __doChannelCreate(INioServerCfg cfg) throws IOException {
                    DatagramChannel _channel = DatagramChannel.open();
                    _channel.configureBlocking(false);
                    _channel.socket().bind(new InetSocketAddress(cfg.getServerHost(), cfg.getPort()));
                    return _channel;
                }

                @Override
                protected String __doBuildProcessorName() {
                    return StringUtils.capitalize(name()).concat("UdpServer-NioEventProcessor-");
                }

                @Override
                protected void __doInitProcessors() throws IOException {
                    __processors = new NioEventProcessor[__selectorCount];
                    for (int _idx = 0; _idx < __selectorCount; _idx++) {
                        __processors[_idx] = new NioEventProcessor<NioUdpListener>(this, __doBuildProcessorName() + _idx) {
                            @Override
                            protected void __doExceptionEvent(SelectionKey key, final Throwable e) {
                                final INioSession _session = (INioSession) key.attachment();
                                if (_session != null) {
                                    __eventGroup.executorService().submit(new Runnable() {
                                        public void run() {
                                            try {
                                                __eventGroup.listener().onExceptionCaught(e, _session);
                                            } catch (Throwable ex) {
                                                _LOG.error(e.getMessage(), RuntimeUtils.unwrapThrow(ex));
                                            }
                                        }
                                    });
                                } else {
                                    _LOG.error(e.getMessage(), RuntimeUtils.unwrapThrow(e));
                                }
                            }
                        };
                        __processors[_idx].start();
                    }
                }

                @Override
                protected void __doRegisterEvent() throws IOException {
                    for (NioEventProcessor _processor : __processors) {
                        _processor.registerEvent(__channel, SelectionKey.OP_READ, new NioSession<NioUdpListener>(this, __channel) {
                            @Override
                            protected int __doChannelRead(ByteBuffer buffer) throws IOException {
                                SocketAddress _address = ((DatagramChannel) __channel).receive(buffer);
                                if (_address != null) {
                                    attr(SocketAddress.class.getName(), _address);
                                    return __buffer.remaining();
                                }
                                return 0;
                            }

                            @Override
                            protected int __doChannelWrite(ByteBuffer buffer) throws IOException {
                                SocketAddress _address = attr(SocketAddress.class.getName());
                                if (_address != null) {
                                    return ((DatagramChannel) __channel).send(buffer, _address);
                                }
                                buffer.reset();
                                return 0;
                            }
                        });
                    }
                }
            };
            __eventGroup.start();
            //
            _LOG.info("UdpServer [" + __eventGroup.name() + "] started at " + __serverCfg.getServerHost() + ":" + __serverCfg.getPort());
        }
    }

    public boolean isStarted() {
        return __isStarted;
    }

    public INioServerCfg serverCfg() {
        return __serverCfg;
    }

    @SuppressWarnings("unchecked")
    public <T extends NioUdpListener> T listener() {
        return (T) __listener;
    }

    public synchronized void close() throws IOException {
        if (__isStarted) {
            __isStarted = false;
            __eventGroup.close();
        }
    }
}
