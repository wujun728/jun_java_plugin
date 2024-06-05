package com.jun.plugin.app1.controller;
import com.jun.plugin.app1.vo.BizTestVo;
import com.jun.plugin.app1.dto.BizTestDto;
import com.jun.plugin.app1.mapper.BizTestMapper;
import com.jun.plugin.app1.entity.BizTestEntity;
import com.jun.plugin.app1.service.BizTestService;
//import com.bjc.lcp.common.cnt.enums.CntTableNameEnum;
//import com.bjc.lcp.common.cnt.service.CntService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.servlet.ModelAndView;
import com.jun.plugin.common.Result;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
* @description 客户信息
* @author Wujun
* @date 2024-03-08
*/
@Api(tags = "客户信息")
@Slf4j
@RestController
@RequestMapping({"/","/api"})
public class BizTestController {

    @Resource
    private BizTestService bizTestService;
    
    @Resource
    private BizTestMapper bizTestMapper;
    
    @ApiOperation(value = "客户信息-新增")
    @PostMapping("/bizTest/add")
    //@RequiresPermissions("bizTest:add")
    public Result add(@Validated(BizTestVo.Create.class) @RequestBody BizTestVo vo) {
    	BizTestDto dto = new BizTestDto();
    	BeanUtils.copyProperties(vo, dto);
        if (ObjectUtils.isEmpty(dto.getMoney())) {
            return Result.fail("参数[money]不能为空");
        }
        if (ObjectUtils.isEmpty(dto.getFullname())) {
            return Result.fail("参数[fullname]不能为空");
        }
        if (ObjectUtils.isEmpty(dto.getCustype())) {
            return Result.fail("参数[custype]不能为空");
        }
        if (ObjectUtils.isEmpty(dto.getState())) {
            return Result.fail("参数[state]不能为空");
        }
        if (ObjectUtils.isEmpty(dto.getRemark())) {
            return Result.fail("参数[remark]不能为空");
        }
        LambdaQueryWrapper<BizTestEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(BizTestEntity::getId, dto.getId());
        List<BizTestEntity> list = bizTestService.list(queryWrapper);
        if (list.size() > 0) {
            return Result.fail("数据已存在");
        }
        BizTestEntity entity = new BizTestEntity();
        
        BeanUtils.copyProperties(dto, entity);
        return Result.success(bizTestService.save(entity));
    }
    
    @ApiOperation(value = "客户信息-删除")
    @DeleteMapping("/bizTest/remove")
    //@RequiresPermissions("bizTest:remove")
    public Result delete(@Validated(BizTestVo.Delete.class) @RequestBody BizTestVo vo) {
    	BizTestDto dto = new BizTestDto();
    	BeanUtils.copyProperties(vo, dto);
         if (ObjectUtils.isEmpty(dto.getId())) {
              return Result.fail("参数[id]不能为空");
         }
        LambdaQueryWrapper<BizTestEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(BizTestEntity::getId, dto.getId());
        return Result.success(bizTestService.remove(queryWrapper));
    }

