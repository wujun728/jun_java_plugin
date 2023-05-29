package com.jun.plugin.resources.core.properties;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.jun.plugin.resources.Constants;
import com.jun.plugin.resources.db.DbUtils;
import com.jun.plugin.resources.db.select.Select;
import com.jun.plugin.resources.encrypt.ResourceEncrypt;

/**
 * Created By Hong on 2018/7/30
 **/
public class DbProperties extends Hashtable<Object, Object> {

    /**
     * 创建没有默认资源的空资源
     */
    public DbProperties() {
        super();
    }

    /***
     * 创建有默认资源的非空资源
     * @param defaults  默认
     */
    public DbProperties(Properties defaults) {
        super(defaults);
    }

    public synchronized Object setProperty(String key, String value) {
        return super.put(key, value);
    }

    public synchronized void load(String tableName) {
        String sql = new Select()
                .table(tableName)
                .columnAll()
                .toString();
        List<Map<String, Object>> list = DbUtils.select(sql);
        for (Map<String, Object> item : list) {
            this.put(item.get(Constants.DB_CONFIG_KEY), ResourceEncrypt.value(item.get(Constants.DB_CONFIG_VALUE)));
        }
    }

}
