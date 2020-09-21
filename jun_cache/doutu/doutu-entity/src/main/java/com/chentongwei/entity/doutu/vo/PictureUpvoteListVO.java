package com.chentongwei.entity.doutu.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * ListByUserIdVO,查看某用户点赞过的图片
 *
 * @author TongWei.Chen 2017-05-31 20:31:00
 */
public class PictureUpvoteListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;
    /** 图片id */
    private Long pictureId;
    /** 用户id */
    private Long userId;
    /** url */
    private String pictureUrl;
    /** 点赞时间 */
    private Date createTime;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
