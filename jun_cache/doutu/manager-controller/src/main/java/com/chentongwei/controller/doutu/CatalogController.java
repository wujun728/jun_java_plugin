package com.chentongwei.controller.doutu;

import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.service.doutu.ICatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 图片分类接口
 *
 * @author TongWei.Chen 2017-6-14 15:48:16
 */
@RestController
@RequestMapping("/doutu/catalog")
public class CatalogController {

    @Autowired
    private ICatalogService catalogService;

    /**
     * 获取分类列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list() {
        return ResultCreator.getSuccess(catalogService.list());
    }
}
