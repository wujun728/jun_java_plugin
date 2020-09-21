package com.chentongwei.entity.doutu.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 图片点赞PO
 *
 * @author TongWei.Chen 2017-05-23 20:08:35
 */
public class PictureUpvote implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;
    /** 图片id */
    private Long pictureId;
    /** 用户id */
    private Long userId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
