package cn.skynethome.redisx.common;

/**
  * 项目名称:[redisx]
  * 包:[cn.skynethome.redisx.common]    
  * 文件名称:[JedisCallback]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2016年12月5日 下午6:05:42]   
  * 修改人:[陆文斌]   
  * 修改时间:[2016年12月5日 下午6:05:42]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public interface JedisCallback<I,O> {

	public O doInJedis(I jedis);
	
}
