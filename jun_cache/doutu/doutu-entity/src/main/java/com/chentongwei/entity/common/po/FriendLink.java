package com.chentongwei.entity.common.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 友情链接
 *
 * @author TongWei.Chen 2017-05-31 18:33:31
 */
public class FriendLink implements Serializable {
    private static final long serialVersionUID = 1L;

    /** id */
    private Integer id;
    /** 链接名 */
    private String name;
    /** 链接url */
    private String url;
    /** 网站拥有者 */
    private String owner;
    /** 排序字段，越小越最后显示 */
    private Integer topNum;
    /** 创建时间 */
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getTopNum() {
        return topNum;
    }

    public void setTopNum(Integer topNum) {
        this.topNum = topNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
