package com.chentongwei.core.tucao.biz.support;

import com.chentongwei.cache.redis.IHashCache;
import com.chentongwei.cache.redis.ISetCache;
import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.common.enums.constant.RedisEnum;
import com.chentongwei.common.util.CommonExceptionUtil;
import com.chentongwei.core.tucao.dao.IArticleDAO;
import com.chentongwei.core.tucao.dao.IArticleSupportDAO;
import com.chentongwei.core.tucao.entity.io.support.ArticleSupportSaveIO;
import com.chentongwei.core.tucao.entity.vo.article.ArticleDetailVO;
import com.chentongwei.core.tucao.enums.status.ArticleSupportStatusEnum;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 文章点赞业务
 */
@Service
public class ArticleSupportBiz {
    private static final Logger LOG = LogManager.getLogger("bizLog");

    @Autowired
    private IArticleSupportDAO articleSupportDAO;
    @Autowired
    private IArticleDAO articleDAO;
    @Autowired
    private ISetCache setCache;
    @Autowired
    private IHashCache hashCache;

    /**
     * 点赞（支持）
     *
     * @param articleSupportSaveIO：参数
     * @return
     */
    @Transactional
    public Result support(ArticleSupportSaveIO articleSupportSaveIO) {
        //文章id需要验证是否存在。
        ArticleDetailVO detailVO = articleDAO.getById(articleSupportSaveIO.getArticleId());
        CommonExceptionUtil.nullCheck(detailVO);
        //开始业务
        supportRedis(articleSupportSaveIO);
        articleSupportSaveIO.setStatus(ArticleSupportStatusEnum.SUPPORT.value());
        return ResultCreator.getSuccess();
    }

    /**
     * 反对
     *
     * @param articleSupportSaveIO：参数
     * @return
     */
    @Transactional
    public Result oppose(ArticleSupportSaveIO articleSupportSaveIO) {
        //文章id需要验证是否存在。
        ArticleDetailVO detailVO = articleDAO.getById(articleSupportSaveIO.getArticleId());
        CommonExceptionUtil.nullCheck(detailVO);
        //开始业务
        opposeRedis(articleSupportSaveIO);
        articleSupportSaveIO.setStatus(ArticleSupportStatusEnum.OPPOSE.value());
        return ResultCreator.getSuccess();
    }

    /**
     * 点赞Redis方法
     *
     * 最后缓存到hash是因为要跑个定时任务每天凌晨去将点赞记录刷到db。刷一条就删除redis的hash一行
     * 为什么不直接用set？
     * 因为set的话是完整的点赞记录，没状态标识他是否已经将这条记录刷到db，所以每天都会将set集合中的全部数据刷到db，显然不妥
     * 所以用个hash类型，若已经点赞/反对了，则再次点击直接移除hash，若没点过则直接变成点赞（1）/反对（0）的状态等小程序执行的时候刷到db
     *
     * @param articleSupportSaveIO：参数
     */
    private final void supportRedis(ArticleSupportSaveIO articleSupportSaveIO) {
        boolean existsFlag = setCache.exists(RedisEnum.getTucaoSupportKey(articleSupportSaveIO.getArticleId().toString()), articleSupportSaveIO.getUserId().toString());
        //若已经点过赞了，再点一次应该删除这条记录，状态是-1(没反对也没点赞)
        if (existsFlag) {
            setCache.removeSet(RedisEnum.getTucaoSupportKey(articleSupportSaveIO.getArticleId().toString()), articleSupportSaveIO.getUserId().toString());
            //从hash中移除
            hashCache.deleteHash(RedisEnum.getTucaoHashArticleSupportKey(articleSupportSaveIO.getArticleId().toString()), articleSupportSaveIO.getUserId().toString());
        } else {
            //sadd到set类型，如：key为：support:文章id；value为：[userId集合]（无需判重，因为重复的话不会加进去）
            setCache.cacheSet(RedisEnum.getTucaoSupportKey(articleSupportSaveIO.getArticleId().toString()), articleSupportSaveIO.getUserId().toString());
            //从反对集合srem掉这条记录
            setCache.removeSet(RedisEnum.getTucaoOpposeKey(articleSupportSaveIO.getArticleId().toString()), articleSupportSaveIO.getUserId().toString());
            //缓存到hash
            hashCache.cacheHash(
                    RedisEnum.getTucaoHashArticleSupportKey(articleSupportSaveIO.getArticleId().toString()),
                    articleSupportSaveIO.getUserId().toString(),
                    String.valueOf(ArticleSupportStatusEnum.SUPPORT.value()));
        }
    }

    /**
     * 反对Redis方法
     *
     * @param articleSupportSaveIO：参数
     */
    private final void opposeRedis(ArticleSupportSaveIO articleSupportSaveIO) {
        //如果点过反对了，再点一次应该删除这条记录，状态是-1
        boolean existsFlag = setCache.exists(RedisEnum.getTucaoOpposeKey(articleSupportSaveIO.getArticleId().toString()), articleSupportSaveIO.getUserId().toString());
        if (existsFlag) {
            setCache.removeSet(RedisEnum.getTucaoOpposeKey(articleSupportSaveIO.getArticleId().toString()), articleSupportSaveIO.getUserId().toString());
            hashCache.deleteHash(RedisEnum.getTucaoHashArticleSupportKey(articleSupportSaveIO.getArticleId().toString()), articleSupportSaveIO.getUserId().toString());
        } else {
            //sadd到set类型，如：key为：support:文章id；value为：[userId集合]（无需判重，因为重复的话不会加进去）
            setCache.cacheSet(RedisEnum.getTucaoOpposeKey(articleSupportSaveIO.getArticleId().toString()), articleSupportSaveIO.getUserId().toString());
            //从反对集合srem掉这条记录
            setCache.removeSet(RedisEnum.getTucaoSupportKey(articleSupportSaveIO.getArticleId().toString()), articleSupportSaveIO.getUserId().toString());
            hashCache.cacheHash(
                    RedisEnum.getTucaoHashArticleSupportKey(articleSupportSaveIO.getArticleId().toString()),
                    articleSupportSaveIO.getUserId().toString(),
                    String.valueOf(ArticleSupportStatusEnum.OPPOSE.value()));
        }
    }
}
