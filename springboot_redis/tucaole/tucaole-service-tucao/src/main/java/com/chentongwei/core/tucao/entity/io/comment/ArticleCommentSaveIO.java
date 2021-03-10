package com.chentongwei.core.tucao.entity.io.comment;

import com.chentongwei.common.entity.io.TokenIO;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 文章评论/回复保存IO
 */
@Data
public class ArticleCommentSaveIO extends TokenIO {
    /** 主键id */
    private Long id;
    /** 回复者id 就是TokenIO里的userId*/

    /** 被回复者id */
    @NotNull
    private Long toUserId;
    /** 父级id */
    @NotNull
    private Long pid;
    /** 文章id */
    @NotNull
    private Long articleId;
    /** 评论内容 */
    @NotNull
    @NotBlank
    private String content;
    /** 评论内容【APP端用】 */
    @NotNull
    @NotBlank
    private String contentApp;
}