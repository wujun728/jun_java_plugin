/*
 * @(#)MyMessageDecoder.java 2014-12-17 上午10:36:14
 * Copyright 2014 鲍建明, Inc. All rights reserved. 8637.com
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.myschool.mima.code;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

/**
 * <p>File：MyMessageDecoder.java</p>
 * <p>Title: 解码类</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2014 2014-12-17 上午10:36:14</p>
 * <p>Company: 8637.com</p>
 * @author 鲍建明
 * @version 1.0
 */
public class MyMessageDecoder implements MessageDecoder
{

	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.demux.MessageDecoder#decodable(org.apache.mina.core.session.IoSession, org.apache.mina.core.buffer.IoBuffer)
	 *  是否适合解码
	 */
	@Override
	public MessageDecoderResult decodable(IoSession session, IoBuffer in)
	{
		int dataType = in.getInt();
		System.out.println("判断是否可以解码：--》" + dataType);
		if(dataType == 1){					//文件上传
			return MessageDecoderResult.OK;
		}else if(dataType == 2){			//对象
			return MessageDecoderResult.OK;
		}
		return MessageDecoderResult.NOT_OK;
	}

	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.demux.MessageDecoder#decode(org.apache.mina.core.session.IoSession, org.apache.mina.core.buffer.IoBuffer, org.apache.mina.filter.codec.ProtocolDecoderOutput)
	 * 数据解码
	 */
	@Override
	public MessageDecoderResult decode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception
	{
		System.out.println("开始解码：");
		System.out.println(in.getInt());
		return null;
	}

	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.demux.MessageDecoder#finishDecode(org.apache.mina.core.session.IoSession, org.apache.mina.filter.codec.ProtocolDecoderOutput)
	 */
	@Override
	public void finishDecode(IoSession session, ProtocolDecoderOutput out)
			throws Exception
	{
	}

}
