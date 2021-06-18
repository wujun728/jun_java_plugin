package org.supercall.domainModel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by kira on 16/7/31.
 */
public class FilterLists {
    private String name;
    private String key;
    private String type;
    private String value;
    private Object datasources;


    public FilterLists(String name, String key, String type, String value, Object datasources) {
        this.name = name;
        this.key = key;
        this.type = type;
        this.value = value;
        this.datasources = datasources;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getDatasources() {
        return datasources;
    }

    public void setDatasources(Object datasources) {
        this.datasources = datasources;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
