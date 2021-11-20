package com.jun.plugin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jun.plugin.common.utils.R;
import com.jun.plugin.service.CategoryService;

@RestController
@RequestMapping("/category")
@Api(description = "种类管理")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation("查询所有种类")
    @GetMapping("/queryAllCategory")
    public R queryAllCategory() {
        return R.ok().data(categoryService.queryAllCategory());
    }


}
