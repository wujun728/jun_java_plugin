package com.chentongwei.core.user.dao;

import com.chentongwei.core.user.entity.io.favorite.ArticleFavoriteDeleteIO;
import com.chentongwei.core.user.entity.io.favorite.ArticleFavoriteSaveIO;
import com.chentongwei.core.user.entity.io.favorite.ArticleFavoriteUpdateIO;
import com.chentongwei.core.user.entity.vo.favorite.ArticleFavoriteDetailVO;
import com.chentongwei.core.user.entity.vo.favorite.ArticleFavoriteListVO;

import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 收藏夹DAO
 **/
public interface IArticleFavoriteDAO {

    /**
     * 查看某人的收藏夹列表
     *
     * @param userId：用户id
     * @return
     */
    List<ArticleFavoriteListVO> list(Long userId);

    /**
     * 收藏夹详情
     *
     * @param id：主键id
     * @return
     */
    ArticleFavoriteDetailVO getById(Long id);

    /**
     * 修改收藏夹
     *
     * @param articleFavoriteUpdateIO：参数
     * @return
     */
    boolean update(ArticleFavoriteUpdateIO articleFavoriteUpdateIO);

    /**
     * 保存收藏夹
     *
     * @param articleFavoriteSaveIO：参数
     * @return
     */
    boolean save(ArticleFavoriteSaveIO articleFavoriteSaveIO);

    /**
     * 删除收藏夹
     *
     * @param articleFavoriteDeleteIO：参数
     * @return
     */
    boolean delete(ArticleFavoriteDeleteIO articleFavoriteDeleteIO);

}
