package com.chentongwei.controller.doutu;

import javax.validation.Valid;

import com.chentongwei.common.entity.Page;
import com.chentongwei.entity.doutu.vo.PictureListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.entity.doutu.io.PictureListCacheIO;
import com.chentongwei.service.doutu.IPictureService;

import java.util.List;

/**
 * 图片接口
 *
 * @author TongWei.Chen 2017-05-17 18:38:42
 */
@RestController
@RequestMapping("/doutu/picture")
public class PictureController {

    @Autowired
    private IPictureService pictureService;

    /**
     * 图片根据分类查询列表
     *
     * @param pictureListIO：参数
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@Valid PictureListCacheIO pictureListIO) {
        List<PictureListVO> pictures = pictureService.listByCatalogId(pictureListIO);
        return ResultCreator.getSuccess(pictures);
    }

    /**
     * 按照时间倒序排序--图片列表主页
     *
     * @param page : 分页信息
     * @return
     */
    @RequestMapping(value = "/listIndex", method = RequestMethod.GET)
    public Result listIndex(Page page) {
        List<PictureListVO> pictureList = pictureService.listIndex(page);
        return ResultCreator.getSuccess(pictureList);
    }
}
