package com.chentongwei.entity.doutu.io;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 图片IO
 *
 * @author TongWei.Chen 2017-05-17 13:31:57
 */
public class PictureIO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 图片id */
    private Long id;
    /** 分类id */
    @NotNull
    private Integer catalogId;
    /** Hash值，验证图片是否存在 */
    private String hash;
    /** 图片路径 */
    private String url;
    /** 上传人id */
    private Long creatorId;
    /** 来源状态 1：爬虫；2：用户通过web上传 */
    private Integer status;
    /** 图片字节数组，验证图片是否已经存在时候使用 */
    private byte[] bytes;
    /** 文件名后缀 */
    private String suffix;
    /** 标签 */
    private List<String> tagList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
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

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }
}
