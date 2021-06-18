package com.chentongwei.core.user.dao;

import com.chentongwei.core.user.entity.io.favorite.ArticleFavoriteLinkSaveIO;
import org.apache.ibatis.annotations.Param;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 资源收藏DAO
 **/
public interface IArticleFavoriteLinkDAO {

    /**
     * 根据收藏夹id查询收藏夹下的资源总数
     *
     * @param favoriteId：收藏夹id
     * @return
     */
    Long countByFavoriteId(Long favoriteId);

    /**
     * 保存
     *
     * @param articleFavoriteLinkSaveIO：参数
     * @return
     */
    boolean save(ArticleFavoriteLinkSaveIO articleFavoriteLinkSaveIO);

    /**
     * 根据收藏夹id删除收藏夹下的所有资源（删除收藏夹会用到此方法）
     *
     * @param favoriteId：收藏夹id
     * @return
     */
    boolean deleteByFavoriteId(Long favoriteId);

    /**
     * 检查某收藏夹是否已经收藏过此篇文章
     *
     * @param articleId：文章id
     * @param favoriteId：收藏夹id
     * @return
     */
    Integer exists(@Param("articleId") Long articleId, @Param("favoriteId") Long favoriteId);

}