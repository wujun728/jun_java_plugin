package com.chentongwei.entity.doutu.io;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 保存图片IO
 *
 * @author TongWei.Chen 2017-06-14 10:42:12
 */
public class PictureSaveIO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 分类id */
    @NotNull
    private Integer catalogId;
    /** 上传人id */
    private Long creatorId;
    /** 来源状态 1：爬虫；2：用户通过web上传 */
    private Integer status;
    /** 图片基本信息 */
    @NotEmpty(message = "请选择图片")
    @NotNull(message = "请选择图片")
    private List<PictureBaseIO> pictures;
    /** 标签 */
    @NotEmpty(message = "请填写标签")
    @NotNull(message = "请填写标签")
    private List<String> pictureTags;

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<PictureBaseIO> getPictures() {
        return pictures;
    }

    public void setPictures(List<PictureBaseIO> pictures) {
        this.pictures = pictures;
    }

    public List<String> getPictureTags() {
        return pictureTags;
    }

    public void setPictureTags(List<String> pictureTags) {
        this.pictureTags = pictureTags;
    }
}
