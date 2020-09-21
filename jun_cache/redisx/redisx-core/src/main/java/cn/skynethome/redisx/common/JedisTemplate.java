package cn.skynethome.redisx.common;

import redis.clients.util.Pool;
/**
  * 项目名称:[redisx]
  * 包:[cn.skynethome.redisx.common]    
  * 文件名称:[JedisTemplate]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2016年12月5日 下午6:05:50]   
  * 修改人:[陆文斌]   
  * 修改时间:[2016年12月5日 下午6:05:50]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class JedisTemplate<I> {

	private final Pool<I> jedisPool;
	
	public JedisTemplate(Pool<I> jedisPool){
		this.jedisPool = jedisPool;
	}
	
	protected I getResource(){
		return jedisPool.getResource();
	}
	
	protected void returnResource(I jedis){
		if(jedis != null){
			jedisPool.returnResource(jedis);
		}
	}
	
	public <O> O execute(JedisCallback<I,O> callback){
		if(callback != null){
			I jedis = null;
			try {
				jedis = getResource();
				return callback.doInJedis(jedis);
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}
	
}
