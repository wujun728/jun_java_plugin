package com.tanghd.thrift.netty.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.UnsupportedMessageTypeException;

import java.util.List;

import org.apache.thrift.transport.TTransportException;

import com.tanghd.thrift.netty.common.message.TNettyThriftDef;
import com.tanghd.thrift.netty.common.message.TNettyThriftMessage;
import com.tanghd.thrift.netty.common.message.TNettyThriftMessageType;

public class TNettyThriftByteToMessageDecoder extends ByteToMessageDecoder {

    private static final String name = "BYTE_TO_MESSAGE_DECODER";

    private int maxReadBufferLenth = Integer.MAX_VALUE;

    public TNettyThriftByteToMessageDecoder(int maxReadBufferLenth) {
        this.maxReadBufferLenth = maxReadBufferLenth;
    }

    public TNettyThriftByteToMessageDecoder() {
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> msgList) throws Exception {
        Channel channel = ctx.channel();
        buf.markReaderIndex();
        int size = buf.readableBytes();
        if (0 >= size) {
            return;
        }
        TNettyThriftMessageType messageType = this.adjustMessageType(ctx, buf);
        ByteBuf inBuf = ctx.alloc().buffer(size);
        buf.resetReaderIndex();

        switch (messageType) {
        case FRAMED:
            this.decodeFramedMessage(buf, inBuf);
            break;
        case UNFRAMED:
            this.decodeUnFrameMessage(buf, inBuf);
            break;
        default:
            ctx.fireExceptionCaught(new Exception("read unkown type message"));
        }

        TNettyThriftMessage tmsg = new TNettyThriftMessage(channel, inBuf, ctx.alloc().buffer(1024), messageType);
        buf.clear();
        msgList.add(tmsg);
    }

    private TNettyThriftMessageType adjustMessageType(ChannelHandlerContext ctx, ByteBuf buf) {
        int size = buf.readInt();
        if (size < 0) {
            // unframe message
            return TNettyThriftMessageType.UNFRAMED;
        } else {
            // framed message
            return TNettyThriftMessageType.FRAMED;
        }
    }

    private void decodeFramedMessage(ByteBuf buf, ByteBuf newBuf) throws Exception {
        int frameSize = buf.readInt();
        if (frameSize < 0) {
            throw new TTransportException("Read a negative frame size (" + frameSize + ")!");
        }

        if (frameSize > maxReadBufferLenth) {
            throw new TTransportException("Frame size (" + frameSize + ") larger than max length ("
                    + maxReadBufferLenth + ")!");
        }

        buf.readBytes(newBuf, frameSize);
    }

    private void decodeUnFrameMessage(ByteBuf buf, ByteBuf newBuf) throws Exception {
        throw new UnsupportedMessageTypeException("no support for unframe message");
    }

    public static String getName() {
        return name;
    }

}
