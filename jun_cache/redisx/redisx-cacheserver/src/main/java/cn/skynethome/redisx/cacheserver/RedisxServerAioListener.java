package cn.skynethome.redisx.cacheserver;

import com.talent.aio.common.ChannelContext;
import com.talent.aio.server.intf.ServerAioListener;

import cn.skynethome.redisx.common.RedisxPacket;

/**
  * 项目名称:[redisx-cacheserver]
  * 包:[cn.skynethome.redisx.cacheserver]    
  * 文件名称:[RedisxServerAioListener]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月16日 下午5:00:36]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月16日 下午5:00:36]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class RedisxServerAioListener implements ServerAioListener<Object, RedisxPacket, Object>
{

	/** 
	 * @see com.talent.aio.common.intf.AioListener#onAfterConnected(com.talent.aio.common.ChannelContext, boolean, boolean)
	 * 
	 * @param channelContext
	 * @param isConnected
	 * @param isReconnect
	 * @throws Exception
	 * @重写人: tanyaowu
	 * @重写时间: 2017年2月15日 下午5:50:47
	 * 
	 */
	@Override
	public void onAfterConnected(ChannelContext<Object, RedisxPacket, Object> channelContext, boolean isConnected, boolean isReconnect) throws Exception
	{
		// TODO Auto-generated method stub
		
	}

	/** 
	 * @see com.talent.aio.common.intf.AioListener#onAfterSent(com.talent.aio.common.ChannelContext, com.talent.aio.common.intf.Packet, boolean)
	 * 
	 * @param channelContext
	 * @param packet
	 * @param isSentSuccess
	 * @throws Exception
	 * @重写人: tanyaowu
	 * @重写时间: 2017年2月15日 下午5:50:47
	 * 
	 */
	@Override
	public void onAfterSent(ChannelContext<Object, RedisxPacket, Object> channelContext, RedisxPacket packet, boolean isSentSuccess) throws Exception
	{
		// TODO Auto-generated method stub
		
	}

	/** 
	 * @see com.talent.aio.common.intf.AioListener#onAfterReceived(com.talent.aio.common.ChannelContext, com.talent.aio.common.intf.Packet, int)
	 * 
	 * @param channelContext
	 * @param packet
	 * @param packetSize
	 * @throws Exception
	 * @重写人: tanyaowu
	 * @重写时间: 2017年2月15日 下午5:50:47
	 * 
	 */
	@Override
	public void onAfterReceived(ChannelContext<Object, RedisxPacket, Object> channelContext, RedisxPacket packet, int packetSize) throws Exception
	{
		// TODO Auto-generated method stub
		
	}

	/** 
	 * @see com.talent.aio.common.intf.AioListener#onAfterClose(com.talent.aio.common.ChannelContext, java.lang.Throwable, java.lang.String, boolean)
	 * 
	 * @param channelContext
	 * @param throwable
	 * @param remark
	 * @param isRemove
	 * @throws Exception
	 * @重写人: tanyaowu
	 * @重写时间: 2017年2月15日 下午5:50:47
	 * 
	 */
	@Override
	public void onAfterClose(ChannelContext<Object, RedisxPacket, Object> channelContext, Throwable throwable, String remark, boolean isRemove) throws Exception
	{
		// TODO Auto-generated method stub
		
	}

   

    

}
