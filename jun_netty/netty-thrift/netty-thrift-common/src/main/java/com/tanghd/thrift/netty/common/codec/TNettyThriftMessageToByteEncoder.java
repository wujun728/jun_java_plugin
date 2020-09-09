package com.tanghd.thrift.netty.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.tanghd.thrift.netty.common.message.TNettyThriftMessage;

public class TNettyThriftMessageToByteEncoder extends MessageToByteEncoder<TNettyThriftMessage> {
    private static final String name = "MESSAGE_TO_BYTE_ENCODER";
    
    @Override
    protected void encode(ChannelHandlerContext ctx, TNettyThriftMessage msg, ByteBuf out) throws Exception {
        ByteBuf outBuf = msg.getOutBuf();
        switch (msg.getMessageType()) {
        case FRAMED:
            this.encodeFrameMessage(outBuf, out);
            break;
        case UNFRAMED:
            break;
        default:
            break;
        }
    }
    
    private void encodeFrameMessage(ByteBuf outBuf,ByteBuf newBuf){
        int frameSize =outBuf.writerIndex();
        newBuf.writeInt(frameSize);
        newBuf.writeBytes(outBuf, 0, frameSize);
    }
    
    public static String getName(){
        return name;
    }

}
