package com.chentongwei.controller.common;

import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Page;
import com.chentongwei.common.entity.Result;
import com.chentongwei.entity.common.io.UpdateLogSaveIO;
import com.chentongwei.service.common.IUpdateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 项目更新日志控制器
 *
 * @author TongWei.Chen 2017-07-11 14:25:15
 */
@RestController
@RequestMapping("/common/updateLog")
public class UpdateLogController {

    @Autowired
    private IUpdateLogService updateLogService;

    /**
     * 更新日志列表
     *
     * @param page:分页信息
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(Page page) {
        return ResultCreator.getSuccess(updateLogService.list(page));
    }

    /**
     * 保存更新日志
     *
     * @param updateLogSaveIO：内容
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@Valid UpdateLogSaveIO updateLogSaveIO) {
        return ResultCreator.getSuccess(updateLogService.save(updateLogSaveIO));
    }
}
