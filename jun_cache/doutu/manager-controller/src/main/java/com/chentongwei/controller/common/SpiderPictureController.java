package com.chentongwei.controller.common;

import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.entity.common.io.SpiderPictureIO;
import com.chentongwei.service.common.ISpiderPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 爬虫--图片
 *
 * @author TongWei.Chen 2017-06-21 11:48:14
 */
@RestController
@RequestMapping(value = "/common/siderPicture")
public class SpiderPictureController {

    @Autowired
    private ISpiderPictureService spiderPictureService;

    @RequestMapping(value = "/xuanBiaoQing", method = RequestMethod.POST)
    public Result xuanBiaoQing(@Valid SpiderPictureIO spiderPictureIO) {
        spiderPictureService.xuanBiaoQing(spiderPictureIO);
        return ResultCreator.getSuccess();
    }

}
