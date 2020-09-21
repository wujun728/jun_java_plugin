package com.chentongwei.entity.common.io;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 友情链接保存或更新IO
 *
 * @author TongWei.Chen 2017-05-31 18:55:24
 */
public class FriendLinkIO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** id */
    private Integer id;
    /** 链接名 */
    @NotEmpty
    @NotNull
    @NotBlank
    private String name;
    /** 链接url */
    @NotEmpty
    @NotNull
    @NotBlank
    private String url;
    /** 网站拥有者 */
    @NotEmpty
    @NotNull
    @NotBlank
    private String owner;
    /** 排序字段，越小越最后显示 */
    @NotNull
    private Integer topNum;

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
}
