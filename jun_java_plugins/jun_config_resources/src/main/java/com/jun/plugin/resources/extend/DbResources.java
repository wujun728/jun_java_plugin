package com.jun.plugin.resources.extend;

import java.io.InputStream;
import java.util.Map;

import com.jun.plugin.resources.KeyConstants;
import com.jun.plugin.resources.config.GlobalConfig;
import com.jun.plugin.resources.core.AbstractResources;
import com.jun.plugin.resources.core.properties.DbProperties;
import com.jun.plugin.resources.encrypt.ResourceEncrypt;
import com.jun.plugin.resources.utils.ResourcesUtils;

/**
 * 
 * Created By Hong on 2018/7/30
 **/
public final class DbResources extends AbstractResources {

    public void load() {
        DbProperties properties = new DbProperties();
        properties.load(GlobalConfig.get().getValue(KeyConstants.DB_TABLE_NAME));
        map.putAll(ResourceEncrypt.value(properties));
    }

    public void load(String tableName) {
        DbProperties properties = new DbProperties();
        properties.load(tableName);
        map.putAll(ResourceEncrypt.value(properties));
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
