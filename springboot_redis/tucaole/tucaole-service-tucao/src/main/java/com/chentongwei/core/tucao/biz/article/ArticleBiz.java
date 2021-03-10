package com.chentongwei.core.tucao.biz.article;

import com.alibaba.fastjson.JSON;
import com.chentongwei.cache.redis.IBasicCache;
import com.chentongwei.cache.redis.ISetCache;
import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Page;
import com.chentongwei.common.entity.Result;
import com.chentongwei.common.enums.constant.RedisEnum;
import com.chentongwei.common.enums.constant.TableNameEnum;
import com.chentongwei.common.util.CommonExceptionUtil;
import com.chentongwei.core.common.biz.CommonBiz;
import com.chentongwei.core.tucao.dao.IArticleCatalogDAO;
import com.chentongwei.core.tucao.dao.IArticleDAO;
import com.chentongwei.core.tucao.entity.io.article.ArticleDeleteIO;
import com.chentongwei.core.tucao.entity.io.article.ArticleSaveIO;
import com.chentongwei.core.tucao.entity.io.article.ArticleUpdateIO;
import com.chentongwei.core.tucao.entity.vo.article.ArticleDetailVO;
import com.chentongwei.core.tucao.entity.vo.article.ArticleListVO;
import com.chentongwei.core.tucao.entity.vo.article.ArticleOperateCount;
import com.chentongwei.core.tucao.entity.vo.catalog.ArticleCatalogListVO;
import com.chentongwei.core.tucao.enums.status.ArticleSupportStatusEnum;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 吐槽文章业务实现
 */
@Service
public class ArticleBiz {

    @Autowired
    private IArticleDAO articleDAO;
    @Autowired
    private IArticleCatalogDAO articleCatalogDAO;
    @Autowired
    private CommonBiz commonBiz;
    @Autowired
    private IBasicCache basicCache;
    @Autowired
    private ISetCache setCache;

    /**
     * 根据文章id查看文章详情
     *
     * @param id：主键id
     * @param userId：用户id
     * @return
     */
    public Result detail(Long id, String userId) {
        ArticleDetailVO detailVO = articleDAO.getById(id);
        //从Redis里取出分类信息
        String catalogStr = basicCache.get(RedisEnum.getTucaoCatalogKey());
        List<ArticleCatalogListVO> catalogList = JSON.parseArray(catalogStr, ArticleCatalogListVO.class);
        //取出分类名称
        detailVO.setCatalogName(catalogList.get(detailVO.getCatalogId() - 1).getName());
        //取出关注者人数
        long followCount = basicCache.increment(RedisEnum.getUserFollowCount(detailVO.getCreatorId().toString()), 0);
        detailVO.setFollowCount(followCount);
        buildOperateCount(detailVO);
        //判断是否点过赞（或反对过），前端高亮显示用
        //-1:不高亮；0：反对；1：支持.
        int supportStatus = getSupportStatus(detailVO.getId(), userId);
        detailVO.setSupportStatus(supportStatus);
        return ResultCreator.getSuccess(detailVO);
    }

    /**
     * 吐槽文章列表
     *
     * @param page：分页信息
     * @return
     */
    public Result list(String userId, Page page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<ArticleListVO> list = articleDAO.list();

        //从Redis里取出分类信息
        String catalog = basicCache.get(RedisEnum.getTucaoCatalogKey());
        List<ArticleCatalogListVO> catalogList = JSON.parseArray(catalog, ArticleCatalogListVO.class);
        //取出分类名称
        list.forEach(article -> {
            //根据下标直接取，效率高。 -1是因为List下标从0开始
            article.setCatalogName(catalogList.get(article.getCatalogId() - 1).getName());
            buildOperateCount(article);
            //判断是否点过赞（或反对过），前端高亮显示用
            //-1:不高亮；0：反对；1：支持.
            int supportStatus = getSupportStatus(article.getId(), userId);
            article.setSupportStatus(supportStatus);

        });
        return ResultCreator.getSuccess(list);
    }

