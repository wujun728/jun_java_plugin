package com.jun.plugin.util4j.buffer;

import java.nio.charset.StandardCharsets;

public final class ByteBuffer extends ArrayBytesBuff
{
	public ByteBuffer() {
		super();
	}

	public ByteBuffer(byte[] data) {
		super(data);
	}

	public ByteBuffer(int capacity) {
		super(capacity);
	}

	public void writeUTF(String str)
	{
		if(str==null)
		{
			writeByte(0);
		}else
		{
			writeByte(1);
			byte[] data=str.getBytes(StandardCharsets.UTF_8);
			writeInt(data.length);
			writeBytes(data);
		}
	}
	
	public String readUTF()
	{
		String str=null;
		byte has=readByte();
		if(has!=0)
		{
			int len=readInt();
			byte[] data=new byte[len];
			readBytes(data);
			str=new String(data,StandardCharsets.UTF_8);
		}
		return str;
	}
}