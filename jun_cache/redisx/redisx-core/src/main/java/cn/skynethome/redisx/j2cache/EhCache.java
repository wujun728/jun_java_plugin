//$Id: EhCache.java 10716 2006-11-03 19:05:11Z max.andersen@jboss.com $
/**
 *  Copyright 2003-2006 Greg Luck, Jboss Inc
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cn.skynethome.redisx.j2cache;

import java.util.List;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

/**
  * 项目名称:[redisx-core]
  * 包:[cn.skynethome.redisx.j2cache]    
  * 文件名称:[EhCache]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月11日 上午9:41:19]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月11日 上午9:41:19]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class EhCache implements Cache, CacheEventListener {
	
	private net.sf.ehcache.Cache cache;
	private CacheExpiredListener listener;

	/**
	 * Creates a new Hibernate pluggable cache based on a cache name.
	 *
	 * @param cache The underlying EhCache instance to use.
	 * @param listener cache listener
	 */
	public EhCache(net.sf.ehcache.Cache cache, CacheExpiredListener listener) {
		this.cache = cache;
		this.cache.getCacheEventNotificationService().registerListener(this);
		this.listener = listener;
	}
	
	

	@SuppressWarnings("rawtypes")
	public List keys() throws CacheException {
		return this.cache.getKeys();
	}

	/**
	 * Gets a value of an element which matches the given key.
	 *
	 * @param key the key of the element to return.
	 * @return The value placed into the cache with an earlier put, or null if not found or expired
	 * @throws CacheException cache exception
	 */
	public Object get(Object key) throws CacheException {
		try {
			if ( key == null ) 
				return null;
			else {
                Element element = cache.get( key );
				if ( element != null )
					return element.getObjectValue();				
			}
			return null;
		}
		catch (net.sf.ehcache.CacheException e) {
			throw new CacheException( e );
		}
	}

	/**
	 * Puts an object into the cache.
	 *
	 * @param key   a key
	 * @param value a value
	 * @throws CacheException if the {@link CacheManager}
	 *                        is shutdown or another {@link Exception} occurs.
	 */
	public void update(Object key, Object value) throws CacheException {
		put( key, value );
	}

	/**
	 * Puts an object into the cache.
	 *
	 * @param key   a key
	 * @param value a value
	 * @throws CacheException if the {@link CacheManager}
	 *                        is shutdown or another {@link Exception} occurs.
	 */
	public void put(Object key, Object value) throws CacheException {
		try {
			Element element = new Element( key, value );
			cache.put( element );
		}
		catch (IllegalArgumentException e) {
			throw new CacheException( e );
		}
		catch (IllegalStateException e) {
			throw new CacheException( e );
		}
		catch (net.sf.ehcache.CacheException e) {
			throw new CacheException( e );
		}

	}

	/**
	 * Removes the element which matches the key
	 * If no element matches, nothing is removed and no Exception is thrown.
	 *
	 * @param key the key of the element to remove
	 * @throws CacheException cache exception
	 */
	@Override
	public void evict(Object key) throws CacheException {
		try {
			cache.remove( key );
		}
		catch (IllegalStateException e) {
			throw new CacheException( e );
		}
		catch (net.sf.ehcache.CacheException e) {
			throw new CacheException( e );
		}
	}

	/* (non-Javadoc)
	 * @see net.oschina.j2cache.Cache#batchRemove(java.util.List)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public void evict(List keys) throws CacheException {
		cache.removeAll(keys);
	}

	/**
	 * Remove all elements in the cache, but leave the cache
	 * in a useable state.
	 *
	 * @throws CacheException cache exception
	 */
	public void clear() throws CacheException {
		try {
			cache.removeAll();
		}
		catch (IllegalStateException e) {
			throw new CacheException( e );
		}
		catch (net.sf.ehcache.CacheException e) {
			throw new CacheException( e );
		}
	}

	/**
	 * Remove the cache and make it unuseable.
	 *
	 * @throws CacheException  cache exception
	 */
	public void destroy() throws CacheException {
		try {
			cache.getCacheManager().removeCache( cache.getName() );
		}
		catch (IllegalStateException e) {
			throw new CacheException( e );
		}
		catch (net.sf.ehcache.CacheException e) {
			throw new CacheException( e );
		}
	}
	
	public Object clone() throws CloneNotSupportedException { 
		throw new CloneNotSupportedException(); 
	}

	@Override
	public void dispose() {}

	@Override
	public void notifyElementEvicted(Ehcache arg0, Element arg1) {}

	@Override
	public void notifyElementExpired(Ehcache cache, Element elem) {
		if(listener != null){
			listener.notifyElementExpired(cache.getName(), elem.getObjectKey());
		}
	}

	@Override
	public void notifyElementPut(Ehcache arg0, Element arg1) throws net.sf.ehcache.CacheException {}

	@Override
	public void notifyElementRemoved(Ehcache arg0, Element arg1) throws net.sf.ehcache.CacheException {}

	@Override
	public void notifyElementUpdated(Ehcache arg0, Element arg1) throws net.sf.ehcache.CacheException {}

	@Override
	public void notifyRemoveAll(Ehcache arg0) {}



    @Override
    public boolean exists(Object key)
    {
        // TODO Auto-generated method stub
        return this.cache.isKeyInCache(key); 
    }

}