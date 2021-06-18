package com.chentongwei.controller.system;

import com.chentongwei.common.annotation.CategoryLog;
import com.chentongwei.common.annotation.DescLog;
import com.chentongwei.common.entity.Result;
import com.chentongwei.core.system.biz.report.ReportBiz;
import com.chentongwei.core.system.entity.io.report.ReportIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 举报记录接口
 **/
@RestController
@RequestMapping("/system/report")
@CategoryLog(menu = "吐槽模块")
public class ReportController {

    @Autowired
    private ReportBiz reportBiz;

    /**
     * 保存举报记录
     *
     * @param reportIO：参数
     * @return
     */
    @PostMapping("/save")
    @DescLog("举报文章")
    public Result save(@Valid ReportIO reportIO) {
        return reportBiz.save(reportIO);
    }
}
