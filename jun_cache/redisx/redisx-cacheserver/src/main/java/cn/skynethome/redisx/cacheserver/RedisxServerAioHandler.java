package cn.skynethome.redisx.cacheserver;

import com.talent.aio.common.Aio;
import com.talent.aio.common.ChannelContext;
import com.talent.aio.server.intf.ServerAioHandler;

import cn.skynethome.redisx.common.Constants;
import cn.skynethome.redisx.common.RedisxAbsAioHandler;
import cn.skynethome.redisx.common.RedisxPacket;

/**
  * 项目名称:[redisx-cacheserver]
  * 包:[cn.skynethome.redisx.cacheserver]    
  * 文件名称:[RedisxServerAioHandler]  
  * 描述:[redisx server aio handler]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月12日 下午3:06:09]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月12日 下午3:06:09]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class RedisxServerAioHandler extends RedisxAbsAioHandler implements ServerAioHandler<Object, RedisxPacket, Object>
{
	/** 
	 * 处理消息
	 */
	@Override
	public Object handler(RedisxPacket packet, ChannelContext<Object, RedisxPacket, Object> channelContext) throws Exception
	{
	    
	    if(Constants.COMMAND_JOIN_GROUP==packet.getCommand())
	    {
	        Aio.bindGroup(channelContext, Constants.GROUPID);
	        return null;
	    }
	    else
	    {
	        byte[] body = packet.getBody();
	        if (body != null)
	        {
	            RedisxPacket resppacket = new RedisxPacket();
	            resppacket.setBody(body);
	            RedisxChannelContextFilter redisxChannelContextFilter = new RedisxChannelContextFilter(channelContext); 
	            Aio.sendToGroup(channelContext.getGroupContext(), Constants.GROUPID, resppacket,redisxChannelContextFilter);
	        }
	        return null;
	    }
	    
		
	}
	
	
}
