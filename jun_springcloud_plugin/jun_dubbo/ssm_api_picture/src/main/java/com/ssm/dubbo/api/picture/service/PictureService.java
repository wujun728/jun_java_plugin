package com.ssm.dubbo.api.picture.service;

import com.ssm.dubbo.api.picture.entity.Picture;

import java.util.List;
import java.util.Map;

/**
 * @author 13
 * @project_name ssm-dubbo
 * @date 2019-01-02
 */
public interface PictureService {
    /**
     * 返回相应的数据集合
     *
     * @param map
     * @return
     */
    List<Picture> findPicture(Map<String, Object> map);

    /**
     * 数据数目
     *
     * @param map
     * @return
     */
    Long getTotalPicture(Map<String, Object> map);

    /**
     * 添加图片
     *
     * @param picture
     * @return
     */
    int addPicture(Picture picture);

    /**
     * 修改图片
     *
     * @param picture
     * @return
     */
    int updatePicture(Picture picture);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int deletePicture(String id);

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    Picture findById(String id);
}
