package cn.skynethome.redisx.cacheserver;

import com.talent.aio.common.ChannelContext;
import com.talent.aio.common.ChannelContextFilter;

import cn.skynethome.redisx.common.RedisxPacket;

/**
  * 项目名称:[redisx-cacheserver]
  * 包:[cn.skynethome.redisx.cacheserver]    
  * 文件名称:[RedisxChannelContextFilter]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月16日 下午5:00:17]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月16日 下午5:00:17]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class RedisxChannelContextFilter implements ChannelContextFilter<Object, RedisxPacket, Object>
{
    ChannelContext<Object, RedisxPacket, Object> channelContext = null;
    
    public RedisxChannelContextFilter()
    {
        
    }

    public RedisxChannelContextFilter(ChannelContext<Object, RedisxPacket, Object> channelContext)
    {
        this.channelContext = channelContext;
    }
    
    @Override
    public boolean filter(ChannelContext<Object, RedisxPacket, Object> channelContext)
    {
        // TODO Auto-generated method stub
        if(null != channelContext && channelContext.equals(this.channelContext))
        {
            return false;
            
        }else
        {
            return true;
        }
    }

}
