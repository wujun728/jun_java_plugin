package com.chentongwei.controller.tucao;

import com.chentongwei.common.entity.Result;
import com.chentongwei.core.tucao.biz.comment.ArticleCommentBiz;
import com.chentongwei.core.tucao.entity.io.comment.ArticleCommentListIO;
import com.chentongwei.core.tucao.entity.io.comment.ArticleCommentSaveIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 文章评论接口
 */
@RestController
@RequestMapping("/tucao/comment")
public class ArticleCommentController {

    @Autowired
    private ArticleCommentBiz articleCommentBiz;

    /**
     * 文章评论/回复列表
     *
     * @param articleCommentListIO：参数
     * @return
     */
    @GetMapping("/list")
    public Result list(@Valid ArticleCommentListIO articleCommentListIO) {
        return articleCommentBiz.list(articleCommentListIO);
    }

    /**
     * 文章评论/回复保存
     *
     * @param articleCommentSaveIO：参数
     * @return
     */
    @PostMapping("/save")
    public Result save(@Valid ArticleCommentSaveIO articleCommentSaveIO) {
        return articleCommentBiz.save(articleCommentSaveIO);
    }

}
