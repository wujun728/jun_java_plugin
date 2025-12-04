<#if isWithPackage?exists && isWithPackage==true>package ${packageName}.service.impl;</#if>
<#if isAutoImport?exists && isAutoImport==true>
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${packageName}.entity.${classInfo.className}Entity;
import ${packageName}.mapper.${classInfo.className}Mapper;
import ${packageName}.service.${classInfo.className}Service;

</#if>
/**
 * @description ${classInfo.classComment}服务层实现
 * @author ${authorName}
 * @date ${.now?string('yyyy-MM-dd')}
 */
@Service
public class ${classInfo.className}ServiceImpl extends ServiceImpl<${classInfo.className}Mapper, ${classInfo.className}Entity> implements ${classInfo.className}Service {


    @Resource
    private ${classInfo.className}Mapper ${classInfo.className?uncap_first}Mapper;




    @LogAOP(title = "${classInfo.classComment}-新增", isThrow = false)
    public int add(${classInfo.className}Param param) {
<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
<#list classInfo.fieldList as fieldItem >
<#if fieldItem.nullable==false>
        if (ObjectUtils.isEmpty(param.get${fieldItem.fieldName?cap_first}())) {
            throw new BuException("参数[${fieldItem.fieldName}]不能为空");
        }
</#if>
</#list>
</#if>
        LambdaQueryWrapper<${classInfo.className}Entity> queryWrapper = Wrappers.lambdaQuery();
<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
<#list classInfo.fieldList as fieldItem >
<#if fieldItem.isPrimaryKey==true>
        queryWrapper.eq(${classInfo.className}Entity::get${fieldItem.fieldName?cap_first}, param.get${fieldItem.fieldName?cap_first}());
</#if>
</#list>
</#if>
        List<${classInfo.className}Entity> list = ${classInfo.className?uncap_first}Service.list(queryWrapper);
        if (list.size() > 0) {
            throw new BuException("数据已存在");
        }
        ${classInfo.className}Entity entity = new ${classInfo.className}Entity();
        BeanUtils.copyProperties(param, entity);
        return ${classInfo.className?uncap_first}Service.save(entity);
    }

    @LogAOP(title = "${classInfo.classComment}-删除", isThrow = false)
    public int delete(${classInfo.className}Param param) {
<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
<#list classInfo.fieldList as fieldItem >
<#if fieldItem.isPrimaryKey==true>
         if (ObjectUtils.isEmpty(param.get${fieldItem.fieldName?cap_first}())) {
              throw new BuException("参数[${fieldItem.fieldName}]不能为空");
         }
</#if>
</#list>
</#if>
        LambdaQueryWrapper<${classInfo.className}Entity> queryWrapper = Wrappers.lambdaQuery();
<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
<#list classInfo.fieldList as fieldItem >
<#if fieldItem.isPrimaryKey==true>
        queryWrapper.eq(${classInfo.className}Entity::get${fieldItem.fieldName?cap_first}, param.get${fieldItem.fieldName?cap_first}());
</#if>
</#list>
</#if>
        return ${classInfo.className?uncap_first}Service.remove(queryWrapper);
    }

    @LogAOP(title = "${classInfo.classComment}-删除", isThrow = false)
    public int delete(List<String> ids) {
        return ${classInfo.className?uncap_first}Service.removeByIds(ids);
    }


    @LogAOP(title = "${classInfo.classComment}-更新", isThrow = false)
    public int update(${classInfo.className}Param param) {
<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
<#list classInfo.fieldList as fieldItem >
<#if fieldItem.isPrimaryKey==true>
         if (ObjectUtils.isEmpty(param.get${fieldItem.fieldName?cap_first}())) {
              throw new BuException("参数[${fieldItem.fieldName}]不能为空");
         }
</#if>
</#list>
</#if>
        LambdaQueryWrapper<${classInfo.className}Entity> queryWrapper = Wrappers.lambdaQuery();
<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
<#list classInfo.fieldList as fieldItem >
<#if fieldItem.isPrimaryKey==true>
        queryWrapper.eq(${classInfo.className}Entity::get${fieldItem.fieldName?cap_first}, param.get${fieldItem.fieldName?cap_first}());
</#if>
</#list>
</#if>
        ${classInfo.className}Entity entity = ${classInfo.className?uncap_first}Service.getOne(queryWrapper);;
        if (entity == null) {
            //throw new BuException("数据不存在");
            entity = new ${classInfo.className}Entity();
        }
        BeanUtils.copyProperties(param, entity);
        return ${classInfo.className?uncap_first}Service.saveOrUpdate(entity);
    }



