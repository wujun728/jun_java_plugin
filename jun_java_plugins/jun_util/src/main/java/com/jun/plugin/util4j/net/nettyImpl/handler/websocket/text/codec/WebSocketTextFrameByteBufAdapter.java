package com.jun.plugin.util4j.net.nettyImpl.handler.websocket.text.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.internal.logging.InternalLogger;

import java.util.List;

import com.jun.plugin.util4j.net.nettyImpl.NetLogFactory;

/**
 * UTF8格式：
 * public String text() {
        return content().toString(CharsetUtil.UTF_8);
    }
 * TextWebSocketFrame消息和byteBuf消息直接的转换
 * 用于避免业务层直接发送byteBuf类型的消息:{@code 
 * @Override
 *	public void sendData(byte[] data) {
 *		ByteBuf buf=ByteBufUtil.threadLocalDirectBuffer();
 *		buf.writeBytes(data);
 *		ctx.writeAndFlush(buf);
 *	}}
 * 
 * @author Administrator
 */
public class WebSocketTextFrameByteBufAdapter extends MessageToMessageCodec<WebSocketFrame, ByteBuf>{
	protected final InternalLogger log = NetLogFactory.getLogger(getClass());
	/**
	 * 将webSocket消息转换为bytebuf类型,以适配后面的解码器
	 */
	@Override
	protected void decode(ChannelHandlerContext paramChannelHandlerContext,
			WebSocketFrame paramINBOUND_IN, List<Object> paramList)
			throws Exception {
		if(paramINBOUND_IN instanceof TextWebSocketFrame)
		{
			TextWebSocketFrame msg=(TextWebSocketFrame)paramINBOUND_IN;
			ByteBuf data = msg.content();
			paramList.add(data);
			data.retain();
			log.debug("TextWebSocketFrame to ByteBuf,size="+data.readableBytes());
		}
	}

	/**
	 * 对于业务层直接发送的bytebuf实例将其转换为websocket消息
	 */
	@Override
	protected void encode(ChannelHandlerContext paramChannelHandlerContext,
			ByteBuf paramOUTBOUND_IN, List<Object> paramList) throws Exception {
		paramList.add(new TextWebSocketFrame(paramOUTBOUND_IN));
		paramOUTBOUND_IN.retain();
		log.debug("ByteBuf to TextWebSocketFrame,size="+paramOUTBOUND_IN.readableBytes());
	}
}
