package com.chentongwei.dao.doutu;

import com.chentongwei.entity.doutu.io.PictureUpvoteListIO;
import com.chentongwei.entity.doutu.vo.PictureUpvoteListVO;
import org.apache.ibatis.annotations.Param;

import com.chentongwei.entity.doutu.io.PictureUpvoteIO;

import java.util.List;

/**
 * 图片点赞DAO
 * 
 * @author TongWei.Chen 2017-5-30 15:02:03
 */
public interface IPictureUpvoteDAO {
	
	/**
	 * 查询某用户是否点赞了某图片
	 * 
	 * @param userId：用户id
	 * @param pictureId：图片id
	 * @return
	 */
	int findByUserIdAndPicId(@Param("userId") Long userId, @Param("pictureId") Long pictureId);

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
	
	/**
	 * 删除某图片的点赞记录
	 * 
	 * @param pictureId：图片id
	 * @return
	 */
	boolean deleteByPictureId(Long pictureId);
	
}
