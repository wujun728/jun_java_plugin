package com.chentongwei.dao.doutu;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chentongwei.entity.doutu.io.ReportIO;
import com.chentongwei.entity.doutu.io.ReportStatusIO;
import com.chentongwei.entity.doutu.vo.ReportVO;

/**
 * 图片举报DAO
 * 
 * @author TongWei.Chen 2017-5-22 19:45:37
 */
public interface IReportDAO {
	
	/**
	 * 根据举报id查询图片分类id
	 * 
	 * @param id：举报id
	 * @return
	 */
	Integer getCatalogID(long id);
	
	/**
	 * 根据举报状态查询图片
	 * 
	 * @param status:举报状态
	 * @return
	 */
	List<ReportVO> listByStatus(Integer status);
	
	/**
	 * 检查图片是否是待审核状态
	 * 
	 * @param pictureId：图片id
	 * @return
	 */
	Integer checkReportStatus(Long id);
	
	/**
	 * 检查当前用户是否举报过此图片
	 * 
	 * @param pictureId：图片id
	 * @param userId：用户id
	 * @return
	 */
	int checkIsReport(@Param("pictureId") Long pictureId, @Param("userId") Long userId);
	
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
