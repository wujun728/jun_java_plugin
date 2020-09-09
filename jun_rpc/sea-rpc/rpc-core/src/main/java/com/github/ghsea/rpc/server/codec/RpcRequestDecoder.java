package com.github.ghsea.rpc.server.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ghsea.rpc.core.serialize.Serializer;
import com.github.ghsea.rpc.core.serialize.SerializerFactory;
import com.github.ghsea.rpc.server.constants.SerializerType;

public class RpcRequestDecoder extends ByteToMessageDecoder {

	// TODO SPI
	private Serializer serializer = SerializerFactory.getSerializer(SerializerType.Hessian);

	private Logger log = LoggerFactory.getLogger(RpcRequestDecoder.class);

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int length = in.readableBytes();
		byte[] byt = new byte[length];
		in.readBytes(byt);

		log.debug("Rpc Server received:{}", byt);
		Object requestObj = serializer.deserialize(byt);
		log.debug("Rpc Server received:{}", requestObj);
		out.add(requestObj);
	}

}
