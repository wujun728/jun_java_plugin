package com.chentongwei.controller.common;

import com.chentongwei.common.entity.Result;
import com.chentongwei.core.common.biz.RegionBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 省市区控制器
 */
@RestController
@RequestMapping("/common/region")
public class RegionController {

    @Autowired
    private RegionBiz regionBiz;

    /**
     * 省市区下拉框
     *
     * @return
     */
    @GetMapping("/list")
    public Result list() {
        return regionBiz.list();
    }
}
