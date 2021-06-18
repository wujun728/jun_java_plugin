package com.roncoo.jui.common.entity;

import java.io.Serializable;
import java.util.Date;

public class WebSiteUrl implements Serializable {
    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String statusId;

    private Integer sort;

    private Long siteId;

    private String urlName;

    private String urlDesc;

    private String inNet;

    private String outNet;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId == null ? null : statusId.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName == null ? null : urlName.trim();
    }

    public String getUrlDesc() {
        return urlDesc;
    }

    public void setUrlDesc(String urlDesc) {
        this.urlDesc = urlDesc == null ? null : urlDesc.trim();
    }

    public String getInNet() {
        return inNet;
    }

    public void setInNet(String inNet) {
        this.inNet = inNet == null ? null : inNet.trim();
    }

    public String getOutNet() {
        return outNet;
    }

    public void setOutNet(String outNet) {
        this.outNet = outNet == null ? null : outNet.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", statusId=").append(statusId);
        sb.append(", sort=").append(sort);
        sb.append(", siteId=").append(siteId);
        sb.append(", urlName=").append(urlName);
        sb.append(", urlDesc=").append(urlDesc);
        sb.append(", inNet=").append(inNet);
        sb.append(", outNet=").append(outNet);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}