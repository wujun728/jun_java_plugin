package com.github.ghsea.rpc.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class Constants {
	public final static ByteBuf LINE_DELIMITER = Unpooled.wrappedBuffer(new byte[] { '\n' });
	
	public final static byte[] BYTE_LINE =new byte[] { '\n' };
}
