package com.chentongwei.core.tucao.biz.comment;

import com.chentongwei.cache.redis.IBasicCache;
import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.common.enums.constant.RedisEnum;
import com.chentongwei.common.enums.constant.TimeEnum;
import com.chentongwei.common.exception.BussinessException;
import com.chentongwei.common.util.CommonExceptionUtil;
import com.chentongwei.core.tucao.dao.IArticleCommentDAO;
import com.chentongwei.core.tucao.dao.IArticleDAO;
import com.chentongwei.core.tucao.entity.io.comment.ArticleCommentListIO;
import com.chentongwei.core.tucao.entity.io.comment.ArticleCommentSaveIO;
import com.chentongwei.core.tucao.entity.vo.article.ArticleDetailVO;
import com.chentongwei.core.tucao.entity.vo.comment.ArticleCommentListVO;
import com.chentongwei.core.tucao.entity.vo.comment.ArticleCommentUserVO;
import com.chentongwei.core.tucao.enums.msg.ArticleCommentMsgEnum;
import com.chentongwei.core.user.dao.IUserDAO;
import com.chentongwei.core.user.entity.vo.user.UserVO;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 文章评论Biz
 */
@Service
public class ArticleCommentBiz {

    private static final Logger LOG = LogManager.getLogger("bizLog");

    /**
     * 将基本信息复制进去，alibaba不推荐用BeanUtils，所以用的BeanCopier
     */
    final BeanCopier articleCommentListCopier = BeanCopier.create(ArticleCommentListVO.class, ArticleCommentListVO.class, false);
    final BeanCopier articleCommentUserCopier = BeanCopier.create(UserVO.class, ArticleCommentUserVO.class, false);

    /** 父id */
    private static final Long ARTICLE_COMMENT_PID = 0L;

    @Autowired
    private IArticleCommentDAO articleCommentDAO;
    @Autowired
    private IUserDAO userDAO;
    @Autowired
    private IArticleDAO articleDAO;
    @Autowired
    private IBasicCache basicCache;

    /**
     * 文章评论/回复列表
     *
     * @param articleCommentListIO：参数
     * @return
     */
    public Result list(ArticleCommentListIO articleCommentListIO) {
        PageHelper.startPage(articleCommentListIO.getPageNum(), articleCommentListIO.getPageSize());
        //先查出所有一级评论（pid=0）
        List<ArticleCommentListVO> list = articleCommentDAO.list(articleCommentListIO.pid(ARTICLE_COMMENT_PID));
        //先遍历出无限极评论
        recursion(list);
        list.forEach(comment -> {
            List<ArticleCommentListVO> resultList = new ArrayList();
            dealComments(comment.getReplyComment(), resultList);
            comment.setReplyComment(resultList);
        });
        return ResultCreator.getSuccess(list);
    }

    /**
     * 文章评论/回复的保存
     *
     * @param articleCommentSaveIO：参数
     * @return
     */
    public Result save(ArticleCommentSaveIO articleCommentSaveIO) {
        //0.判断15s内是否评论过
        String str = basicCache.get(RedisEnum.getCheckCommentCount(articleCommentSaveIO.getUserId().toString()));
        if (StringUtils.isNotEmpty(str)) {
            LOG.info("15s内只能评论一次");
            throw new BussinessException(ArticleCommentMsgEnum.CHECK_COMMENT_COUNT);
        }
        // 1.articleId判断文章是否存在
        ArticleDetailVO articleDetailVO = articleDAO.getById(articleCommentSaveIO.getArticleId());
        CommonExceptionUtil.nullCheck(articleDetailVO);
        // 2.判断用户是否存在
        UserVO userVO = userDAO.getById(articleCommentSaveIO.getToUserId());
        CommonExceptionUtil.nullCheck(userVO);
        // 3.pid???
        articleCommentDAO.save(articleCommentSaveIO);
        //文章id + 1，算总评论数
        basicCache.increment(RedisEnum.getTucaoArticleCommentCount(articleCommentSaveIO.getArticleId().toString()), 1);
        //15S只能评论1次
        basicCache.set(RedisEnum.getCheckCommentCount(articleCommentSaveIO.getUserId().toString()), "1", TimeEnum.FIFTEEN_SECOND.value(), TimeUnit.SECONDS);
        return ResultCreator.getSuccess();
    }

    /**
     * 递归查询所有回复
     *
     * @param list：参数
     */
    private final void recursion(List<ArticleCommentListVO> list) {
        list.forEach(articleComment -> {
            List<ArticleCommentListVO> replyList = articleCommentDAO.list(new ArticleCommentListIO().pid(articleComment.getId()).articleId(articleComment.getArticleId()));
            articleComment.setReplyComment(replyList);
            //评论用户 from_user_id
            UserVO commentUser = userDAO.getById(articleComment.getFromUserId());
            articleCommentUserCopier.copy(commentUser, articleComment.getCommentUser(), null);
            //回复用户 to_user_id
            UserVO replyUser = userDAO.getById(articleComment.getToUserId());
            articleCommentUserCopier.copy(replyUser, articleComment.getReplyUser(), null);
            recursion(replyList);
        });
    }

    /**
     * 处理评论，将无限极评论转成二级
     *
     * @param childrenList：无限极评论1级评论的children
     * @param resultList：最终的结果
     */
    private final void dealComments(List<ArticleCommentListVO> childrenList, List<ArticleCommentListVO> resultList) {
        for (ArticleCommentListVO childrenComment : childrenList) {
            ArticleCommentListVO resultComment = new ArticleCommentListVO();
            articleCommentListCopier.copy(childrenComment, resultComment, null);
            resultComment.setReplyComment(null);
            resultList.add(resultComment);
            List<ArticleCommentListVO> children = childrenComment.getReplyComment();
            if (CollectionUtils.isNotEmpty(children)) {
                dealComments(children, resultList);
            }
        }
    }
}
