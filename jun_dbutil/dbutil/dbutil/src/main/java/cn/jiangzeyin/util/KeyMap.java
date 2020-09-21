package cn.jiangzeyin.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by jiangzeyin on 2017/8/15.
 */
public class KeyMap<K, V> {
    private Map<K, V> map;

    public KeyMap(Map<K, V> map) {
        Assert.notNull(map);
        this.map = map;
        Set<Map.Entry<K, V>> entries = this.map.entrySet();
        Iterator<Map.Entry<K, V>> iterator = entries.iterator();
        Map<K, V> temp = new HashMap<>();
        while (iterator.hasNext()) {
            Map.Entry<K, V> entry = iterator.next();
            String key = entry.getKey().toString();
            String newKey = key.toLowerCase();
            if (!key.equals(newKey)) {
                temp.put((K) newKey, entry.getValue());
                iterator.remove();
            }
        }
        map.putAll(temp);
    }

    public V get(K k) {
        return map.get(k);
    }
}
