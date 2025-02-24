package com.company.project.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.company.project.common.exception.BusinessException;
import com.company.project.common.utils.AssertUtil;
import com.company.project.entity.SysDictDetailEntity;
import com.company.project.entity.SysDictEntity;
import com.company.project.service.SysDictDetailService;
import com.company.project.service.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 字典管理
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Api(tags = "字典管理")
@RestController
@RequestMapping("/sysDict")
public class SysDictController {
    @Resource
    private SysDictService sysDictService;
    @Resource
    private SysDictDetailService sysDictDetailService;


    @ApiOperation(value = "新增")
    @PostMapping("/add")
    @SaCheckPermission("sysDict:add")
    public void add(@RequestBody SysDictEntity sysDict) {
        AssertUtil.isStringNotBlank(sysDict.getName(), "字典名称不能为空");
        SysDictEntity q = sysDictService.getOne(Wrappers.<SysDictEntity>lambdaQuery().eq(SysDictEntity::getName, sysDict.getName()));
        AssertUtil.isNull(q, "字典名称已存在");
        sysDictService.save(sysDict);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/delete")
    @SaCheckPermission("sysDict:delete")
    public void delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        sysDictService.removeByIds(ids);
        //删除detail
        sysDictDetailService.remove(Wrappers.<SysDictDetailEntity>lambdaQuery().in(SysDictDetailEntity::getDictId, ids));
    }

    @ApiOperation(value = "更新")
    @PutMapping("/update")
    @SaCheckPermission("sysDict:update")
    public void update(@RequestBody SysDictEntity sysDict) {
        AssertUtil.isStringNotBlank(sysDict.getName(), "字典名称不能为空");
        sysDictService.updateById(sysDict);
    }

    @ApiOperation(value = "查询分页数据")
    @PostMapping("/listByPage")
    @SaCheckPermission("sysDict:list")
    public IPage<SysDictEntity> findListByPage(@RequestBody SysDictEntity sysDict) {
        LambdaQueryWrapper<SysDictEntity> queryWrapper = Wrappers.lambdaQuery();
        //查询条件示例
        if (!StringUtils.isEmpty(sysDict.getName())) {
            queryWrapper.like(SysDictEntity::getName, sysDict.getName());
            queryWrapper.or();
            queryWrapper.like(SysDictEntity::getRemark, sysDict.getName());
        }
        queryWrapper.orderByAsc(SysDictEntity::getName);
        return sysDictService.page(sysDict.getQueryPage(), queryWrapper);
    }

}
