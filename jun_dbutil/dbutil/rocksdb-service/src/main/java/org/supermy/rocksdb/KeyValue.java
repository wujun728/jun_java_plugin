package org.supermy.rocksdb;

/**
 * Created by moyong on 17/5/20.
 */
public class KeyValue {
    public KeyValue(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    private String key;
    private Object value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }


}
