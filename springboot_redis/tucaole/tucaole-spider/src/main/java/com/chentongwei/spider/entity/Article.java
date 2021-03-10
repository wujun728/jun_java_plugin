package com.chentongwei.spider.entity;

import java.util.Date;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: csdn文章
 */
public class Article {
    /**
     * 主键ID
     */
    private Long id;

    private Date inertAt;
    /**
     * 文章名称
     */
    private String name;

    /**
     * 文章名称
     */
    private String url;

    /**
     * 属于哪一期
     */
    private Integer stage;

    /**
     * 浏览量
     */
    private Integer views;

    /**
     * 收藏数
     */
    private Integer collections;

    /**
     * 所属知识库类别
     */
    private String type;

    /**
     * 类别图片地址
     */
    private String img;

    /**
     * 更新时间
     */
    private Date updateAt;

    public Article() {
        this.updateAt = new Date();
        this.inertAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getInertAt() {
        return inertAt;
    }

    public void setInertAt(Date inertAt) {
        this.inertAt = inertAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getCollections() {
        return collections;
    }

    public void setCollections(Integer collections) {
        this.collections = collections;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}
