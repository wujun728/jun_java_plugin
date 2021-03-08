package com.gosalelab.generator;

import com.gosalelab.constants.CacheConstants;
import com.gosalelab.exception.CacheKeyGeneratorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * @author Wujun
 */
@Component
@ConditionalOnProperty(prefix = CacheConstants.ASPECT_CACHE_PREFIX, name = "enable", havingValue = "true")
public class CacheKeyGeneratorHolder {

    private final Map<String, KeyGenerator> keyGenerators;

    @Autowired
    public CacheKeyGeneratorHolder(Map<String, KeyGenerator> keyGenerators) {
        this.keyGenerators = keyGenerators;
    }

    public KeyGenerator findKeyGenerator(String type) {

        String name = type.toLowerCase() + KeyGenerator.class.getSimpleName();

        KeyGenerator generator = keyGenerators.get(name);

        if (generator == null) {
            throw new CacheKeyGeneratorException("KeyGenerator:" + name + " does not exist.");
        }

        return generator;
    }
}
