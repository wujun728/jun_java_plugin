package io.github.wujun728.db.orm.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Map Builder
 */
public class MapBuilder {

    private Map<String, Object> map = new HashMap<>();

    public MapBuilder add(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        return map;
    }
}
