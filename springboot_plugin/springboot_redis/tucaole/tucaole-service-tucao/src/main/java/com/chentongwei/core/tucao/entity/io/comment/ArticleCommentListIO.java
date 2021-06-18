package com.chentongwei.core.tucao.entity.io.comment;

import com.chentongwei.common.entity.Page;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 文章评论/回复列表IO
 */
@Data
public class ArticleCommentListIO extends Page {
    /** 父id */
    private Long pid;
    /** 文章id */
    @NotNull
    private Long articleId;

    public ArticleCommentListIO pid(Long pid) {
        this.pid = pid;
        return this;
    }

    public ArticleCommentListIO articleId(Long articleId) {
        this.articleId = articleId;
        return this;
    }
}
