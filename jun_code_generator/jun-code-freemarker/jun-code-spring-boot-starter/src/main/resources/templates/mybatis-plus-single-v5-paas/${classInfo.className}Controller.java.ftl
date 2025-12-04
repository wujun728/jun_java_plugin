<#if isWithPackage?exists && isWithPackage==true>package ${packageName}.controller;</#if>
<#if isAutoImport?exists && isAutoImport==true>
import ${packageName}.vo.${classInfo.className}Param;
import ${packageName}.dto.${classInfo.className}Dto;
import ${packageName}.mapper.${classInfo.className}Mapper;
import ${packageName}.entity.${classInfo.className}Entity;
import ${packageName}.service.${classInfo.className}Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
<#--import org.apache.shiro.authz.annotation.RequiresPermissions;-->
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.trueland.scrm.common.model.rpc.Rsp;
import net.trueland.scrm.common.model.vo.page.PageData;
import net.trueland.scrm.common.util.base.BeansCopyUtils;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
</#if>
/**
* @description ${classInfo.classComment}
* @author ${authorName}
* @date ${.now?string('yyyy-MM-dd')}
*/
@Api(tags = "${classInfo.classComment}-管理")
@Slf4j
@RestController
@RequestMapping("/${classInfo.className?uncap_first}")
@AllowedIdentity(AllowedIdentity.Identity.ANY)
public class ${classInfo.className}Controller {

    @Resource
    private ${classInfo.className}Service ${classInfo.className?uncap_first}Service;

    
    @Operation(description =  "${classInfo.classComment}-新增")
    @PostMapping("/add")
    //@RequiresPermissions("${classInfo.className?uncap_first}:add")
    public Rsp add(@Validated(${classInfo.className}Param.Create.class) @RequestBody ${classInfo.className}Param param) {
        return Rsp.success(${classInfo.className?uncap_first}Service.save(param) > 0);
    }
    
    @Operation(description =  "${classInfo.classComment}-删除")
    @DeleteMapping("/remove")
    //@RequiresPermissions("${classInfo.className?uncap_first}:remove")
    public Rsp delete(@Validated(${classInfo.className}Param.Delete.class) @RequestBody ${classInfo.className}Param param) {
        return Rsp.success(${classInfo.className?uncap_first}Service.remove(param) > 0);
    }

    @Operation(description =  "${classInfo.classComment}-删除")
    @DeleteMapping("/delete")
    //@RequiresPermissions("${classInfo.className?uncap_first}:delete")
    public Rsp delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        return Rsp.success(${classInfo.className?uncap_first}Service.removeByIds(ids));
    }


    @Operation(description =  "${classInfo.classComment}-更新")
    @PutMapping("/update")
    //@RequiresPermissions("${classInfo.className?uncap_first}:update")
    public Rsp update(@Validated(${classInfo.className}Param.Update.class) @RequestBody ${classInfo.className}Param param) {
        return Rsp.success(${classInfo.className?uncap_first}Service.saveOrUpdate(param) > 0);
    }
    


    @Operation(description =  "${classInfo.classComment}-查询单条")
    @RequestMapping(value = "/getOne",method = {RequestMethod.GET,RequestMethod.POST})
    //@RequiresPermissions("${classInfo.className?uncap_first}:getOne")
    public Rsp getOne(@RequestBody ${classInfo.className}Param param) {
        return Rsp.success(${classInfo.className?uncap_first}Service.getOne(param));
    }
    
    


    @Operation(description =  "${classInfo.classComment}-查询列表分页数据")
    @RequestMapping(value = "/listByPage",method = {RequestMethod.GET,RequestMethod.POST})
    //@RequiresPermissions("${classInfo.className?uncap_first}:listByPage")
    public Rsp queryPage(@RequestBody ${classInfo.className}Param ${classInfo.className?uncap_first}) {
        return Rsp.success(${classInfo.className?uncap_first}Service.queryPage(${classInfo.className?uncap_first}));
    }
    
    @Operation(description =  "${classInfo.classComment}-查询全部列表数据")
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST})
    //@RequiresPermissions("${classInfo.className?uncap_first}:list")
    public Rsp findListByPage(@RequestBody ${classInfo.className}Param ${classInfo.className?uncap_first}) {
        return Rsp.success(${classInfo.className?uncap_first}Service.listData(${classInfo.className?uncap_first}));
    }


}

