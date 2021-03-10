package com.chentongwei.core.user.biz.favorite;

import com.chentongwei.cache.redis.IBasicCache;
import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.common.enums.constant.RedisEnum;
import com.chentongwei.common.enums.constant.TableNameEnum;
import com.chentongwei.common.enums.constant.TimeEnum;
import com.chentongwei.common.exception.BussinessException;
import com.chentongwei.common.util.CommonExceptionUtil;
import com.chentongwei.core.common.biz.CommonBiz;
import com.chentongwei.core.user.dao.IArticleFavoriteDAO;
import com.chentongwei.core.user.dao.IArticleFavoriteLinkDAO;
import com.chentongwei.core.user.entity.io.favorite.*;
import com.chentongwei.core.user.entity.vo.favorite.ArticleFavoriteDetailVO;
import com.chentongwei.core.user.entity.vo.favorite.ArticleFavoriteListVO;
import com.chentongwei.core.user.enums.msg.ArticleFavoriteMsgEnum;
import com.github.pagehelper.PageHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 收藏夹Biz
 **/
@Service
public class ArticleFavoriteBiz {
    private static final Logger LOG = LogManager.getLogger("bizLog");

    @Autowired
    private IArticleFavoriteDAO favoriteDAO;
    @Autowired
    private IArticleFavoriteLinkDAO articleFavoriteLinkDAO;
    @Autowired
    private CommonBiz commonBiz;
    @Autowired
    private IBasicCache basicCache;

    /**
     * 查看某人的收藏夹列表
     *
     * @param articleFavoriteListIO：用户id
     * @return
     */
    public Result list(ArticleFavoriteListIO articleFavoriteListIO) {
        //TODO 根据文章id查看每个收藏夹是否收藏过？
        //TODO 判断userId是不是当前登录用户，若不是则只查询公开的不查询私有的
        PageHelper.startPage(articleFavoriteListIO.getPageNum(), articleFavoriteListIO.getPageSize());
        List<ArticleFavoriteListVO> list = favoriteDAO.list(articleFavoriteListIO.getUserId());
        //收藏资源数
        list.forEach(articleFavoriteListVO -> {
            Long count = articleFavoriteLinkDAO.countByFavoriteId(articleFavoriteListVO.getId());
            articleFavoriteListVO.setCount(count);
        });
        return ResultCreator.getSuccess(list);
    }

    /**
     * 查看某收藏夹的详情
     *
     * @param favoriteId：收藏夹id
     * @return
     */
    public Result detail(Long favoriteId) {
        ArticleFavoriteDetailVO favoriteDetailVO = favoriteDAO.getById(favoriteId);
        CommonExceptionUtil.nullCheck(favoriteDetailVO);
        return ResultCreator.getSuccess(favoriteDetailVO);
    }

    /**
     * 修改收藏夹
     *
     * @param articleFavoriteUpdateIO：参数
     * @return
     */
    @Transactional
    public Result update(ArticleFavoriteUpdateIO articleFavoriteUpdateIO) {
        //判断是否是本人数据
        commonBiz.existsOneSelf(articleFavoriteUpdateIO.getId(), articleFavoriteUpdateIO.getUserId(), TableNameEnum.FAVORITE.tableName());
        favoriteDAO.update(articleFavoriteUpdateIO);
        LOG.info("收藏夹【{}】修改成功", articleFavoriteUpdateIO.getName());
        return ResultCreator.getSuccess();
    }

    /**
     * 新建收藏夹
     *
     * @param articleFavoriteSaveIO：参数
     * @return
     */
    @Transactional
    public Result save(ArticleFavoriteSaveIO articleFavoriteSaveIO) {
        long count = basicCache.increment(RedisEnum.getFavoriteCountMax(articleFavoriteSaveIO.getUserId().toString()), 0);
        if (count >= TimeEnum.THRITY_TIME.value()) {
            LOG.info("每天最多创建30个收藏夹");
            throw new BussinessException(ArticleFavoriteMsgEnum.FAVORITE_COUNT_MAX);
        }
        favoriteDAO.save(articleFavoriteSaveIO);
        //redis + 1
        basicCache.increment(RedisEnum.getFavoriteCountMax(articleFavoriteSaveIO.getUserId().toString()), 1);
        LOG.info("收藏夹【{}】创建成功！", articleFavoriteSaveIO.getName());
        return ResultCreator.getSuccess();
    }

    /**
     * 保存到收藏夹
     *
     * @param articleFavoriteLinkSaveIO：参数
     * @return
     */
    @Transactional
    public Result saveToFavorite(ArticleFavoriteLinkSaveIO articleFavoriteLinkSaveIO) {
        //判断是否是自己的数据
        commonBiz.existsOneSelf(articleFavoriteLinkSaveIO.getFavoriteId(), articleFavoriteLinkSaveIO.getUserId(), TableNameEnum.FAVORITE.tableName());
        long count = basicCache.increment(RedisEnum.getSaveToFavoriteCountMax(articleFavoriteLinkSaveIO.getUserId().toString()), 0);
        if (count >= TimeEnum.HUNDRED_TIME.value()) {
            LOG.info("每天最多收藏100个文章");
            throw new BussinessException(ArticleFavoriteMsgEnum.SAVE_TO_FAVORITE_COUNT_MAX);
        }
        //判断是否收藏过
        Integer existsFlag = articleFavoriteLinkDAO.exists(articleFavoriteLinkSaveIO.getArticleId(), articleFavoriteLinkSaveIO.getFavoriteId());
        CommonExceptionUtil.notNullCheck(existsFlag, ArticleFavoriteMsgEnum.ALREADY_COLLECT);

        articleFavoriteLinkDAO.save(articleFavoriteLinkSaveIO);
        //redis + 1
        basicCache.increment(RedisEnum.getSaveToFavoriteCountMax(articleFavoriteLinkSaveIO.getUserId().toString()), 1);
        LOG.info("文章【{}】保存到收藏夹【{}】成功", articleFavoriteLinkSaveIO.getArticleId(), articleFavoriteLinkSaveIO.getFavoriteId());
        return ResultCreator.getSuccess();
    }

    /**
     * 删除收藏夹
     *
     * @param articleFavoriteDeleteIO：参数
     * @return
     */
    @Transactional
    public Result delete(ArticleFavoriteDeleteIO articleFavoriteDeleteIO) {
        commonBiz.existsOneSelf(articleFavoriteDeleteIO.getId(), articleFavoriteDeleteIO.getUserId(), TableNameEnum.FAVORITE.tableName());
        favoriteDAO.delete(articleFavoriteDeleteIO);
        LOG.info("收藏夹【{}】删除成功！", articleFavoriteDeleteIO.getId());
        //删除收藏夹下的资源
        articleFavoriteLinkDAO.deleteByFavoriteId(articleFavoriteDeleteIO.getId());
        LOG.info("收藏夹下的资源删除成功！");
        return ResultCreator.getSuccess();
    }

}
