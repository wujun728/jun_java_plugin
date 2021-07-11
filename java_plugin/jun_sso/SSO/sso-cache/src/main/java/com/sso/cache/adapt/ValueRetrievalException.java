package com.sso.cache.adapt;

import java.util.concurrent.Callable;

/**
 * Created by cdr_c on 2016/8/16.
 * Wrapper exception to be thrown from {@link #get(Object, Callable)}
 * in case of the value loader callback failing with an exception.
 * @since 4.3
 */
public class ValueRetrievalException extends RuntimeException {

    private final Object key;

    public ValueRetrievalException(Object key, Callable<?> loader, Throwable ex) {
        super(String.format("Value for key '%s' could not be loaded using '%s'", key, loader), ex);
        this.key = key;
    }

    public Object getKey() {
        return this.key;
    }
}
