package com.chentongwei.service.doutu;

import java.util.List;

import com.chentongwei.common.entity.Page;
import com.chentongwei.entity.doutu.io.ReportIO;
import com.chentongwei.entity.doutu.io.ReportStatusIO;
import com.chentongwei.entity.doutu.vo.ReportVO;

/**
 * 图片举报业务接口
 * 
 * @author TongWei.Chen 2017-5-22 20:06:41
 */
public interface IReportService {
	
	/**
	 * 查询已举报待审核状态的图片
	 * 
	 * @param page:分页
	 * @return
	 */
	List<ReportVO> listWaitCheck(Page page);
	
	/**
	 * 新增举报记录
	 * 
	 * @param reportIO：举报io
	 * @return
	 */
	boolean save(ReportIO reportIO);
	
	/**
	 * 编辑审核状态
	 * 
	 * @param reportStatusIO:审核状态io
	 * @return
	 */
	boolean editStatus(ReportStatusIO reportStatusIO);
}
