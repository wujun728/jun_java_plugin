package com.chentongwei.controller.doutu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.entity.doutu.io.PictureTagListIO;
import com.chentongwei.entity.doutu.vo.PictureTagListVO;
import com.chentongwei.service.doutu.IPictureTagService;

/**
 * 图片标签的控制层接口
 * 
 * @author TongWei.Chen 2017年5月29日22:43:39
 */
@RestController
@RequestMapping("/doutu/pictureTag")
public class PictureTagController {
	
	@Autowired
	private IPictureTagService pictureTagService;
	
	/**
	 * 根据标签名称模糊查询图片列表
	 * 
	 * @param pictureTagListIO: 标签名称
	 * @return
	 */
	@RequestMapping(value = "/listByTagName", method = RequestMethod.GET)
	public Result listByTagName(PictureTagListIO pictureTagListIO) {
		List<PictureTagListVO> list = pictureTagService.listByTagName(pictureTagListIO);
		return ResultCreator.getSuccess(list);
	}
}
