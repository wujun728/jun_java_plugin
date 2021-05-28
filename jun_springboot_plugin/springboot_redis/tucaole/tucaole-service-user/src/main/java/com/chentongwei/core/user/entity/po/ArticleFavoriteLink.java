package com.chentongwei.core.user.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 资源收藏表
 */
@Data
public class ArticleFavoriteLink implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;
    /** 吐槽了文章id */
    private Long articleId;
    /** 收藏夹id */
    private Integer favoriteId;
    /** 收藏时间 */
    private Date createTime;
}