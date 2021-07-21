package com.wf.ew.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wf.ew.common.JsonResult;
import com.wf.ew.common.PageResult;
import com.wf.ew.system.model.Role;
import com.wf.ew.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.Iterator;
import java.util.List;

@Api(value = "角色管理", tags = "role")
@RestController
@RequestMapping("${api.version}/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @RequiresPermissions("get:/v1/role")
    @ApiOperation(value = "查询所有角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "搜索关键字", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping()
    public PageResult<Role> list(String keyword) {
        List<Role> list = roleService.selectList(new EntityWrapper<Role>().orderBy("create_time", true));
        // 筛选结果
        if (keyword != null && !keyword.trim().isEmpty()) {
            keyword = keyword.trim();
            Iterator<Role> iterator = list.iterator();
            while (iterator.hasNext()) {
                Role next = iterator.next();
                boolean b = next.getRoleName().contains(keyword) || next.getComments().contains(keyword);
                if (!b) {
                    iterator.remove();
                }
            }
        }
        return new PageResult<>(list);
    }

    @RequiresPermissions("post:/v1/role")
    @ApiOperation(value = "添加角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "角色信息", required = true, dataType = "Role", paramType = "form"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping()
    public JsonResult add(Role role) {
        if (roleService.insert(role)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    @RequiresPermissions("put:/v1/role")
    @ApiOperation(value = "修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "角色信息", required = true, dataType = "Role", paramType = "form"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @PutMapping()
    public JsonResult update(Role role) {
        if (roleService.updateById(role)) {
            return JsonResult.ok("修改成功！");
        }
        return JsonResult.error("修改失败！");
    }

    @RequiresPermissions("delete:/v1/role/{id}")
    @ApiOperation(value = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Integer roleId) {
        if (roleService.deleteById(roleId)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }
}
