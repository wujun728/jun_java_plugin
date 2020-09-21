package com.chentongwei.controller.doutu;

import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Page;
import com.chentongwei.common.entity.Result;
import com.chentongwei.entity.doutu.io.ReportStatusIO;
import com.chentongwei.entity.doutu.vo.ReportVO;
import com.chentongwei.service.doutu.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 图片举报接口
 * 
 * @author TongWei.Chen 2017-5-22 20:09:59
 */
@RestController
@RequestMapping("/doutu/report")
public class ReportController {
	
	@Autowired
	private IReportService reportService;
	
	/**
	 * 查询已举报待审核状态的图片
	 * 
	 * @param page:分页
	 * @return
	 */
	@RequestMapping(value = "/listWaitCheck", method = RequestMethod.GET)
	public Result listWaitCheck(Page page) {
		List<ReportVO> list = reportService.listWaitCheck(page);
		return ResultCreator.getSuccess(list);
	}
	
	/**
	 * 举报审核接口
	 * 
	 * @param reportStatusIO：审核状态io
	 * @return
	 */
	@RequestMapping(value = "/editVerify", method = RequestMethod.POST)
	public Result editVerify(@Valid ReportStatusIO reportStatusIO) {
		return ResultCreator.getSuccess(reportService.editStatus(reportStatusIO));
	}
}
