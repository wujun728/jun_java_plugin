package org.supercall.domainModel;

/**
 * Created by kira on 16/8/2.
 */
public class SelectorDataSource {
    private String key;

    private String value;

    public SelectorDataSource(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
