package cn.classg.controller;

import cn.classg.common.utils.R;
import cn.classg.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
