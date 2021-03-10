/*
 * @(#)MyMessageEncoder.java 2014-12-17 上午10:36:31
 * Copyright 2014 鲍建明, Inc. All rights reserved. 8637.com
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.myschool.mima.code;

import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import com.myschool.mima.bean.AbstractMessage;

/**
 * <p>File：MyMessageEncoder.java</p>
 * <p>Title: 编码类</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2014 2014-12-17 上午10:36:31</p>
 * <p>Company: 8637.com</p>
 * @author 鲍建明
 * @version 1.0
 */
public class MyMessageEncoder implements MessageEncoder<AbstractMessage>
{

	private CharsetDecoder decoder;
	private String charset;
	
	/*private MyMessageEncoder(String charset){
		this.charset = charset;
		decoder = Charset.forName(charset).newDecoder();
	}*/
	

	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.demux.MessageEncoder#encode(org.apache.mina.core.session.IoSession, java.lang.Object, org.apache.mina.filter.codec.ProtocolEncoderOutput)
	 */
	@Override
	public void encode(IoSession session, AbstractMessage message,
			ProtocolEncoderOutput out) throws Exception
	{
	/*	IoBuffer buffer = IoBuffer.allocate(1024*1024*50); 
		FileBean bean = (FileBean) message.getData();
		byte[] byteStr = bean.getFileName().getBytes("UTF-8");
		buffer.putInt(byteStr.length);
		buffer.putInt(bean.getFileSize());
		buffer.put(byteStr);
		buffer.put(bean.getFileContent());
		buffer.flip();
		out.write(buffer);*/
		System.out.println("编码完成！");
	}

}
