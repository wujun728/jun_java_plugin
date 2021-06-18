/**
 * Copyright (c) 2015-2017, Winter Lau (javayou@gmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.oschina.j2cache.hibernate3;

import net.oschina.j2cache.CacheChannel;
import org.hibernate.cache.*;
import org.hibernate.cache.access.AccessType;
import org.hibernate.cache.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.access.EntityRegionAccessStrategy;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * J2Cache implements Hibernate Cache Region
 * @author Wujun
 */
public class J2CacheRegion implements GeneralDataRegion {
	
	private String regionName;
	private CacheChannel cache;
	
	public J2CacheRegion(String name, CacheChannel cache){
		this.regionName = name;
		this.cache = cache;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Region#destroy()
	 */
	@Override
	public void destroy() throws CacheException {
	}

    /* (non-Javadoc)
     * @see org.hibernate.cache.Region#getElementCountInMemory()
     */
	@Override
	public long getElementCountInMemory() {
		return -1;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Region#getElementCountOnDisk()
	 */
	@Override
	public long getElementCountOnDisk() {
		return -1;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Region#getName()
	 */
	@Override
	public String getName() {
		return regionName;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Region#getSizeInMemory()
	 */
	@Override
	public long getSizeInMemory() {
		return -1;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Region#getTimeout()
	 */
	@Override
	public int getTimeout() {
		return -1;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Region#nextTimestamp()
	 */
	@Override
	public long nextTimestamp() {
		return Timestamper.next();
	}

	/* (non-Javadoc)
	 * @see org.hibernate.cache.Region#toMap()
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Map toMap() {
        try {
            Map<Object, Object> result = new HashMap<Object, Object>();
            for (Object key : cache.keys(this.regionName)) {
                Object e = cache.get(this.regionName, (String)key);
                if (e != null) {
                    result.put(key, e);
                }
            }
            return result;
        } catch (Exception e) {
            throw new CacheException(e);
        }
	}

	@Override
	public void evict(Object key) throws CacheException {
		cache.evict(this.regionName, (String)key);
	}

	@Override
	public void evictAll() throws CacheException {
		cache.clear(this.regionName);
	}

	@Override
	public Object get(Object key) throws CacheException {
		return cache.get(this.regionName, (String)key);
	}

	@Override
	public void put(Object key, Object value) throws CacheException {
		cache.set(this.regionName, (String)key, value);
	}

	private static class Transactional extends J2CacheRegion implements TransactionalDataRegion {

		public Transactional(String name, CacheChannel cache) {
			super(name, cache);
		}

		@Override
		public CacheDataDescription getCacheDataDescription() {
			return null;
		}

		@Override
		public boolean isTransactionAware() {
			return false;
		}
		
	}
	
	final static class QueryResults extends J2CacheRegion implements QueryResultsRegion {
		public QueryResults(String name, CacheChannel cache) {
			super(name, cache);
		}
	}
	
	final static class Entity extends Transactional implements EntityRegion {

		public Entity(String name, CacheChannel cache) {
			super(name, cache);
		}

		@Override
		public EntityRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
			return null;
		}
		
	}
	
	final static class Collection extends Transactional implements CollectionRegion {

		public Collection(String name, CacheChannel cache) {
			super(name, cache);
		}

		@Override
		public CollectionRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
			return null;
		}
		
	}
	
	final static class Timestamps extends J2CacheRegion implements TimestampsRegion {
		public Timestamps(String name, CacheChannel cache) {
			super(name, cache);
		}
		
	}
	
}
