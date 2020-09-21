package com.chentongwei.entity.doutu.io;

import com.chentongwei.common.entity.Page;

import javax.validation.constraints.NotNull;

/**
 * 查询某用户点赞了哪些图片的IO
 *
 * @author TongWei.Chen 2017-05-31 20:38:27
 */
public class PictureUpvoteListIO extends Page {
    private static final long serialVersionUID = 1L;

    /** 用户id */
    @NotNull
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