    /**
     * 更新吐槽文章
     *
     * @param articleUpdateIO：参数
     * @return
     */
    @Transactional
    public Result update(ArticleUpdateIO articleUpdateIO) {
        //检查是否是自己的数据
        commonBiz.existsOneSelf(articleUpdateIO.getId(), articleUpdateIO.getUserId(), TableNameEnum.TUCAO_ARTICLE.tableName());
        articleDAO.update(articleUpdateIO);
        return ResultCreator.getSuccess();
    }

    /**
     * 发表吐槽文章
     *
     * @param articleSaveIO：参数
     * @return
     */
    @Transactional
    public Result save(ArticleSaveIO articleSaveIO) {
        String catalogName = articleCatalogDAO.getById(articleSaveIO.getCatalogId());
        CommonExceptionUtil.nullCheck(catalogName);
        articleDAO.save(articleSaveIO);
        return ResultCreator.getSuccess();
    }

    /**
     * 删除文章（并非删除，只是改变is_delete的状态）
     *
     * @param tucaoArticleDeleteIO：参数
     * @return
     */
    @Transactional
    public Result delete(ArticleDeleteIO tucaoArticleDeleteIO) {
        //检查是否是自己的数据
        commonBiz.existsOneSelf(tucaoArticleDeleteIO.getId(), tucaoArticleDeleteIO.getUserId(), TableNameEnum.TUCAO_ARTICLE.tableName());
        articleDAO.updateDeleteStatus(tucaoArticleDeleteIO.getId());
        return ResultCreator.getSuccess();
    }

    /**
     * 获取文章评论数
     *
     * @param articleId：文章id
     */
    private final long getCommentCount(final Long articleId) {
        return basicCache.increment(RedisEnum.getTucaoArticleCommentCount(articleId.toString()), 0);
    }

    /**
     * 获取点赞总数
     *
     * @param articleId：文章id
     */
    private final long getSupportCount(final Long articleId) {
        //点赞总数
        return setCache.count(RedisEnum.getTucaoSupportKey(articleId.toString()));
    }

    /**
     * 获取反对总数
     *
     * @param articleId：文章id
     */
    private final long getOpposeCount(final Long articleId) {
        //反对总数
        return setCache.count(RedisEnum.getTucaoOpposeKey(articleId.toString()));
    }

    /**
     * 判断是否点过赞（或反对过），前端高亮显示用
     * -1:不高亮；0：反对；1：支持
     *
     * @param articleId:文章id
     * @param userId：从headers中取出userId，判断是否存在，若存在则直接将userId传给redis的各种方法，否则userId=-1,代表没登录
     */
    private final int getSupportStatus(final Long articleId, final String userId) {
        //默认-1
        Integer supportStatus = ArticleSupportStatusEnum.NO.value();
        boolean supportExistsFlag = setCache.exists(RedisEnum.getTucaoSupportKey(articleId.toString()), userId == null ? "-1" : userId);
        //如果有点赞记录,则1
        if (supportExistsFlag) {
            supportStatus = ArticleSupportStatusEnum.SUPPORT.value();
        } else {
            //否则看有没有取消点赞记录
            boolean opposeExistsFlag = setCache.exists(RedisEnum.getTucaoOpposeKey(articleId.toString()), userId == null ? "-1" : userId);
            //若有取消点赞记录，则0
            if (opposeExistsFlag) {
                supportStatus = ArticleSupportStatusEnum.OPPOSE.value();
            }
        }
        return supportStatus;
    }

    /**
     * 获取文章点赞点踩评论等数
     *
     * @param articleOperateCount：参数
     */
    private final void buildOperateCount (ArticleOperateCount articleOperateCount) {
        //获取评论数
        long commentCount = getCommentCount(articleOperateCount.getId());
        articleOperateCount.setCommentCount(commentCount);
        //获取点赞总数
        long supportCount = getSupportCount(articleOperateCount.getId());
        articleOperateCount.setSupportCount(supportCount);
        //获取反对总数
        long opposeCount = getOpposeCount(articleOperateCount.getId());
        articleOperateCount.setOpposeCount(opposeCount);
    }

}
