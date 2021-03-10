package com.chentongwei.core.tucao.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 评论表
 *
 * @author Wujun
 */
@Data
public class ArticleComment implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;
    /** 回复者id */
    private Long fromUserId;
    /** 被回复者id */
    private Long toUserId;
    /** 父级id */
    private Long pid;
    /** 文章id */
    private Long articleId;
    /** 评论内容 */
    private String content;
    /** 评论内容【APP端用】 */
    private String contentApp;
    /** 是否删除： 0：已删除；1：未删除 */
    private Integer isDelete;
    /** 评论时间 */
    private Date createTime;

}