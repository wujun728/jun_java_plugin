package com.chentongwei.service.common.impl;

import com.chentongwei.common.handler.CommonExceptionHandler;
import com.chentongwei.dao.common.IFriendLinkDAO;
import com.chentongwei.entity.common.io.FriendLinkIO;
import com.chentongwei.entity.common.vo.FriendLinkVO;
import com.chentongwei.service.common.IFriendLinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友情链接业务接口实现
 *
 * @author TongWei.Chen 2017-05-31 19:09:35
 */
@Service
public class FriendLinkServiceImpl implements IFriendLinkService {
    private static final Logger LOG = LoggerFactory.getLogger(FriendLinkServiceImpl.class);

    @Autowired
    private IFriendLinkDAO friendLinkDAO;

    @Override
    public FriendLinkVO getByID(Integer id) {
        FriendLinkVO friendLink = friendLinkDAO.getByID(id);
        CommonExceptionHandler.nullCheck(friendLink);
        return friendLink;
    }

    @Override
    public List<FriendLinkVO> list() {
        return friendLinkDAO.list();
    }

    @Override
    public boolean save(FriendLinkIO friendLinkIO) {
        boolean flag = friendLinkDAO.save(friendLinkIO);
        CommonExceptionHandler.flagCheck(flag);
        LOG.info("新增友链成功");
        return true;
    }

    @Override
    public boolean edit(FriendLinkIO friendLinkIO) {
        FriendLinkVO friendLink = friendLinkDAO.getByID(friendLinkIO.getId());
        CommonExceptionHandler.nullCheck(friendLink);
        boolean flag = friendLinkDAO.edit(friendLinkIO);
        CommonExceptionHandler.flagCheck(flag);
        LOG.info("编辑友链成功");
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        boolean flag = friendLinkDAO.delete(id);
        CommonExceptionHandler.flagCheck(flag);
        LOG.info("删除友链成功");
        return true;
    }
}