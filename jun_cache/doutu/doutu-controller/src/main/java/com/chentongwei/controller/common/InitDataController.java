package com.chentongwei.controller.common;

import com.chentongwei.service.common.IInitDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * 初始化数据的控制器
 * 容器启动时自动执行的方法，无需开发者手动调用。
 *
 * @author TongWei.Chen 2017-05-19 14:29:39
 */
@RestController
@RequestMapping("/common/initData")
public class InitDataController {

    @Autowired
    private IInitDataService initDataService;

    /**
     * 将图片分类名称放到redis的list中
     */
    @PostConstruct
    public void initCatalog() {
        initDataService.initCatalog();
    }

    /**
     * 将图片按照不同分类放入redis的zset
     */
//    @PostConstruct
    public void initPicture() {
    	initDataService.initPicture();
    }

    /**
     * 将图片按照时间倒序排序放到redis的list
     */
//    @PostConstruct
    public void initIndex() {
        initDataService.initIndex();
    }
}
