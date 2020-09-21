package cn.skynethome.redisx.j2cache;

import java.util.Properties;

/**
  * 项目名称:[redisx-core]
  * 包:[cn.skynethome.redisx.j2cache]    
  * 文件名称:[NullCacheProvider]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月11日 上午9:42:10]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月11日 上午9:42:10]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class NullCacheProvider implements CacheProvider {

	private final static NullCache cache = new NullCache();

	@Override
	public String name() {
		return "none";
	}

	/* (non-Javadoc)
	 * @see net.oschina.j2cache.CacheProvider#buildCache(java.lang.String, boolean, net.oschina.j2cache.CacheExpiredListener)
	 */
	@Override
	public Cache buildCache(String regionName, boolean autoCreate,
			CacheExpiredListener listener) throws CacheException {
		return cache;
	}

	/* (non-Javadoc)
	 * @see net.oschina.j2cache.CacheProvider#start()
	 */
	@Override
	public void start(Properties props) throws CacheException {
	}

	/* (non-Javadoc)
	 * @see net.oschina.j2cache.CacheProvider#stop()
	 */
	@Override
	public void stop() {
	}

}