    @LogAOP(title = "${classInfo.classComment}-查询单条", isThrow = false)
    public ${classInfo.className}Resp getOne(${classInfo.className}Param param) {
<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
<#list classInfo.fieldList as fieldItem >
<#if fieldItem.isPrimaryKey==true>
         if (ObjectUtils.isEmpty(param.get${fieldItem.fieldName?cap_first}())) {
              throw new BuException("参数[${fieldItem.fieldName}]不能为空");
         }
</#if>
</#list>
</#if>
        LambdaQueryWrapper<${classInfo.className}Entity> queryWrapper = Wrappers.lambdaQuery();
<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
<#list classInfo.fieldList as fieldItem >
<#if fieldItem.isPrimaryKey==true>
        queryWrapper.eq(${classInfo.className}Entity::get${fieldItem.fieldName?cap_first}, param.get${fieldItem.fieldName?cap_first}());
</#if>
</#list>
</#if>
        ${classInfo.className}Entity entity = ${classInfo.className?uncap_first}Service.getOne(queryWrapper);
        ${classInfo.className}Resp resp = new ${classInfo.className}Resp();
        BeanUtils.copyProperties(entity, resp);
        return resp;
    }




    @LogAOP(title = "${classInfo.classComment}-查询列表分页数据", isThrow = false)
    public Rsp<PageData<${classInfo.className}Resp>> queryPage( ${classInfo.className}Param ${classInfo.className?uncap_first}) {
            Long accountId = CrmUserInfoContext.getAccountId();
            if (param.isPage()) {
                PageHelper.startPage(param.getPageNum(), param.getPageSize());
            }
            LambdaQueryWrapper<${classInfo.className}Entity> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(${classInfo.className}Entity::getAccountId, accountId);
            queryWrapper.eq(${classInfo.className}Entity::getStatus, YesOrNo.YES.getCode());
            queryWrapper.eq(param.getCode() != null, ${classInfo.className}Entity::getCode, param.getCode());
            queryWrapper.eq(StringUtils.isNotBlank(param.getParentCode()), ${classInfo.className}Entity::getParentCode, param.getParentCode());
            queryWrapper.like(StringUtils.isNotBlank(param.getName()), ${classInfo.className}Entity::getName, param.getName());
            queryWrapper.orderByDesc(${classInfo.className}Entity::getId));
            List<${classInfo.className}Entity> list = ${classInfo.className}Mapper.selectList(queryWrapper);
            Function<${classInfo.className}Entity, ${classInfo.className}Resp> fun = (s) -> BeanUtil.copyProperties(s, ${classInfo.className}Resp.class);
            return Rsp.success(Page.of(list, fun));
    }

    @LogAOP(title = "${classInfo.classComment}-查询全部列表数据", isThrow = false)
    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST})
    //@RequiresPermissions("${classInfo.className?uncap_first}:list")
    public Resp findListByPage(@RequestBody ${classInfo.className}Param ${classInfo.className?uncap_first}) {
        LambdaQueryWrapper<${classInfo.className}Entity> queryWrapper = Wrappers.lambdaQuery();
<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
<#list classInfo.fieldList as fieldItem >
<#if fieldItem.isPrimaryKey==true>
        queryWrapper.eq(ObjectUtils.isNotEmpty(${classInfo.className?uncap_first}.get${fieldItem.fieldName?cap_first}()),${classInfo.className}Entity::get${fieldItem.fieldName?cap_first}, ${classInfo.className?uncap_first}.get${fieldItem.fieldName?cap_first}());
<#else>
        queryWrapper.eq(ObjectUtils.isNotEmpty(${classInfo.className?uncap_first}.get${fieldItem.fieldName?cap_first}()),${classInfo.className}Entity::get${fieldItem.fieldName?cap_first}, ${classInfo.className?uncap_first}.get${fieldItem.fieldName?cap_first}());
</#if>
</#list>
</#if>
        List<${classInfo.className}Entity> list = ${classInfo.className?uncap_first}Service.list(queryWrapper);
        return Rsp.success(list);
    }



}



