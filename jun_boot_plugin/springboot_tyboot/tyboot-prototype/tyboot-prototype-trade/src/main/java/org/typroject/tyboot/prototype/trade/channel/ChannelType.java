package org.typroject.tyboot.prototype.trade.channel;

public interface ChannelType {

	
	/**
	 * 交易渠道标识
	 * @return
	 */
	String getChannel();
	
	
	/**
	 * 交易渠道名称
	 * @return
	 */
	String parseString();
	
	
	/**
	 * 交易渠道实现类beanName
	 * @return
	 */
	String getChannelProcess();
}
