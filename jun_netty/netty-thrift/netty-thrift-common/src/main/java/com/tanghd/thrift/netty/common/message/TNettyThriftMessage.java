package com.tanghd.thrift.netty.common.message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

public class TNettyThriftMessage {
    private Channel channel;
    private final byte[] buffer;
    private final int bufferSize;
    private int currentBufferPosition;
    private ByteBuf outBuf;
    private ByteBuf inBuf;

    private final TNettyThriftMessageType messageType;

    public TNettyThriftMessage(Channel channel, ByteBuf inBuf, ByteBuf outBuf,TNettyThriftMessageType messageType) {
        this.inBuf = inBuf;
        bufferSize = inBuf.readableBytes();
        this.buffer = new byte[bufferSize];
        inBuf.readBytes(buffer);
        this.channel = channel;
        this.outBuf = outBuf;
        this.inBuf.resetReaderIndex();
        this.messageType = messageType;
    }

    public Byte readByte() {
        if (currentBufferPosition == bufferSize) {
            return null;
        }
        byte t = inBuf.readByte();
        currentBufferPosition++;
        return t;
    }

    public int read(byte[] buf, int off, int len) {
        int remain = this.getRemainBufferSize();
        if (remain <= 0) {
            return 0;
        }
        if (remain < len) {
            len = remain;
        }
        inBuf.readBytes(buf, off, len);
        currentBufferPosition += len;
        return len;
    }

    public void write(byte[] buf, int off, int len) {
        outBuf.writeBytes(buf, off, len);
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public ByteBuf getOutBuf() {
        return outBuf;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public int getCurrentBufferPosition() {
        return currentBufferPosition;
    }

    public int getWriterIndex() {
        return this.outBuf.writerIndex();
    }

    public int getRemainBufferSize() {
        if (currentBufferPosition >= bufferSize) {
            return 0;
        }
        return bufferSize - currentBufferPosition;
    }

    public void resetReaderIndex() {
        inBuf.resetReaderIndex();
    }

    public void consumeBuffer(int len) {
        if (currentBufferPosition + len > bufferSize) {
            len = bufferSize - currentBufferPosition;
            currentBufferPosition = bufferSize;
        }else{
            currentBufferPosition += len;
        }
        if (len > 0) {
            inBuf.skipBytes(len);
        }
    }

    public TNettyThriftMessageType getMessageType() {
        return messageType;
    }
    
    public ByteBuf getInBuf(){
        return this.inBuf;
    }

}
