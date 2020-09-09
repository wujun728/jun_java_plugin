package com.tanghd.thrift.netty.server.server;

import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tanghd.thrift.netty.common.message.TNettyThriftDef;
import com.tanghd.thrift.netty.server.transport.TNettyThriftServerTransport;

public class TNettyThriftServer extends TServer {

    private static final Logger LOG = LoggerFactory.getLogger(TNettyThriftServer.class);

    public static class Args extends AbstractServerArgs<Args> {

        private int port;
        private int maxReadBuffer;
        private int clientTimeout;

        public Args(int port) {
            super(new TNettyThriftServerTransport(port));
            this.clientTimeout = 0;
        }

        public Args(int port, int timeout) {
            super(new TNettyThriftServerTransport(port, timeout));
            this.clientTimeout = timeout;
        }

        public int getPort() {
            return port;
        }

        public int getMaxReadBuffer() {
            return maxReadBuffer;
        }

        public void setMaxReadBuffer(int maxReadBuffer) {
            this.maxReadBuffer = maxReadBuffer;
        }

        public int getClientTimeout() {
            return clientTimeout;
        }
    }

    private Args args;

    private TNettyThriftServerDef serverDef;

    private static final int DEFAULT_MAX_READ_LENGTH = Integer.MAX_VALUE;

    public TNettyThriftServer(Args args) {
        super(args);
        this.args = args;
    }

    @Override
    public void serve() {
        int maxReadSize = args.getMaxReadBuffer();
        if (0 >= maxReadSize) {
            maxReadSize = DEFAULT_MAX_READ_LENGTH;
        }
        args.setMaxReadBuffer(maxReadSize);

        serverDef = new TNettyThriftServerDef();
        serverDef.setEventHandler(eventHandler_);
        serverDef.setInputProtocolFactory(inputProtocolFactory_);
        serverDef.setInputTransportFactory(inputTransportFactory_);
        serverDef.setOutputProtocolFactory(outputProtocolFactory_);
        serverDef.setOutputTransportFactory(outputTransportFactory_);
        serverDef.setProcessorFactory(processorFactory_);
        serverDef.setMaxReadBufferLength(args.getMaxReadBuffer());

        TNettyThriftServerTransport serverTransport = (TNettyThriftServerTransport) serverTransport_;
        serverTransport.setServerDef(serverDef);
        try {
            serverTransport_.listen();
        } catch (TTransportException e) {
            LOG.error("Netty Server Start error", e);
            return;
        }
    }

    public Args getArgs() {
        return args;
    }

    public void setArgs(Args args) {
        this.args = args;
    }

    public TNettyThriftDef getServerDef() {
        return serverDef;
    }

    public void setServerDef(TNettyThriftServerDef serverDef) {
        this.serverDef = serverDef;
    }
}
