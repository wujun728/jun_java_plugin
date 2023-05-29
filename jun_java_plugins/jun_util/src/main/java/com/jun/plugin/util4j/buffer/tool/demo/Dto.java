package com.jun.plugin.util4j.buffer.tool.demo;

import com.jun.plugin.util4j.buffer.ByteBuffer;

public interface Dto {
	
	public void readFrom(ByteBuffer buff);
	
	public void writeTo(ByteBuffer buff);
}
