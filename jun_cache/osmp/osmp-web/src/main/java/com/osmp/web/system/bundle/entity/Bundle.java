package com.osmp.web.system.bundle.entity;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年11月19日 下午2:19:44
 */
public class Bundle {

    private String id;

    private String name;

    private boolean fragment;

    private int stateRaw;

    private String state;

    private String version;

    private String symbolicName;

    private String category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFragment() {
        return fragment;
    }

    public void setFragment(boolean fragment) {
        this.fragment = fragment;
    }

    public int getStateRaw() {
        return stateRaw;
    }

    public void setStateRaw(int stateRaw) {
        this.stateRaw = stateRaw;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSymbolicName() {
        return symbolicName;
    }

    public void setSymbolicName(String symbolicName) {
        this.symbolicName = symbolicName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Bundle [id=" + id + ", name=" + name + ", fragment=" + fragment + ", stateRaw="
                + stateRaw + ", state=" + state + ", version=" + version + ", symbolicName="
                + symbolicName + ", category=" + category + "]";
    }

}
