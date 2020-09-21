package com.chentongwei.controller.doutu;

import com.chentongwei.common.annotation.CategoryLog;
import com.chentongwei.common.annotation.DescLog;
import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.entity.doutu.io.ReportIO;
import com.chentongwei.service.doutu.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 图片举报接口
 * 
 * @author TongWei.Chen 2017-5-22 20:09:59
 */
@RestController
@RequestMapping("/doutu/report")
@CategoryLog(menu = "斗图大会", subMenu = "图片举报")
public class ReportController {
	
	@Autowired
	private IReportService reportService;

	/**
	 * 新增举报记录
	 * 
	 * @param reportIO：举报io
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@DescLog("举报了图片")
	public Result save(@Valid ReportIO reportIO) {
		return ResultCreator.getSuccess(reportService.save(reportIO));
	}
}
