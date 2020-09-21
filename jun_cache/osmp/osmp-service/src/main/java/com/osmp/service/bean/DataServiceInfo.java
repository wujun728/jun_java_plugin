/*   
 * Project: OSMP
 * FileName: DataServiceInfo.java
 * version: V1.0
 */
package com.osmp.service.bean;

import java.util.Date;

import com.osmp.jdbc.define.Column;
import com.osmp.service.util.ServiceUtil;

public class DataServiceInfo {
    @Column(mapField="id",name="id")
    private int id;
    private String bundle;
    private String version;
    private String name;
    private int state;
    private String mark;
    private Date updateTime;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getBundle() {
        return bundle;
    }
    public void setBundle(String bundle) {
        this.bundle = bundle;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public String getMark() {
        return mark;
    }
    public void setMark(String mark) {
        this.mark = mark;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public String generateServiceName(){
        return ServiceUtil.generateServiceName(bundle, version, name);
    }
}
