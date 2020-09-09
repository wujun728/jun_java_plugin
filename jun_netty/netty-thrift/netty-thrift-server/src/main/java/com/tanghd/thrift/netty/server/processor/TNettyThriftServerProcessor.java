package com.tanghd.thrift.netty.server.processor;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tanghd.thrift.netty.common.message.TNettyThriftDef;
import com.tanghd.thrift.netty.common.transport.TNettyThriftTransport;
import com.tanghd.thrift.netty.server.server.TNettyThriftServerDef;

public class TNettyThriftServerProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(TNettyThriftServerProcessor.class);

    private TProcessor processor = null;
    private TTransport inputTransport = null;
    private TTransport outputTransport = null;
    private TProtocol inputProtocol = null;
    private TProtocol outputProtocol = null;

    private TNettyThriftTransport client;

    private ServerContext serverContext;

    private TNettyThriftServerDef serverDef;

    public TNettyThriftServerProcessor(TNettyThriftServerDef serverDef, TNettyThriftTransport client_) {
        this.serverDef = serverDef;
        this.client = client_;
        processor = serverDef.getProcessorFactory().getProcessor(client);
        inputTransport = serverDef.getInputTransportFactory().getTransport(client);
        outputTransport = serverDef.getOutputTransportFactory().getTransport(client);
        inputProtocol = serverDef.getInputProtocolFactory().getProtocol(inputTransport);
        outputProtocol = serverDef.getOutputProtocolFactory().getProtocol(outputTransport);
        if (null != serverDef.getEventHandler()) {
            serverContext = serverDef.getEventHandler().createContext(inputProtocol, outputProtocol);
        }
    }

    public void invoke() {
        try {
            if (null != serverDef.getEventHandler()) {
                serverDef.getEventHandler().processContext(serverContext, inputTransport, outputTransport);
            }
            processor.process(inputProtocol, outputProtocol);

            return;
        } catch (Exception e) {
            LOG.error("process error :", e);
        }

        if (serverDef.getEventHandler() != null) {
            serverDef.getEventHandler().deleteContext(serverContext, inputProtocol, outputProtocol);
        }

    }
}
