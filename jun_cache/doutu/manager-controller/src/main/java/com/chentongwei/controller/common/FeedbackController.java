package com.chentongwei.controller.common;

import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Page;
import com.chentongwei.common.entity.Result;
import com.chentongwei.entity.common.io.StatusIO;
import com.chentongwei.service.common.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 反馈接口
 *
 * @author TongWei.Chen 2017-06-21 14:55:15
 */
@RestController
@RequestMapping("/common/feedback")
public class FeedbackController {

    @Autowired
    private IFeedbackService feedbackService;

    /**
     * 反馈列表查询
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(Page page) {
        return ResultCreator.getSuccess(feedbackService.list(page));
    }

    /**
     * 修改处理状态
     *
     * @param statusIO
     * @return
     */
    @RequestMapping(value = "/editStatus", method = RequestMethod.POST)
    public Result editStatus(@Valid StatusIO statusIO) {
        return ResultCreator.getSuccess(feedbackService.editStatus(statusIO));
    }

}
