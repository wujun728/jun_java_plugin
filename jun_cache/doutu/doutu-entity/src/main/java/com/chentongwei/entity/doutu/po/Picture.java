package com.chentongwei.entity.doutu.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 图片PO
 *
 * @author TongWei.Chen 2017-05-17 13:00:16
 */
public class Picture implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;
    /** 分类id */
    private Integer CatalogId;
    /** Hash值，验证图片是否存在 */
    private String hash;
    /** 图片路径 */
    private String url;
    /** 图片宽度 */
    private Double width;
    /** 图片高度 */
    private Double height;
    /** 上传人id */
    private Long creatorId;
    /** 上传时间 */
    private Date createTime;
    /** 来源状态 1：爬虫；2：用户通过web上传 */
    private Integer status;
    /** 删除状态 0:已删除；1：正常*/
    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCatalogId() {
        return CatalogId;
    }

    public void setCatalogId(Integer catalogId) {
        CatalogId = catalogId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Picture{");
        sb.append("id=").append(id);
        sb.append(", CatalogId=").append(CatalogId);
        sb.append(", hash='").append(hash).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", creatorId=").append(creatorId);
        sb.append(", createTime=").append(createTime);
        sb.append(", status=").append(status);
        sb.append(", isDelete=").append(isDelete);
        sb.append('}');
        return sb.toString();
    }
}
