package net.oschina.j2cache.store.ehcache;

import net.oschina.j2cache.Cache;
import net.oschina.j2cache.CacheException;
import net.oschina.j2cache.CacheExpiredListener;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

import java.util.List;

/**
 * 缓存的EhCache实现
 *
 * @author Wujun
 */
public class EhCache implements Cache, CacheEventListener {

    private final net.sf.ehcache.Cache cache;
    private final CacheExpiredListener listener;

    public EhCache(net.sf.ehcache.Cache cache, CacheExpiredListener listener) {
        this.cache = cache;
        this.cache.getCacheEventNotificationService().registerListener(this);
        this.listener = listener;
    }

    @Override
    public Object get(Object key) throws CacheException {
        try {
            if (key == null) {
                return null;
            } else {
                Element element = cache.get(key);
                if (element != null) {
                    return element.getObjectValue();
                }
                return null;
            }
        } catch (net.sf.ehcache.CacheException e) {
            throw new CacheException(e);
        }
    }

    @Override
    public void put(Object key, Object value) throws CacheException {
        try {
            Element element = new Element(key, value);
            cache.put(element);
        }
        catch (IllegalArgumentException | net.sf.ehcache.CacheException | IllegalStateException e) {
            throw new CacheException(e);
        }
    }

    @Override
    public void evict(Object key) throws CacheException {
        try {
            cache.remove(key);
        }
        catch (IllegalStateException | net.sf.ehcache.CacheException e) {
            throw new CacheException(e);
        }
    }

    @Override
    public void evict(List keys) throws CacheException {
        cache.removeAll(keys);
    }

    @Override
    public List keys() throws CacheException {
        return cache.getKeys();
    }

    @Override
    public void clear() throws CacheException {
        try {
            cache.removeAll();
        } catch (net.sf.ehcache.CacheException e) {
            throw new CacheException(e);
        }

    }

    @Override
    public void destroy() throws CacheException {
        try {
            cache.getCacheManager().removeCache(cache.getName());
        }
        catch (IllegalStateException | net.sf.ehcache.CacheException e) {
            throw new CacheException(e);
        }
    }

    // ---------------------------------
    /** 重写来自 {@link CacheEventListener} 的时间通知方法，用以实现自定义缓存策略 */
    // ---------------------------------

    @Override
    public void notifyElementRemoved(Ehcache ehcache, Element element) throws net.sf.ehcache.CacheException {

    }

    @Override
    public void notifyElementPut(Ehcache ehcache, Element element) throws net.sf.ehcache.CacheException {

    }

    @Override
    public void notifyElementUpdated(Ehcache ehcache, Element element) throws net.sf.ehcache.CacheException {

    }

    /**
     * 缓存是过期时的通知，在这里实现集群广播
     * @param ehcache {@link EhCache}
     * @param element {@link Element}
     */
    @Override
    public void notifyElementExpired(Ehcache ehcache, Element element) {
        if (listener != null) {
            listener.notifyElementExpired(ehcache.getName(), element.getObjectKey());
        }
    }

    @Override
    public void notifyElementEvicted(Ehcache ehcache, Element element) {

    }

    @Override
    public void notifyRemoveAll(Ehcache ehcache) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
