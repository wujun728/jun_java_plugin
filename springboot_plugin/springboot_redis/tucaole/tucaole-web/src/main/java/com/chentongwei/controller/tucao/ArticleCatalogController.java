package com.chentongwei.controller.tucao;

import com.chentongwei.common.entity.Result;
import com.chentongwei.core.tucao.biz.catalog.ArticleCatalogBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 分类接口
 */
@RestController
@RequestMapping("/tucao/catalog")
public class ArticleCatalogController {

    @Autowired
    private ArticleCatalogBiz articleCatalogBiz;

    /**
     * 获取分类列表
     *
     * @return
     */
    @GetMapping("/list")
    public Result list() {
        return articleCatalogBiz.list();
    }
}