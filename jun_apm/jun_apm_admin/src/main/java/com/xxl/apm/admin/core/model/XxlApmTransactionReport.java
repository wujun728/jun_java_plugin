package com.xxl.apm.admin.core.model;

/**
 * @author xuxueli 2019-01-17
 */
public class XxlApmTransactionReport {

    private long id;

    private String appname;
    private long addtime;
    private String address;
    private String hostname;

    private String type;
    private String name;

    private long total_count;
    private long fail_count;

    private long time_max;   // 机器维度精确，ALL维度按照 total_count（min_address_x_time * min_address_x_count.. / all_count） 加权汇总；
    private long time_avg;
    private long time_tp90;
    private long time_tp95;
    private long time_tp99;
    private long time_tp999;


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

    public long getTime_max() {
        return time_max;
    }

    public void setTime_max(long time_max) {
        this.time_max = time_max;
    }

    public long getTime_avg() {
        return time_avg;
    }

    public void setTime_avg(long time_avg) {
        this.time_avg = time_avg;
    }

    public long getTime_tp90() {
        return time_tp90;
    }

    public void setTime_tp90(long time_tp90) {
        this.time_tp90 = time_tp90;
    }

    public long getTime_tp95() {
        return time_tp95;
    }

    public void setTime_tp95(long time_tp95) {
        this.time_tp95 = time_tp95;
    }

    public long getTime_tp99() {
        return time_tp99;
    }

    public void setTime_tp99(long time_tp99) {
        this.time_tp99 = time_tp99;
    }

    public long getTime_tp999() {
        return time_tp999;
    }

    public void setTime_tp999(long time_tp999) {
        this.time_tp999 = time_tp999;
    }
}
