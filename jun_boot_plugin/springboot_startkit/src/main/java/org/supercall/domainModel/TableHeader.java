package org.supercall.domainModel;

/**
 * Created by kira on 16/7/31.
 */
public class TableHeader {
    private String name;
    private String styleName;
    private String key;
    public TableHeader(String name, String styleName, String key) {
        this.name = name;
        this.styleName = styleName;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
