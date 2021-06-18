package com.ssm.dubbo.service.picture.dao;

import com.ssm.dubbo.api.picture.entity.Picture;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 13
 * @project_name ssm-dubbo
 * @date 2019-01-02
 */
@Repository
public interface PictureDao {
    /**
     * 返回相应的数据集合
     *
     * @param map
     * @return
     */
    List<Picture> findPictures(Map<String, Object> map);

    /**
     * 数据数目
     *
     * @param map
     * @return
     */
    Long getTotalPictures(Map<String, Object> map);

    /**
     * 添加图片
     *
     * @param picture
     * @return
     */
    int insertPicture(Picture picture);

    /**
     * 修改图片
     *
     * @param picture
     * @return
     */
    int updPicture(Picture picture);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int delPicture(String id);

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    Picture findPictureById(String id);
}
