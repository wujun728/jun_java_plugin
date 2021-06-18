package net.oschina.j2cache.springcache;

import net.oschina.j2cache.CacheChannel;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.AbstractValueAdaptingCache;

import java.util.concurrent.Callable;

/**
 * @author Wujun
 */
public class J2CacheSpringCacheAdapter extends AbstractValueAdaptingCache {
    private final String name;
    private final CacheChannel j2Cache;
    private boolean allowNullValues;

    /**
     * Create an {@code AbstractValueAdaptingCache} with the given setting.
     *
     * @param allowNullValues whether to allow for {@code null} values
     */
    protected J2CacheSpringCacheAdapter(boolean allowNullValues, CacheChannel j2Cache, String name) {
        super(allowNullValues);
        this.allowNullValues = allowNullValues;
        this.name = name;
        this.j2Cache = j2Cache;
    }

    /**
     * Perform an actual lookup in the underlying store.
     *
     * @param key the key whose associated value is to be returned
     * @return the raw store value for the key
     */

    private static String getKey(Object key) {
        return key.toString();
    }

    @Override
    protected Object lookup(Object key) {
        return j2Cache.get(name, getKey(key)).getValue();
    }

    /**
     * Return the cache name.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Return the underlying native cache provider.
     */
    @Override
    public Object getNativeCache() {
        return j2Cache;
    }

    /**
     * Return the value to which this cache maps the specified key, obtaining
     * that value from {@code valueLoader} if necessary. This method provides
     * a simple substitute for the conventional "if cached, return; otherwise
     * create, cache and return" pattern.
     * <p>If possible, implementations should ensure that the loading operation
     * is synchronized so that the specified {@code valueLoader} is only called
     * once in case of concurrent access on the same key.
     * <p>If the {@code valueLoader} throws an exception, it is wrapped in
     * a {@link ValueRetrievalException}
     *
     * @param key         the key whose associated value is to be returned
     * @param valueLoader
     * @return the value to which this cache maps the specified key
     * @throws ValueRetrievalException if the {@code valueLoader} throws an exception
     * @since 4.3
     */
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) throws ValueRetrievalException {
        ValueWrapper val = get(key);
        if (val != null) {
            return (T) val.get();
        }
        T t;
        try {
            t = valueLoader.call();
        } catch (Exception e) {
            throw new ValueRetrievalException(key, valueLoader, e);
        }
        this.put(key, t);
        return t;
    }

    /**
     * Associate the specified value with the specified key in this cache.
     * <p>If the cache previously contained a mapping for this key, the old
     * value is replaced by the specified value.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     */
    @Override
    public void put(Object key, Object value) {
        j2Cache.set(name, getKey(key), value, allowNullValues);
    }

    /**
     * Atomically associate the specified value with the specified key in this cache
     * if it is not set already.
     * <p>This is equivalent to:
     * <pre><code>
     * Object existingValue = cache.get(key);
     * if (existingValue == null) {
     *     cache.put(key, value);
     *     return null;
     * } else {
     *     return existingValue;
     * }
     * </code></pre>
     * except that the action is performed atomically. While all out-of-the-box
     * {@link CacheManager} implementations are able to perform the put atomically,
     * the operation may also be implemented in two steps, e.g. with a check for
     * presence and a subsequent put, in a non-atomic way. Check the documentation
     * of the native cache implementation that you are using for more details.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @return the value to which this cache maps the specified key (which may be
     * {@code null} itself), or also {@code null} if the cache did not contain any
     * mapping for that key prior to this call. Returning {@code null} is therefore
     * an indicator that the given {@code value} has been associated with the key.
     * @since 4.1
     */
    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        ValueWrapper existingValue = get(getKey(key));
        if (existingValue == null) {
            put(key, value);
            return null;
        } else {
            return existingValue;
        }

    }

    /**
     * Evict the mapping for this key from this cache if it is present.
     *
     * @param key the key whose mapping is to be removed from the cache
     */
    @Override
    public void evict(Object key) {
        j2Cache.evict(name, getKey(key));
    }

    /**
     * Remove all mappings from the cache.
     */
    @Override
    public void clear() {
        j2Cache.clear(name);
    }
}
