/*
 * @(#)MyDemuxingProtocolCodecFactory.java 2014-12-17 上午10:34:48
 * Copyright 2014 鲍建明, Inc. All rights reserved. 8637.com
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.myschool.mima.code;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

import com.myschool.mima.bean.AbstractMessage;

/**
 * <p>File：MyDemuxingProtocolCodecFactory.java</p>
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2014 2014-12-17 上午10:34:48</p>
 * <p>Company: 8637.com</p>
 * @author 鲍建明
 * @version 1.0
 */
public class MyDemuxingProtocolCodecFactory extends DemuxingProtocolCodecFactory
{
	public MyDemuxingProtocolCodecFactory(boolean server){
		if(server){
			super.addMessageDecoder(MyMessageDecoder.class);
		}else{
			super.addMessageEncoder(AbstractMessage.class, MyMessageEncoder.class);
		}
	}
}
