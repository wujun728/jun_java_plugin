package com.cosmoplat.controller.sys;

import com.cosmoplat.common.aop.annotation.LogAnnotation;
import com.cosmoplat.common.utils.DataResult;
import com.cosmoplat.entity.sys.SysDept;
import com.cosmoplat.service.sys.DeptService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 部门管理
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@RequestMapping("/sys")
@RestController
public class DeptController {
    @Resource
    private DeptService deptService;

    @PostMapping("/dept")
    @LogAnnotation(title = "机构管理", action = "新增组织")
    @RequiresPermissions("sys:dept:add")
    public DataResult addDept(@RequestBody @Valid SysDept vo) {
        return DataResult.success(deptService.addDept(vo));
    }

    @DeleteMapping("/dept/{id}")
    @LogAnnotation(title = "机构管理", action = "删除组织")
    @RequiresPermissions("sys:dept:deleted")
    public DataResult deleted(@PathVariable("id") String id) {
        deptService.deleted(id);
        return DataResult.success();
    }

    @PutMapping("/dept")
    @LogAnnotation(title = "机构管理", action = "更新组织信息")
    @RequiresPermissions("sys:dept:update")
    public DataResult updateDept(@RequestBody SysDept vo) {
        if (StringUtils.isEmpty(vo.getId())) {
            return DataResult.fail("id不能为空");
        }
        deptService.updateDept(vo);
        return DataResult.success();
    }

    @GetMapping("/dept/{id}")
    @LogAnnotation(title = "机构管理", action = "查询组织详情")
    @RequiresPermissions("sys:dept:detail")
    public DataResult detailInfo(@PathVariable("id") String id) {
        return DataResult.success(deptService.getById(id));
    }

    @GetMapping("/dept/tree")
    @LogAnnotation(title = "机构管理", action = "树型组织列表")
    @RequiresPermissions(value = {"sys:user:list", "sys:user:update", "sys:user:add", "sys:dept:add", "sys:dept:update"}, logical = Logical.OR)
    public DataResult getTree(@RequestParam(required = false) String deptId) {
        return DataResult.success(deptService.deptTreeList(deptId));
    }

    @GetMapping("/depts")
    @LogAnnotation(title = "机构管理", action = "获取所有组织机构")
    @RequiresPermissions("sys:dept:list")
    public DataResult getDeptAll() {
        return DataResult.success(deptService.selectAll());
    }

}
