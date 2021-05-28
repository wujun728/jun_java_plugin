package com.chentongwei.core.user.dao;

import com.chentongwei.core.user.entity.io.follow.FollowListIO;
import com.chentongwei.core.user.entity.io.follow.FollowSaveIO;
import com.chentongwei.core.user.entity.vo.follow.FollowListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 关注DAO
 */
public interface IFollowDAO {

    /**
     * 我关注的人列表
     *
     * @param followListIO：用户id
     * @return
     */
    List<FollowListVO> listMyFollowers(FollowListIO followListIO);

    /**
     * 关注我的人列表
     *
     * @param followListIO：用户id
     * @return
     */
    List<FollowListVO> listMyFans(FollowListIO followListIO);

    /**
     * 根据关注者id和被关注者id去查询记录
     *
     * @param userId：关注者id
     * @param followUserId：被关注者id
     * @return
     */
    Long getIdByUserIdAndFollowId(@Param("userId") Long userId, @Param("followUserId") Long followUserId);

    /**
     * 保存关注记录
     *
     * @param followSaveIO：关注
     * @return
     */
    boolean save(FollowSaveIO followSaveIO);

    /**
     * 根据id删除记录
     *
     * @param id：主键id
     * @return
     */
    boolean delete(Long id);

}
