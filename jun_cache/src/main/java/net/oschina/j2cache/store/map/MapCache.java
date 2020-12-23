package net.oschina.j2cache.store.map;

import net.oschina.j2cache.Cache;
import net.oschina.j2cache.CacheException;

import java.util.List;

/**
 * @author Wujun
 */
public class MapCache implements Cache {



    @Override
    public Object get(Object key) throws CacheException {
        return null;
    }

    @Override
    public void put(Object key, Object value) throws CacheException {

    }

    @Override
    public void evict(Object key) throws CacheException {

    }

    @Override
    public void evict(List keys) throws CacheException {

    }

    @Override
    public List keys() throws CacheException {
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public void destroy() throws CacheException {

    }
}
