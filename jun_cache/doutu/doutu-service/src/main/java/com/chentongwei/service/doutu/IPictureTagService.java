package com.chentongwei.service.doutu;

import java.util.List;

import com.chentongwei.entity.doutu.io.PictureTagListIO;
import com.chentongwei.entity.doutu.vo.PictureTagListVO;

/**
 * 图片标签业务接口
 *
 * @author TongWei.Chen 2017-05-27 11:42:57
 */
public interface IPictureTagService {
	
	/**
	 * 根据标签名称模糊查询图片列表
	 * 
	 * @param pictureTagListIO: 标签名称
	 * @return
	 */
	List<PictureTagListVO> listByTagName(PictureTagListIO pictureTagListIO);
}
