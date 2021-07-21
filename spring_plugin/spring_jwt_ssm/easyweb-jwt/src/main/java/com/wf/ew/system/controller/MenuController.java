package com.wf.ew.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wf.ew.common.JsonResult;
import com.wf.ew.common.PageResult;
import com.wf.ew.system.model.Menu;
import com.wf.ew.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.Iterator;
import java.util.List;

@Api(value = "菜单管理", tags = "menu")
@RestController
@RequestMapping("${api.version}/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @RequiresPermissions("get:/v1/menu")
    @ApiOperation(value = "查询所有菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "搜索关键字", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping()
    public PageResult<Menu> list(String keyword) {
        List<Menu> list = menuService.selectList(new EntityWrapper<Menu>().orderBy("sort_number", true));
        for (Menu one : list) {
            for (Menu t : list) {
                if (one.getParentId() == t.getMenuId()) {
                    one.setParentName(t.getMenuName());
                }
            }
        }
        // 筛选结果
        if (keyword != null && !keyword.trim().isEmpty()) {
            keyword = keyword.trim();
            Iterator<Menu> iterator = list.iterator();
            while (iterator.hasNext()) {
                Menu next = iterator.next();
                boolean b = next.getMenuName().contains(keyword) || (next.getParentName() != null && next.getParentName().contains(keyword));
                if (!b) {
                    iterator.remove();
                }
            }
        }
        return new PageResult<>(list);
    }

    @RequiresPermissions("post:/v1/menu")
    @ApiOperation(value = "添加菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menu", value = "菜单信息", required = true, dataType = "Menu", paramType = "form"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping()
    public JsonResult add(Menu menu) {
        if (menuService.insert(menu)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    @RequiresPermissions("put:/v1/menu")
    @ApiOperation(value = "修改菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menu", value = "菜单信息", required = true, dataType = "Menu", paramType = "form"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @PutMapping()
    public JsonResult update(Menu menu) {
        if (menuService.updateById(menu)) {
            return JsonResult.ok("修改成功！");
        }
        return JsonResult.error("修改失败！");
    }

    @RequiresPermissions("delete:/v1/menu/{id}")
    @ApiOperation(value = "删除菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Integer menuId) {
        if (menuService.deleteById(menuId)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }
}
