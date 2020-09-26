package com.redis.proxy.net.codec;

import com.redis.proxy.net.resps.RdsResp;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 
 * @author zhanggaofeng
 */
public class RedisCmdEncoder extends MessageToByteEncoder<RdsResp> {

        @Override
        public void encode(ChannelHandlerContext ctx, RdsResp msg, ByteBuf out) throws Exception {
                msg.write(out);
        }
}
