package com.chentongwei.service.doutu.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chentongwei.dao.doutu.IPictureTagDAO;
import com.chentongwei.entity.doutu.io.PictureTagListIO;
import com.chentongwei.entity.doutu.vo.PictureTagListVO;
import com.chentongwei.service.doutu.IPictureTagService;
import com.github.pagehelper.PageHelper;

/**
 * 图片标签业务接口实现
 *
 * @author TongWei.Chen 2017-05-27 11:44:08
 */
@Service
public class PictureTagServiceImpl implements IPictureTagService {

	@Autowired
	private IPictureTagDAO pictureTagDAO;
	
	@Override
	public List<PictureTagListVO> listByTagName(PictureTagListIO pictureTagListIO) {
		//start page
		PageHelper.startPage(pictureTagListIO.getPageNum(), pictureTagListIO.getPageSize());
		//query
		return pictureTagDAO.listByTagName(pictureTagListIO);
	}

}