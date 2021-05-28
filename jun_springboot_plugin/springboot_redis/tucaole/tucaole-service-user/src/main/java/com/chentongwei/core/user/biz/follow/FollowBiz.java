package com.chentongwei.core.user.biz.follow;

import com.chentongwei.cache.redis.IBasicCache;
import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.common.enums.constant.RedisEnum;
import com.chentongwei.common.exception.BussinessException;
import com.chentongwei.common.util.CommonExceptionUtil;
import com.chentongwei.core.user.dao.IFollowDAO;
import com.chentongwei.core.user.dao.IUserDAO;
import com.chentongwei.core.user.entity.io.follow.FollowListIO;
import com.chentongwei.core.user.entity.io.follow.FollowSaveIO;
import com.chentongwei.core.user.entity.vo.follow.FollowListVO;
import com.chentongwei.core.user.entity.vo.user.UserVO;
import com.chentongwei.core.user.enums.msg.FollowMsgEnum;
import com.github.pagehelper.PageHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 关注Biz
 */
@Service
public class FollowBiz {
    private static final Logger LOG = LogManager.getLogger("logBiz");

    @Autowired
    private IFollowDAO followDAO;
    @Autowired
    private IUserDAO userDAO;
    @Autowired
    private IBasicCache basicCache;

    /**
     * 我关注的人列表
     *
     * @param followListIO：用户id
     * @return
     */
    public Result listMyFollowers(FollowListIO followListIO) {
        PageHelper.startPage(followListIO.getPageNum(), followListIO.getPageSize());
        List<FollowListVO> followList = followDAO.listMyFollowers(followListIO);
        return ResultCreator.getSuccess(followList);
    }

    /**
     * 我关注的人列表
     *
     * @param followListIO：用户id
     * @return
     */
    public Result listMyFans(FollowListIO followListIO) {
        PageHelper.startPage(followListIO.getPageNum(), followListIO.getPageSize());
        List<FollowListVO> followList = followDAO.listMyFans(followListIO);
        return ResultCreator.getSuccess(followList);
    }

    /**
     * 保存关注记录
     *
     * @param followSaveIO：参数
     * @return
     */
    @Transactional
    public Result save(FollowSaveIO followSaveIO) {
        //判断被关注者是不是自己
        if (Objects.equals(followSaveIO.getUserId(), followSaveIO.getFollowUserId())) {
            LOG.info("被关注者不能是自己");
            throw new BussinessException(FollowMsgEnum.FOLLOW_USER_IS_SELF);
        }
        //判断被关注者follow_user_id是否存在
        UserVO userVO = userDAO.getById(followSaveIO.getFollowUserId());
        CommonExceptionUtil.nullCheck(userVO);
        //判断是否已经关注过了，若已经关注过了，则删除记录，代表取消关注
        Long id = followDAO.getIdByUserIdAndFollowId(followSaveIO.getUserId(), followSaveIO.getFollowUserId());
        if (null == id) {
            followDAO.save(followSaveIO);
            //保存到redis
            basicCache.increment(RedisEnum.getUserFollowCount(followSaveIO.getFollowUserId().toString()), 1);
        } else {
            followDAO.delete(id);
            //移除redis
            long count = basicCache.increment(RedisEnum.getUserFollowCount(followSaveIO.getFollowUserId().toString()), 0);
            if (count > 0L) {
                basicCache.increment(RedisEnum.getUserFollowCount(followSaveIO.getFollowUserId().toString()), -1);
            }
        }
        return ResultCreator.getSuccess();
    }

}
