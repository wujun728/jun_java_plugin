package com.chentongwei.service.doutu;

import com.chentongwei.entity.doutu.io.PictureUpvoteIO;
import com.chentongwei.entity.doutu.io.PictureUpvoteListIO;
import com.chentongwei.entity.doutu.vo.PictureUpvoteListVO;

import java.util.List;

/**
 * 图片点赞业务接口
 * 
 * @author TongWei.Chen 2017-5-30 18:20:34
 */
public interface IPictureUpvoteService {

	/**
	 * 查询某用户点赞过的图片
	 *
	 * @param pictureUpvoteListIO：用户id和分页信息
	 * @return
	 */
	List<PictureUpvoteListVO> listByUserID(PictureUpvoteListIO pictureUpvoteListIO);

	/**
	 * 点赞方法
	 * 
	 * @param pictureUpvote：点赞信息
	 * @return
	 */
	boolean save(PictureUpvoteIO pictureUpvote);
}
