package cn.skynethome.redisx.cacheclient;

import com.talent.aio.client.intf.ClientAioHandler;
import com.talent.aio.common.ChannelContext;

import cn.skynethome.redisx.common.RedisXCacheBean;
import cn.skynethome.redisx.common.RedisxAbsAioHandler;
import cn.skynethome.redisx.common.RedisxPacket;
import cn.skynethome.redisx.common.SerializationAndCompressUtils;

/**
  * 项目名称:[redisx-cacheclient]
  * 包:[cn.skynethome.redisx.cacheclient]    
  * 文件名称:[RedisXSharedSentinelAioHandler]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月16日 下午4:59:50]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月16日 下午4:59:50]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class RedisXSharedSentinelAioHandler extends RedisxAbsAioHandler implements ClientAioHandler<Object, RedisxPacket, Object>
{
    
   
    public RedisXSharedSentinelAioHandler()
    {
        super();
    }
    
	/** 
	 * 处理消息
	 */
	@Override
	public Object handler(RedisxPacket packet, ChannelContext<Object, RedisxPacket, Object> channelContext) throws Exception
	{
		byte[] body = packet.getBody();
		if (body != null)
		{
		    RedisXCacheBean redisXCacheBean = (RedisXCacheBean)SerializationAndCompressUtils.fastDeserialize(body);
		    
		    if("PUT".equals(redisXCacheBean.getOption()))
		    {
		        System.out.println("xxxx");
		        
		    }else if("DEL".equals(redisXCacheBean.getOption()))
		    {
		        System.out.println("bbb");
		    }
		}

		return null;
	}

	private static RedisxPacket heartbeatPacket = new RedisxPacket();

	/** 
	 * 此方法如果返回null，框架层面则不会发心跳；如果返回非null，框架层面会定时发本方法返回的消息包
	 */
	@Override
	public RedisxPacket heartbeatPacket()
	{
		return heartbeatPacket;
	}
}