    @ApiOperation(value = "客户信息-删除")
    @DeleteMapping("/bizTest/delete")
    //@RequiresPermissions("bizTest:delete")
    public Result delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        return Result.success(bizTestService.removeByIds(ids));
    }


    @ApiOperation(value = "客户信息-更新")
    @PutMapping("/bizTest/update")
    //@RequiresPermissions("bizTest:update")
    public Result update(@Validated(BizTestVo.Update.class) @RequestBody BizTestVo vo) {
    	BizTestDto dto = new BizTestDto();
    	BeanUtils.copyProperties(vo, dto);
         if (ObjectUtils.isEmpty(dto.getId())) {
              return Result.fail("参数[id]不能为空");
         }
        LambdaQueryWrapper<BizTestEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(BizTestEntity::getId, dto.getId());
        BizTestEntity entity = bizTestService.getOne(queryWrapper);;
        if (entity == null) {
            //return Result.fail("数据不存在");
            entity = new BizTestEntity();
        }
        BeanUtils.copyProperties(dto, entity);
        return Result.success(bizTestService.saveOrUpdate(entity));
    }
    


    @ApiOperation(value = "客户信息-查询单条")
    @RequestMapping(value = "/bizTest/getOne",method = {RequestMethod.GET,RequestMethod.POST})
    //@RequiresPermissions("bizTest:getOne")
    public Result getOne(@RequestBody BizTestVo vo) {
    	BizTestDto dto = new BizTestDto();
    	BeanUtils.copyProperties(vo, dto);
         if (ObjectUtils.isEmpty(dto.getId())) {
              return Result.fail("参数[id]不能为空");
         }
        LambdaQueryWrapper<BizTestEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(BizTestEntity::getId, dto.getId());
        BizTestEntity entity = bizTestService.getOne(queryWrapper);;
        return Result.success(entity);
    }
    
    


    @ApiOperation(value = "客户信息-查询列表分页数据")
    @RequestMapping(value = "/bizTest/listByPage",method = {RequestMethod.GET,RequestMethod.POST})
    //@RequiresPermissions("bizTest:listByPage")
    public Result listByPage(@RequestBody BizTestVo bizTest) {
        Page page = new Page(bizTest.getPage(), bizTest.getLimit());
        BizTestDto dto = new BizTestDto();
    	BeanUtils.copyProperties(bizTest, dto);
        LambdaQueryWrapper<BizTestEntity> queryWrapper = Wrappers.lambdaQuery();
        if (!ObjectUtils.isEmpty(bizTest.getId())) {
            queryWrapper.eq(BizTestEntity::getId, dto.getId());
        }
        if (!ObjectUtils.isEmpty(bizTest.getCusname())) {
            queryWrapper.eq(BizTestEntity::getCusname, dto.getCusname());
        }
        if (!ObjectUtils.isEmpty(bizTest.getMoney())) {
            queryWrapper.eq(BizTestEntity::getMoney, dto.getMoney());
        }
        if (!ObjectUtils.isEmpty(bizTest.getCusdesc())) {
            queryWrapper.eq(BizTestEntity::getCusdesc, dto.getCusdesc());
        }
        if (!ObjectUtils.isEmpty(bizTest.getFullname())) {
            queryWrapper.eq(BizTestEntity::getFullname, dto.getFullname());
        }
        if (!ObjectUtils.isEmpty(bizTest.getCreateDate())) {
            queryWrapper.eq(BizTestEntity::getCreateDate, dto.getCreateDate());
        }
        if (!ObjectUtils.isEmpty(bizTest.getCustype())) {
            queryWrapper.eq(BizTestEntity::getCustype, dto.getCustype());
        }
        if (!ObjectUtils.isEmpty(bizTest.getState())) {
            queryWrapper.eq(BizTestEntity::getState, dto.getState());
        }
        if (!ObjectUtils.isEmpty(bizTest.getRemark())) {
            queryWrapper.eq(BizTestEntity::getRemark, dto.getRemark());
        }
        IPage<BizTestEntity> iPage = bizTestService.page(page, queryWrapper);
        return Result.success(iPage);
    }
    
    @ApiOperation(value = "客户信息-查询全部列表数据")
    @RequestMapping(value = "/bizTest/list",method = {RequestMethod.GET,RequestMethod.POST})
    //@RequiresPermissions("bizTest:list")
    public Result findListByPage(@RequestBody BizTestVo bizTest) {
        LambdaQueryWrapper<BizTestEntity> queryWrapper = Wrappers.lambdaQuery();
        if (!ObjectUtils.isEmpty(bizTest.getId())) {
            queryWrapper.eq(BizTestEntity::getId, bizTest.getId());
        }
        if (!ObjectUtils.isEmpty(bizTest.getCusname())) {
            queryWrapper.eq(BizTestEntity::getCusname, bizTest.getCusname());
        }
        if (!ObjectUtils.isEmpty(bizTest.getMoney())) {
            queryWrapper.eq(BizTestEntity::getMoney, bizTest.getMoney());
        }
        if (!ObjectUtils.isEmpty(bizTest.getCusdesc())) {
            queryWrapper.eq(BizTestEntity::getCusdesc, bizTest.getCusdesc());
        }
        if (!ObjectUtils.isEmpty(bizTest.getFullname())) {
            queryWrapper.eq(BizTestEntity::getFullname, bizTest.getFullname());
        }
        if (!ObjectUtils.isEmpty(bizTest.getCreateDate())) {
            queryWrapper.eq(BizTestEntity::getCreateDate, bizTest.getCreateDate());
        }
        if (!ObjectUtils.isEmpty(bizTest.getCustype())) {
            queryWrapper.eq(BizTestEntity::getCustype, bizTest.getCustype());
        }
        if (!ObjectUtils.isEmpty(bizTest.getState())) {
            queryWrapper.eq(BizTestEntity::getState, bizTest.getState());
        }
        if (!ObjectUtils.isEmpty(bizTest.getRemark())) {
            queryWrapper.eq(BizTestEntity::getRemark, bizTest.getRemark());
        }
        List<BizTestEntity> list = bizTestService.list(queryWrapper);
        return Result.success(list);
    }


}

