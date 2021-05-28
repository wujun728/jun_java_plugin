package com.chentongwei.core.tucao.entity.vo.comment;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 评论/回复List
 *
 * @author Wujun
 */
@Data
public class ArticleCommentListVO implements Serializable {
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
    /** 评论时间 */
    private Date createTime;

    /** 评论回复信息 */
    private List<ArticleCommentListVO> replyComment;
    /** 评论者信息 */
    private ArticleCommentUserVO commentUser = new ArticleCommentUserVO();
    /** 回复评论的人 */
    private ArticleCommentUserVO replyUser = new ArticleCommentUserVO();

}