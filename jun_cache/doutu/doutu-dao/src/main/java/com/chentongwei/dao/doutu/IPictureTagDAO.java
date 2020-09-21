package com.chentongwei.dao.doutu;

import java.util.List;

import com.chentongwei.entity.doutu.io.PictureTagIO;
import com.chentongwei.entity.doutu.io.PictureTagListIO;
import com.chentongwei.entity.doutu.vo.PictureTagListVO;
import org.apache.ibatis.annotations.Param;

/**
 * 图片标签DAO
 *
 * @author TongWei.Chen 2017-05-27 11:36:30
 */
public interface IPictureTagDAO {

	/**
	 * 根据标签名称模糊查询图片列表
	 * 
	 * @param pictureTagListIO: 标签名称
	 * @return
	 */
	List<PictureTagListVO> listByTagName(PictureTagListIO pictureTagListIO);
	
    /**
     * 保存图片标签
     *
     * @param pictureTag：图片标签IO
     * @return
     */
    boolean save(PictureTagIO pictureTag);

	/**
	 * 新增的width和height字段，默认值都是0，需要批量去获取width和height并更新到db
	 *
	 * @param width : 宽度
	 * @param height ：高度
	 * @param id ：图片id
	 */
	boolean update(@Param("width") double width, @Param("height") double height, @Param("id") long id);

}
