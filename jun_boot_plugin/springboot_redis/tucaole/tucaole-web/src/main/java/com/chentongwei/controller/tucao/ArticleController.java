package com.chentongwei.controller.tucao;

import com.chentongwei.common.annotation.CategoryLog;
import com.chentongwei.common.annotation.DescLog;
import com.chentongwei.common.entity.Page;
import com.chentongwei.common.entity.Result;
import com.chentongwei.common.enums.msg.ResponseEnum;
import com.chentongwei.common.util.CommonExceptionUtil;
import com.chentongwei.core.tucao.biz.article.ArticleBiz;
import com.chentongwei.core.tucao.biz.article.ReadCountBiz;
import com.chentongwei.core.tucao.entity.io.article.ArticleDeleteIO;
import com.chentongwei.core.tucao.entity.io.article.ArticleSaveIO;
import com.chentongwei.core.tucao.entity.io.article.ArticleUpdateIO;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 吐槽文章接口
 */
@RestController
@RequestMapping("/tucao/article")
@CategoryLog(menu = "吐槽模块")
public class ArticleController {

    @Autowired
    private ArticleBiz articleBiz;
    @Autowired
    private ReadCountBiz readCountBiz;

    /**
     * 吐槽文章列表接口
     *
     * @param page：分页信息
     * @return
     */
    @GetMapping("/list")
    public Result list(HttpServletRequest request, Page page) {
        String userId = request.getHeader("userId");
        return articleBiz.list(userId, page);
    }

    /**
     * 文章详情
     *
     * @param articleId：文章id
     * @return
     */
    @GetMapping("/detail")
    public Result detail(HttpServletRequest request, Long articleId) {
        String userId = request.getHeader("userId");
        CommonExceptionUtil.nullCheck(articleId, ResponseEnum.PARAM_ERROR);
        //文章阅读数
        readCountBiz.addArticleReadCount(articleId);
        return articleBiz.detail(articleId, userId);
    }

    /**
     * 更新吐槽文章
     *
     * @param articleUpdateIO：参数
     * @return
     */
    @PostMapping("/update")
    @DescLog("更新文章")
    public Result update(@Valid ArticleUpdateIO articleUpdateIO) {
        String content = StringEscapeUtils.escapeHtml(articleUpdateIO.getContent());
        articleUpdateIO.setContent(content);
        articleUpdateIO.setModifyTime(new Date());
        return articleBiz.update(articleUpdateIO);
    }

    /**
     * 保存吐槽文章
     *
     * @param articleSaveIO：参数
     * @return
     */
    @PostMapping("/save")
    @DescLog("保存文章")
    public Result save(@Valid ArticleSaveIO articleSaveIO) {
        String content = StringEscapeUtils.escapeHtml(articleSaveIO.getContent());
        articleSaveIO.setContent(content);
        return articleBiz.save(articleSaveIO);
    }

    /**
     * 删除文章（并非删除，只是改变is_delete的状态）
     *
     * @param tucaoArticleDeleteIO：参数
     * @return
     */
    @PostMapping("/delete")
    @DescLog("删除文章")
    public Result delete(@Valid ArticleDeleteIO tucaoArticleDeleteIO) {
        return articleBiz.delete(tucaoArticleDeleteIO);
    }
}
