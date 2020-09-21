package com.chentongwei.service.doutu;

import com.chentongwei.common.entity.Page;
import com.chentongwei.entity.doutu.io.PictureIO;
import com.chentongwei.entity.doutu.io.PictureListAdminIO;
import com.chentongwei.entity.doutu.io.PictureListCacheIO;
import com.chentongwei.entity.doutu.io.PictureSaveIO;
import com.chentongwei.entity.doutu.vo.PictureListAdminVO;
import com.chentongwei.entity.doutu.vo.PictureListCacheVO;
import com.chentongwei.entity.doutu.vo.PictureListVO;

import java.util.List;

/**
 * 图片业务层
 *
 * @author TongWei.Chen 2017-05-17 14:06:28
 */
public interface IPictureService {

    /**
     * 按照分类id查询图片信息
     *
     * @param pictureListIO 图片分类id，分页信息
     * @return
     */
    List<PictureListVO> listByCatalogId(PictureListCacheIO pictureListIO);

    /**
     * 查询图片信息（查全部，不分页，容器启动时按照不同分类加载到redis）
     *
     * @param pictureListIO 图片分类id，分页信息
     * @return
     */
    @Deprecated
    PictureListCacheVO listCache(PictureListCacheIO pictureListIO);

    /**
     * 图片列表 -- admin端
     *
     * @param pictureListAdminIO:搜索条件
     * @return
     */
    List<PictureListAdminVO> listPictureAdmin(PictureListAdminIO pictureListAdminIO);

    /**
     * 按照时间倒序排序--图片列表主页
     *
     * @param page : 分页信息
     * @return
     */
    List<PictureListVO> listIndex(Page page);

    /**
     * 查询图片是否存在
     *
     * @param hex：图片的hash值
     * @return
     */
    int existsHash(String hex);

    /**
     * 保存图片
     *
     * @param pictureSaveIO ： 图片IO
     * @return
     */
    boolean save(PictureSaveIO pictureSaveIO);

    /**
     * 保存图片 -- 爬虫用
     *
     * @param pictureIO ： 图片IO
     * @return
     */
    boolean saveForSpider(PictureIO pictureIO);

    /**
     * 作废
     *
     * @param id:图片id
     * @return
     */
    boolean obsolete(Long id);
}