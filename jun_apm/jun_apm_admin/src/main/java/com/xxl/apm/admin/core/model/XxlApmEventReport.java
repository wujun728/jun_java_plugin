package com.xxl.apm.admin.core.model;

/**
 * @author xuxueli 2019-01-17
 */
public class XxlApmEventReport {

    private long id;

    private String appname;
    private long addtime;
    private String address;
    private String hostname;

    private String type;
    private String name;

    private long total_count;
    private long fail_count;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotal_count() {
        return total_count;
    }

    public void setTotal_count(long total_count) {
        this.total_count = total_count;
    }

    public long getFail_count() {
        return fail_count;
    }

    public void setFail_count(long fail_count) {
        this.fail_count = fail_count;
    }

}
