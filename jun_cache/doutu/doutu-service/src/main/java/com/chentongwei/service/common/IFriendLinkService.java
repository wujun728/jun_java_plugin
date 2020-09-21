package com.chentongwei.service.common;

import com.chentongwei.entity.common.io.FriendLinkIO;
import com.chentongwei.entity.common.vo.FriendLinkVO;

import java.util.List;

/**
 * 友情链接业务接口
 *
 * @author TongWei.Chen 2017-05-31 19:07:42
 */
public interface IFriendLinkService {

    /**
     * 根据id查看详情
     *
     * @param id：友链id
     * @return
     */
    FriendLinkVO getByID(Integer id);

    /**
     * 友链列表查看
     *
     * @return
     */
    List<FriendLinkVO> list();

    /**
     * 新增友链
     *
     * @param friendLinkIO：友链IO
     * @return
     */
    boolean save(FriendLinkIO friendLinkIO);

    /**
     * 修改友链
     *
     * @param friendLinkIO：友链IO
     * @return
     */
    boolean edit(FriendLinkIO friendLinkIO);

    /**
     * 根据id删除友链
     *
     * @param id：友链id
     * @return
     */
    boolean delete(Integer id);
}