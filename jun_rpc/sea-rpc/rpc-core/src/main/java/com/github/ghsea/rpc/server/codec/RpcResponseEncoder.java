package com.github.ghsea.rpc.server.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ghsea.rpc.core.Constants;
import com.github.ghsea.rpc.core.message.RpcResponseMsg;
import com.github.ghsea.rpc.core.serialize.Serializer;
import com.github.ghsea.rpc.core.serialize.SerializerFactory;
import com.github.ghsea.rpc.server.constants.SerializerType;

public class RpcResponseEncoder extends MessageToByteEncoder<RpcResponseMsg> {

	// TODO SPI
	private Serializer serializer = SerializerFactory.getSerializer(SerializerType.Hessian);

	private Logger log = LoggerFactory.getLogger(RpcRequestDecoder.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, RpcResponseMsg msg, ByteBuf out) throws Exception {
		byte[] byt = serializer.serialize(msg);
		log.debug("Server is encoding response msg {}.", byt);
		out.writeBytes(byt);
		out.writeBytes(Constants.LINE_DELIMITER);
	}

}
