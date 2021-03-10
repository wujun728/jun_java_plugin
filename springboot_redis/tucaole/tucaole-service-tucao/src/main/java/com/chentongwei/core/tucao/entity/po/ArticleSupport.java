package com.chentongwei.core.tucao.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 文章点赞
 */
@Data
public class ArticleSupport implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Integer id;
    /** 吐槽文章id */
    private Integer articleId;
    /** 用户id */
    private Integer userId;
    /** 0：取消点赞；1：点赞 */
    private boolean status;
    /** 点赞时间 */
    private Date createTime;

}
