package com.chentongwei.controller.tucao;

import com.chentongwei.common.annotation.CategoryLog;
import com.chentongwei.common.annotation.DescLog;
import com.chentongwei.common.entity.Result;
import com.chentongwei.core.tucao.biz.support.ArticleSupportBiz;
import com.chentongwei.core.tucao.entity.io.support.ArticleSupportSaveIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 文章点赞接口
 */
@RestController
@RequestMapping("/tucao/article")
@CategoryLog(menu = "吐槽模块")
public class ArticleSupportController {

    @Autowired
    private ArticleSupportBiz articleSupportBiz;

    /**
     * 点赞
     *
     * @param articleSupportSaveIO：参数
     * @return
     */
    @PostMapping("/support")
    @DescLog("文章点赞")
    public Result support(@Valid ArticleSupportSaveIO articleSupportSaveIO) {
        return articleSupportBiz.support(articleSupportSaveIO);
    }

    /**
     * 反对
     *
     * @param articleSupportSaveIO：参数
     * @return
     */
    @PostMapping("/oppose")
    @DescLog("文章点踩")
    public Result oppose(@Valid ArticleSupportSaveIO articleSupportSaveIO) {
        return articleSupportBiz.oppose(articleSupportSaveIO);
    }

}
