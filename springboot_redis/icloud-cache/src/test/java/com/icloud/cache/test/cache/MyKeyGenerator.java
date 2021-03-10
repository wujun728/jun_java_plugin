package com.icloud.cache.test.cache;

import com.icloud.cache.constants.CacheScope;
import com.icloud.cache.parser.IKeyGenerator;
import com.icloud.cache.parser.IUserKeyGenerator;

/**
 * ${DESCRIPTION}
 *
 * @author Wujun
 * @create 2018-05-22 14:05
 */
public class MyKeyGenerator extends IKeyGenerator {
    @Override
    public IUserKeyGenerator getUserKeyGenerator() {
        return null;
    }

    @Override
    public String buildKey(String key, CacheScope scope, Class<?>[] parameterTypes, Object[] arguments) {
        return "myKey_"+arguments[0];
    }
}
