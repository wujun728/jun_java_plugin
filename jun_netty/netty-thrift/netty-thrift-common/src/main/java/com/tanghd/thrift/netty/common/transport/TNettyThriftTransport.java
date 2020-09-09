package com.tanghd.thrift.netty.common.transport;

import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.tanghd.thrift.netty.common.message.TNettyThriftMessage;

public class TNettyThriftTransport extends TTransport {

    private TNettyThriftMessage thriftMessage;

    public TNettyThriftTransport(TNettyThriftMessage thriftMessage) {
        this.thriftMessage = thriftMessage;
    }

    @Override
    public boolean isOpen() {
        return thriftMessage.getChannel().isOpen();
    }

    @Override
    public void open() throws TTransportException {
        return;
    }

    @Override
    public void close() {
        return;
    }

    @Override
    public int read(byte[] buf, int off, int len) throws TTransportException {
        try {
            return thriftMessage.read(buf, off, len);
        } catch (Exception e) {
            throw new TTransportException(e);
        }
    }

    @Override
    public void write(byte[] buf, int off, int len) throws TTransportException {
        try {
            thriftMessage.write(buf, off, len);
        } catch (Exception e) {
            throw new TTransportException(e);
        }

    }

    @Override
    public boolean peek() {
        return thriftMessage.getBufferSize() > thriftMessage.getCurrentBufferPosition();
    }

    @Override
    public int readAll(byte[] buf, int off, int len) throws TTransportException {
        return this.read(buf, off, len);
    }

    @Override
    public void write(byte[] buf) throws TTransportException {
        this.write(buf, 0, buf.length);
    }

    @Override
    public void flush() throws TTransportException {
    }

    @Override
    public byte[] getBuffer() {
        return thriftMessage.getBuffer();
    }

    @Override
    public int getBufferPosition() {
        return thriftMessage.getCurrentBufferPosition();
    }

    @Override
    public int getBytesRemainingInBuffer() {
        return thriftMessage.getRemainBufferSize();
    }

    @Override
    public void consumeBuffer(int len) {
        thriftMessage.consumeBuffer(len);
    }

}
