package com.chentongwei.controller.doutu;

import com.chentongwei.common.annotation.CategoryLog;
import com.chentongwei.common.annotation.DescLog;
import com.chentongwei.entity.doutu.io.PictureUpvoteListIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.entity.doutu.io.PictureUpvoteIO;
import com.chentongwei.service.doutu.IPictureUpvoteService;

import javax.validation.Valid;

/**
 * 图片点赞接口
 * 
 * @author TongWei.Chen 2017-5-30 18:28:34
 */
@RestController
@RequestMapping("/doutu/pictureUpvote")
@CategoryLog(menu = "斗图大会", subMenu = "图片点赞")
public class PictureUpvoteController {
	
	@Autowired
	private IPictureUpvoteService pictureUpvoteService;

	/**
	 * 查询某用户点赞了哪些图片
	 *
	 * @param pictureUpvoteListIO：用户id和分页信息
	 * @return
	 */
	@RequestMapping(value = "/listByUserID", method = RequestMethod.GET)
	public Result listByUserID(@Valid PictureUpvoteListIO pictureUpvoteListIO) {
		return ResultCreator.getSuccess(pictureUpvoteService.listByUserID(pictureUpvoteListIO));
	}

	/**
	 * 图片点赞功能
	 * 
	 * @param pictureUpvote：点赞信息
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
    @DescLog("点赞了图片")
	public Result save(@Valid PictureUpvoteIO pictureUpvote) {
		return ResultCreator.getSuccess(pictureUpvoteService.save(pictureUpvote));
	}
}
