package com.github.ghsea.rpc.client.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ghsea.rpc.core.message.RpcRequestMsg;
import com.github.ghsea.rpc.core.serialize.Serializer;
import com.github.ghsea.rpc.core.serialize.SerializerFactory;
import com.github.ghsea.rpc.server.constants.SerializerType;

public class RpcRequestMsgEncoder extends MessageToByteEncoder<RpcRequestMsg> {

	// TODO SPI
	private Serializer serializer = SerializerFactory.getSerializer(SerializerType.Hessian);
	private Logger log = LoggerFactory.getLogger(RpcRequestMsgEncoder.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, RpcRequestMsg msg, ByteBuf out) throws Exception {
		log.debug("Rpc Client is encoding RpcRequestMsg {}", msg);
		byte[] bytMsg = serializer.serialize(msg);
		log.debug("Rpc Client sent:{},length:{}", bytMsg, bytMsg.length);
		out.writeBytes(bytMsg);
	}

}
