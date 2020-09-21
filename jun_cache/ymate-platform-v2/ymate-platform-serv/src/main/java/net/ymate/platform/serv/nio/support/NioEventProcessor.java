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
package net.ymate.platform.serv.nio.support;

import net.ymate.platform.core.util.DateTimeUtils;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.serv.IListener;
import net.ymate.platform.serv.ISession;
import net.ymate.platform.serv.nio.INioSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/9 上午9:28
 * @version 1.0
 */
public class NioEventProcessor<LISTENER extends IListener<INioSession>> extends Thread {

    private static final Log _LOG = LogFactory.getLog(NioEventProcessor.class);

    private final NioEventGroup<LISTENER> __eventGroup;

    protected Selector __selector;

    protected boolean __flag;

    private final Queue<Object[]> __eventQueues = new LinkedBlockingQueue<Object[]>();
    private final Queue<INioSession> __closeQueues = new LinkedBlockingQueue<INioSession>();

    public NioEventProcessor(NioEventGroup<LISTENER> eventGroup, String name) throws IOException {
        super(name);
        __flag = true;
        __eventGroup = eventGroup;
        __selector = Selector.open();
    }

    @Override
    public void run() {
        try {
            while (__flag) {
                __selector.select(5 * DateTimeUtils.SECOND);
                __doRegisterEvent();
                Iterator<SelectionKey> _keyIterator = __selector.selectedKeys().iterator();
                while (_keyIterator.hasNext()) {
                    SelectionKey _selectionKey = _keyIterator.next();
                    _keyIterator.remove();
                    if (_selectionKey.isValid()) {
                        Object _attachment = _selectionKey.attachment();
                        if (_attachment instanceof INioSession) {
                            ((INioSession) _attachment).touch();
                        }
                        try {
                            if (_selectionKey.isAcceptable()) {
                                __doAcceptEvent(_selectionKey);
                            } else if (_selectionKey.isConnectable()) {
                                __doConnectEvent(_selectionKey);
                            } else if (_selectionKey.isReadable()) {
                                __doReadEvent(_selectionKey);
                            } else if (_selectionKey.isWritable()) {
                                __doWriteEvent(_selectionKey);
                            }
                        } catch (Throwable e) {
                            __doExceptionEvent(_selectionKey, e);
                        }
                    }
                }
                __doClosedEvent();
            }
        } catch (IOException e) {
            if (__flag) {
                _LOG.error(e.getMessage(), RuntimeUtils.unwrapThrow(e));
            } else {
                _LOG.debug(e.getMessage(), RuntimeUtils.unwrapThrow(e));
            }
        }
    }

    @Override
    public void interrupt() {
        try {
            __flag = false;
            join();
            __selector.close();
        } catch (Exception e) {
            _LOG.error(e.getMessage(), RuntimeUtils.unwrapThrow(e));
        }
        super.interrupt();
    }

    public void registerEvent(SelectableChannel channel, int ops, INioSession session) throws IOException {
        if (Thread.currentThread() == this) {
            SelectionKey key = channel.register(__selector, ops, session);
            if (session != null) {
                session.selectionKey(key);
                session.status(ISession.Status.CONNECTED);
                //
                __eventGroup.listener().onSessionRegisted(session);
            }
        } else {
            __eventQueues.offer(new Object[]{channel, ops, session});
            __selector.wakeup();
        }
    }

    public void unregisterEvent(INioSession session) {
        if (__closeQueues.contains(session)) {
            return;
        }
        __closeQueues.add(session);
        __selector.wakeup();
    }

    protected void __doRegisterEvent() {
        Object[] _event;
        while ((_event = __eventQueues.poll()) != null) {
            try {
                SelectableChannel _channel = (SelectableChannel) _event[0];
                if (!_channel.isOpen()) {
                    continue;
                }
                INioSession _session = (INioSession) _event[2];
                SelectionKey _key = _channel.register(__selector, (Integer) _event[1], _session);
                if (_session != null) {
                    _session.selectionKey(_key);
                    _session.status(ISession.Status.CONNECTED);
                    //
                    __eventGroup.listener().onSessionRegisted(_session);
                }
            } catch (Exception e) {
                _LOG.error(e.getMessage(), RuntimeUtils.unwrapThrow(e));
            }
        }
    }

    protected void __doClosedEvent() {
        INioSession _session;
        while ((_session = __closeQueues.poll()) != null) {
            try {
                _session.closeNow();
            } catch (IOException e) {
                _LOG.error(e.getMessage(), RuntimeUtils.unwrapThrow(e));
            }
        }
    }

    protected void __doExceptionEvent(final SelectionKey key, final Throwable e) {
        final INioSession _session = (INioSession) key.attachment();
        if (_session == null) {
            try {
                key.channel().close();
                key.cancel();
            } catch (Throwable ex) {
                _LOG.error(e.getMessage(), RuntimeUtils.unwrapThrow(ex));
            }
        } else {
            _session.status(ISession.Status.ERROR);
        }
        __eventGroup.executorService().submit(new Runnable() {
            public void run() {
                try {
                    __eventGroup.listener().onExceptionCaught(e, _session);
                } catch (Throwable ex) {
                    _LOG.error(e.getMessage(), RuntimeUtils.unwrapThrow(ex));
                }
            }
        });
        if (_session != null) {
            try {
                _session.close();
            } catch (Throwable ex) {
                _LOG.error(e.getMessage(), RuntimeUtils.unwrapThrow(ex));
            }
        }
    }

    protected void __doAcceptEvent(SelectionKey key) throws IOException {
        SocketChannel _channel = ((ServerSocketChannel) key.channel()).accept();
        _channel.configureBlocking(false);
        INioSession _session = new NioSession<LISTENER>(__eventGroup, _channel);
        _session.selectionKey(key);
        _session.status(ISession.Status.CONNECTED);
        __eventGroup.listener().onSessionAccepted(_session);
    }

    protected void __doConnectEvent(SelectionKey key) throws IOException {
        INioSession _session = (INioSession) key.attachment();
        if (_session != null) {
            SocketChannel _channel = (SocketChannel) key.interestOps(0).channel();
            if (_channel.finishConnect()) {
                _session.finishConnect();
            }
            _session.selectionKey(key);
            _session.status(ISession.Status.CONNECTED);
            __eventGroup.listener().onSessionConnected(_session);
        }
    }

    protected void __doReadEvent(SelectionKey key) throws IOException {
        final INioSession _session = (INioSession) key.attachment();
        if (_session != null && _session.isConnected()) {
            _session.read();
        }
    }

    protected void __doWriteEvent(SelectionKey key) throws IOException {
        INioSession _session = (INioSession) key.attachment();
        if (_session != null && _session.isConnected()) {
            _session.write();
        }
    }
}
