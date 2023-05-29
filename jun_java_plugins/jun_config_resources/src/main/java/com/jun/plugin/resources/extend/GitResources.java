package com.jun.plugin.resources.extend;

import java.io.InputStream;
import java.util.Map;

import com.jun.plugin.resources.core.AbstractResources;
import com.jun.plugin.resources.core.properties.GitProperties;

/**
 * 
 * Created By Hong on 2018/8/13
 **/
public final class GitResources extends AbstractResources {

    public void load() {
        GitProperties properties = new GitProperties();
        properties.load();
        map.putAll(properties);
    }

    public void load(String... name) {
        GitProperties properties = new GitProperties();
        properties.load(name);
        map.putAll(properties);
    }

    @Override
    public void load(InputStream is) {

    }

    @Override
    public Map<Object, Object> getConfig() {
        return map;
    }

    @Override
    public void clear() {
        map.clear();
    }
}
