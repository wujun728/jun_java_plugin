package com.jun.plugin.dbtracer.xml;

import java.util.HashMap;
import java.util.Map;

import com.jun.plugin.dbtracer.Util;

public class TableConfiguration {

    private String name;

    /**
     * 主键
     */
    private String key;
    private Map<String, TableField> filedName2Config;

    public TableConfiguration() {
        filedName2Config = new HashMap<String, TableField>();
    }

    public Map<String, TableField> getFiledName2Config() {
        return filedName2Config;
    }

    public void setFiledName2Config(Map<String, TableField> filedName2Config) {
        this.filedName2Config = filedName2Config;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("name=").append(name).append(",key=").append(key).append(",filedName2Config=")
                .append(Util.toString(filedName2Config));
        return sb.toString();
    }

}
