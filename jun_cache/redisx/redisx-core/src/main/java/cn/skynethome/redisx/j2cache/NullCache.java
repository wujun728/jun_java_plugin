package cn.skynethome.redisx.j2cache;

import java.util.List;

/**
  * 项目名称:[redisx-core]
  * 包:[cn.skynethome.redisx.j2cache]    
  * 文件名称:[NullCache]  
  * 描述:[空的缓存Provide]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月11日 上午9:41:53]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月11日 上午9:41:53]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class NullCache implements Cache {

	/* (non-Javadoc)
	 * @see net.oschina.j2cache.Cache#get(java.lang.Object)
	 */
	@Override
	public Object get(Object key) throws CacheException {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.oschina.j2cache.Cache#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void put(Object key, Object value) throws CacheException {
	}

	/* (non-Javadoc)
	 * @see net.oschina.j2cache.Cache#update(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void update(Object key, Object value) throws CacheException {
	}

	/* (non-Javadoc)
	 * @see net.oschina.j2cache.Cache#keys()
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List keys() throws CacheException {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.oschina.j2cache.Cache#remove(java.lang.Object)
	 */
	@Override
	public void evict(Object key) throws CacheException {
	}

	/* (non-Javadoc)
	 * @see net.oschina.j2cache.Cache#batchRemove(java.util.List)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public void evict(List keys) throws CacheException {	
	}

	/* (non-Javadoc)
	 * @see net.oschina.j2cache.Cache#clear()
	 */
	@Override
	public void clear() throws CacheException {
	}

	/* (non-Javadoc)
	 * @see net.oschina.j2cache.Cache#destroy()
	 */
	@Override
	public void destroy() throws CacheException {
	}

	@Override
    public boolean exists(Object key)
    {
        // TODO Auto-generated method stub
        return false;
    }

}
