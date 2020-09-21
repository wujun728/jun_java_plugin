/**
 * **************************************************************************
 *
 * @说明: 
 * @项目名称: talent-aio-examples-server
 *
 * @author: tanyaowu 
 * @创建时间: 2016年11月18日 上午9:13:15
 *
 * **************************************************************************
 */
package cn.skynethome.redisx.common;

import org.apache.log4j.Logger;

import com.talent.aio.client.intf.ClientAioHandler;
import com.talent.aio.common.ChannelContext;

import cn.skynethome.redisx.j2cache.Cache;
import cn.skynethome.redisx.spring.RedisXImpl;
import cn.skynethome.redisx.spring.i.RedisX;

/**
  * 项目名称:[redisx-core]
  * 包:[cn.skynethome.redisx.common]    
  * 文件名称:[RedisXAioHandler]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月16日 下午4:58:02]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月16日 下午4:58:02]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class RedisXAioHandler extends RedisxAbsAioHandler implements ClientAioHandler<Object, RedisxPacket, Object>
{
    
    private Logger logger = Logger.getLogger(RedisXImpl.class);

    private Cache redisXCache;
    private RedisX redisX;
    
    
    public RedisXAioHandler(Cache redisXCache,RedisX redisX)
    {
        super();
        this.redisXCache = redisXCache;
        this.redisX = redisX;
    }
    
    public RedisXAioHandler()
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
		        if(!redisXCache.exists(redisXCacheBean.getKey()))//当且只当local ehcache获取过当前对象的时候才重新获取
		        {
		            logger.info("recevie a cmmand,the put action not  to  be save key {"+redisXCacheBean.getKey()+"} from local ehcache,because the local ehcache has never been");
		            
		        }else
		        {
		            
		            logger.info("recevie a cmmand,put action,re save key {"+redisXCacheBean.getKey()+"} from local ehcache.");
                    
		            if(Constants.STRING.equals(redisXCacheBean.getType()))
		            {
		                    String key = redisXCacheBean.getKey();
		                    redisXCache.evict(key);
	                        redisXCache.put(redisXCacheBean, redisX.getString(key));
	                       
		            }else if(Constants.OBJECT.equals(redisXCacheBean.getType()))
		            {
		                String key = redisXCacheBean.getKey();
		                redisXCache.evict(key); //移除本地key
	                    redisXCache.put(redisXCacheBean, redisX.getObject(key));//重新获取
  
		            }
		        }
		       
		        
		    }else if("DEL".equals(redisXCacheBean.getOption()))
		    {
		        String key = redisXCacheBean.getKey();
		        
		        if(redisXCache.exists(key))
		        {
		            logger.info("recevie a cmmand,del action,not del  key {"+redisXCacheBean.getKey()+"} from local ehcache,because it does not exist.");
		        }else
		        {
		            logger.info("recevie a cmmand,del action,del  key {"+redisXCacheBean.getKey()+"} from local ehcache.");
		            redisXCache.evict(key);
		        }
		        
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
