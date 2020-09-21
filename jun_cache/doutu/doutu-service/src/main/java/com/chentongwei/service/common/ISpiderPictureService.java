package com.chentongwei.service.common;

import com.chentongwei.entity.common.io.SpiderPictureIO;

/**
 * 爬虫--图片
 *
 * @author TongWei.Chen 2017-06-21 11:29:48
 */
public interface ISpiderPictureService {

    /**
     * 爬http://www.xuanbiaoqing.com的数据
     *
     * http://www.xuanbiaoqing.com/search/listajax/?page=?&keyword=?
     */
     void xuanBiaoQing(SpiderPictureIO spiderPictureIO);

}
