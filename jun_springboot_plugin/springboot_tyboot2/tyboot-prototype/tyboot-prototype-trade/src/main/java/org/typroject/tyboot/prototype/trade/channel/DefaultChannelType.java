package org.typroject.tyboot.prototype.trade.channel;

/** 
 * 
 * <pre>
 *  Tyrest
 *  File: DefaultChannelType.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 * 
 *  Notes:
 *  $Id: DefaultChannelType.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月17日		magintrursh		Initial.
 *
 * </pre>
 */
public enum DefaultChannelType implements ChannelType {


	/**
	 * 支付渠道：PINGXX-支付宝
	 */
	PINGXX_ALIPAY("alipay","pingxxChannel"),
	/**
	 * 支付渠道：PINGXX-支付宝手机网页支付
	 */
	PINGXX_ALIPAY_WAP("alipay_wap","pingxxChannel"),
	
	/**
	 * 支付渠道：PINGXX-微信APP
	 */
	PINGXX_WX("wx","pingxxChannel"),
	/**
	 * 支付渠道：PINGXX-微信公众账号支付
	 */
	PINGXX_WX_PUB("wx_pub","pingxxChannel"),

	/**
	 * 支付渠道：PINGXX-微信小程序支付
	 */
	PINGXX_WX_XCX("wx_lite","pingxxChannel"),

	/**
	 * 支付渠道：现金
	 */
	CASH("cash","cashChannel"),
	
	/**
	 * 支付渠道：虚拟账户支付
	 */
	VIRTUAL("virtual","virtualChannel"),

	/**
	 * 苹果支付
	 */
	APPLE("apple","appleChannel"),
	
	/**
	 * 支付渠道：银行卡
	 */
	BANK_CARD("bank","cashChannel"),

		WX_APP("wx_app","wxChannel"),

	ALIPAY_APP("alipay_app","alipayChannel");
	
	
	private DefaultChannelType(String channel,String channelProcess)
	{
		this.channel = channel;
		this.channelProcess = channelProcess;
	}
	
	private String channel;
	
	private String channelProcess;
	
	
	public String getChannelProcess() {
		return channelProcess;
	}

	public String parseString()
	{
		String str = "";
		switch(this)
		{
			case PINGXX_ALIPAY : 
				str = "支付宝";
				break;
			case PINGXX_WX:
				str = "微信";
				break;
			case CASH:
				str = "现金";
				break;
			case VIRTUAL:
				str = "虚拟账户支付";
				break;
			default :
				str = "";
		}
		return str;
	}
	
	public static DefaultChannelType getInstance(String str)
	{
		DefaultChannelType channel = null;
		
		for(DefaultChannelType ut : DefaultChannelType.values())
		{
			if(ut.getChannel().equals(str))
			{
				channel = ut;
			}
		}
		
		return channel;
		
	}

	public String getChannel() {
		return channel;
	}

	
}

/*
*$Log: av-env.bat,v $
*/