package com.chentongwei.controller.system;

import com.chentongwei.common.entity.Result;
import com.chentongwei.core.system.biz.report.ReportTypeBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 举报类型接口
 **/
@RestController
@RequestMapping("/system/reportType")
public class ReportTypeController {

    @Autowired
    private ReportTypeBiz reportTypeBiz;

    /**
     * 举报类型列表
     *
     * @return
     */
    @GetMapping("/list")
    public Result list() {
        return reportTypeBiz.list();
    }

}
