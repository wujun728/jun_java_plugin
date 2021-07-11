package cc.mrbird.febs.system.controller;


import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.entity.Strings;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.system.entity.Dept;
import cc.mrbird.febs.system.service.IDeptService;
import com.wuwenze.poi.ExcelKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author MrBird
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("dept")
public class DeptController {

    private final IDeptService deptService;

    @GetMapping("select/tree")
    @ControllerEndpoint(exceptionMessage = "获取部门树失败")
    public FebsResponse getDeptTree() throws FebsException {
        return new FebsResponse().success().data(deptService.findDept());
    }

    @GetMapping("tree")
    @ControllerEndpoint(exceptionMessage = "获取部门树失败")
    public FebsResponse getDeptTree(Dept dept) throws FebsException {
        return new FebsResponse().success().data(deptService.findDept(dept));
    }

    @PostMapping
    @RequiresPermissions("dept:add")
    @ControllerEndpoint(operation = "新增部门", exceptionMessage = "新增部门失败")
    public FebsResponse addDept(@Valid Dept dept) {
        deptService.createDept(dept);
        return new FebsResponse().success();
    }

    @GetMapping("delete/{deptIds}")
    @RequiresPermissions("dept:delete")
    @ControllerEndpoint(operation = "删除部门", exceptionMessage = "删除部门失败")
    public FebsResponse deleteDept(@NotBlank(message = "{required}") @PathVariable String deptIds) throws FebsException {
        deptService.deleteDept(StringUtils.split(deptIds, Strings.COMMA));
        return new FebsResponse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("dept:update")
    @ControllerEndpoint(operation = "修改部门", exceptionMessage = "修改部门失败")
    public FebsResponse updateDept(@Valid Dept dept) throws FebsException {
        deptService.updateDept(dept);
        return new FebsResponse().success();
    }

    @GetMapping("excel")
    @RequiresPermissions("dept:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(Dept dept, QueryRequest request, HttpServletResponse response) throws FebsException {
        ExcelKit.$Export(Dept.class, response)
                .downXlsx(deptService.findDept(dept, request), false);
    }
}
