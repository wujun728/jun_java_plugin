package com.chentongwei.service.common.impl;

import com.chentongwei.common.entity.Page;
import com.chentongwei.common.handler.CommonExceptionHandler;
import com.chentongwei.dao.common.IFeedbackDAO;
import com.chentongwei.entity.common.io.FeedbackIO;
import com.chentongwei.entity.common.io.StatusIO;
import com.chentongwei.entity.common.vo.FeedbackVO;
import com.chentongwei.service.common.IFeedbackService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 反馈业务层接口实现
 *
 * @author TongWei.Chen 2017-06-21 14:52:19
 */
@Service
public class FeedbackServiceImpl implements IFeedbackService {

    private static final Logger LOG = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    @Autowired
    private IFeedbackDAO feedbackDAO;

    @Override
    public List<FeedbackVO> list(Page page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<FeedbackVO> list = feedbackDAO.list();
        return list;
    }

    @Override
    public boolean save(FeedbackIO feedbackIO) {
        boolean flag = feedbackDAO.save(feedbackIO);
        CommonExceptionHandler.flagCheck(flag);
        LOG.info("新增反馈成功");
        return true;
    }

    @Override
    public boolean editStatus(StatusIO statusIO) {
        boolean flag = feedbackDAO.editStatus(statusIO);
        CommonExceptionHandler.flagCheck(flag);
        LOG.info("处理反馈状态成功，状态值为{}", statusIO.getStatus());
        return true;
    }
}
