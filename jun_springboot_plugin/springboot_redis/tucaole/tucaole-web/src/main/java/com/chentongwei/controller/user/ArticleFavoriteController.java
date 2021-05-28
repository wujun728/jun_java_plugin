package com.chentongwei.controller.user;

import com.chentongwei.common.annotation.CategoryLog;
import com.chentongwei.common.annotation.DescLog;
import com.chentongwei.common.entity.Result;
import com.chentongwei.common.enums.msg.ResponseEnum;
import com.chentongwei.common.util.CommonExceptionUtil;
import com.chentongwei.core.user.biz.favorite.ArticleFavoriteBiz;
import com.chentongwei.core.user.entity.io.favorite.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 收藏夹接口
 **/
@RestController
@RequestMapping("/user/favorite")
@CategoryLog(menu = "用户模块")
public class ArticleFavoriteController {

    @Autowired
    private ArticleFavoriteBiz articleFavoriteBiz;

    /**
     * 收藏夹列表
     *
     * @param articleFavoriteListIO：参数
     * @return
     */
    @GetMapping("/list")
    public Result list(@Valid ArticleFavoriteListIO articleFavoriteListIO) {
        return articleFavoriteBiz.list(articleFavoriteListIO);
    }

    /**
     * 收藏夹详情
     *
     * @param favoriteId：收藏夹id
     * @return
     */
    @GetMapping("/detail")
    public Result detail(Long favoriteId) {
        CommonExceptionUtil.nullCheck(favoriteId, ResponseEnum.PARAM_ERROR);
        return articleFavoriteBiz.detail(favoriteId);
    }

    /**
     * 修改收藏夹
     *
     * @param articleFavoriteUpdateIO：参数
     * @return
     */
    @PostMapping("/update")
    @DescLog("修改收藏夹")
    public Result update(@Valid ArticleFavoriteUpdateIO articleFavoriteUpdateIO) {
        return articleFavoriteBiz.update(articleFavoriteUpdateIO);
    }

    /**
     * 创建收藏夹
     *
     * @param articleFavoriteSaveIO：参数
     * @return
     */
    @PostMapping("/save")
    @DescLog("创建收藏夹")
    public Result save(@Valid ArticleFavoriteSaveIO articleFavoriteSaveIO) {
        return articleFavoriteBiz.save(articleFavoriteSaveIO);
    }

    /**
     * 保存到收藏夹
     *
     * @param articleFavoriteLinkSaveIO：参数
     * @return
     */
    @PostMapping("/saveToFavorite")
    @DescLog("将文章保存到收藏夹")
    public Result saveToFavorite(@Valid ArticleFavoriteLinkSaveIO articleFavoriteLinkSaveIO) {
        return articleFavoriteBiz.saveToFavorite(articleFavoriteLinkSaveIO);
    }

    /**
     * 删除收藏夹
     *
     * @param articleFavoriteDeleteIO：参数
     * @return
     */
    @PostMapping("/delete")
    @DescLog("删除收藏夹")
    public Result delete(@Valid ArticleFavoriteDeleteIO articleFavoriteDeleteIO) {
        return articleFavoriteBiz.delete(articleFavoriteDeleteIO);
    }
}
