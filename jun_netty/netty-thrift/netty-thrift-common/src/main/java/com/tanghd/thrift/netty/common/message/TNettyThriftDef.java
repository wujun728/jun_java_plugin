package com.tanghd.thrift.netty.common.message;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TTransportFactory;

public class TNettyThriftDef {

    protected TProcessorFactory processorFactory;

    protected TTransportFactory inputTransportFactory;
    protected TTransportFactory outputTransportFactory;
    protected TProtocolFactory inputProtocolFactory;
    protected TProtocolFactory outputProtocolFactory;

    public TProcessorFactory getProcessorFactory() {
        return processorFactory;
    }

    public void setProcessorFactory(TProcessorFactory processorFactory) {
        this.processorFactory = processorFactory;
    }

    public TTransportFactory getInputTransportFactory() {
        return inputTransportFactory;
    }

    public void setInputTransportFactory(TTransportFactory inputTransportFactory) {
        this.inputTransportFactory = inputTransportFactory;
    }

    public TTransportFactory getOutputTransportFactory() {
        return outputTransportFactory;
    }

    public void setOutputTransportFactory(TTransportFactory outputTransportFactory) {
        this.outputTransportFactory = outputTransportFactory;
    }

    public TProtocolFactory getInputProtocolFactory() {
        return inputProtocolFactory;
    }

    public void setInputProtocolFactory(TProtocolFactory inputProtocolFactory) {
        this.inputProtocolFactory = inputProtocolFactory;
    }

    public TProtocolFactory getOutputProtocolFactory() {
        return outputProtocolFactory;
    }

    public void setOutputProtocolFactory(TProtocolFactory outputProtocolFactory) {
        this.outputProtocolFactory = outputProtocolFactory;
    }
}
