package com.chentongwei.controller.common;

import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Page;
import com.chentongwei.common.entity.Result;
import com.chentongwei.service.common.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统日志
 *
 * @author TongWei.Chen 2017-06-02 15:58:12
 */
@RestController
@RequestMapping("/common/sysLog")
public class SysLogController {

    @Autowired
    private ISysLogService sysLogService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(Page page) {
        return ResultCreator.getSuccess(sysLogService.list(page));
    }
}
