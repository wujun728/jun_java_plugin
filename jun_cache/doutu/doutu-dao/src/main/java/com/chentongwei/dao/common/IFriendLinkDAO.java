package com.chentongwei.dao.common;

import com.chentongwei.entity.common.io.FriendLinkIO;
import com.chentongwei.entity.common.vo.FriendLinkVO;

import java.util.List;

/**
 * 友情链接DAO
 *
 * @author TongWei.Chen 2017-05-31 18:54:22
 */
public interface IFriendLinkDAO {

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
