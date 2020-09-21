package com.chentongwei.entity.doutu.io;

import java.io.Serializable;

/**
 * 保存或更新IO
 *
 * @author TongWei.Chen 2017-05-27 11:40:30
 */
public class PictureTagIO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;
    /** 图片id */
    private Long pictureId;
    /** 标签名称 */
    private String tagName;
    /** 图片路径 */
    private String pictureUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPictureId() {
        return pictureId;
    }

    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
