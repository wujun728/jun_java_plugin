package com.chentongwei.dao.doutu;

import java.util.List;

import com.chentongwei.entity.doutu.io.PictureIO;
import com.chentongwei.entity.doutu.io.PictureListAdminIO;
import com.chentongwei.entity.doutu.vo.PictureDetailVO;
import com.chentongwei.entity.doutu.vo.PictureListAdminVO;
import com.chentongwei.entity.doutu.vo.PictureListVO;
import org.apache.ibatis.annotations.Param;

/**
 * 图片DAO
 *
 * @author TongWei.Chen 2017-05-17 14:05:26
 */
public interface IPictureDAO {

	/**
	 * 根据id查看详情
	 * 
	 * @param id：id
	 * @return
	 */
	PictureDetailVO getByID(Long id);
	
	/**
	 * 为了缓存用而写，比如举报审核通过接口，需要删除对应redis的记录，
	 * 那里就用到了此方法，
	 * 为什么不用getByID呢？
	 * 因为在initDataService中是将PictureListVO缓存到了zset，所以要移除对象时必须用PictureListVO，而不是PictureDetailVO
	 * 
	 * @param reportId:举报id
	 * @return
	 */
	PictureListVO getByIDForCache(Long reportId);

    /**
     * 根据hash查看是否有存在的文件
     *
     * @param hex：hash值
     * @return
     */
    int existsHash(String hex);

    /**
     * 根据分类id查询图片信息（查全部，不分页，容器启动时按照不同分类加载到redis）
     *
     * @return
     */
    List<PictureListVO> listByCatalogId(Integer catalogId);

	/**
	 * 按照时间倒序排序--图片列表主页
	 *
	 * @return
	 */
	List<PictureListVO> list();

	/**
	 * 图片列表 -- admin端
	 *
	 * @param pictureListAdminIO:搜索条件
	 * @return
	 */
	List<PictureListAdminVO> listPictureAdmin(PictureListAdminIO pictureListAdminIO);

	/**
     * 保存图片
     *
     * @param pictureIO ： 图片IO
     * @return
     */
    boolean save(PictureIO pictureIO);

	/**
	 * 新增的width和height字段，默认值都是0，需要批量去获取width和height并更新到db
	 *
	 * @param width : 宽度
	 * @param height ：高度
	 * @param id ：图片id
	 */
    boolean update(@Param("width") double width, @Param("height") double height, @Param("id") long id);

	/**
	 * 作废
	 *
	 * @param id:图片id
	 * @param status:作废状态。0：已作废 1：正常
	 * @return
	 */
	boolean obsolete(@Param(value = "id") Long id, @Param(value = "status") int status);

    /**
     * 删除图片，举报并审核通过后需要删除图片
     *
     * @param id：图片id
     * @return
     */
    boolean delete(Long id);
}
