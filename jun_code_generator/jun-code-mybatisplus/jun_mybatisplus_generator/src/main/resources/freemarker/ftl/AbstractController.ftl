/**
 * @filename:${entityName}Controller ${createTime}
 * @project ${project}  ${version}
 * Copyright(c) 2020 ${author} Co. Ltd. 
 * All right reserved. 
 */
package ${abstractControllerUrl};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gitee.flying.cattle.mdg.aid.JsonResult;
import com.gitee.flying.cattle.mdg.aid.PageParam;
<#if isSwagger=="true" >
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
</#if>

/**   
 * @Description:TODO(${entityComment}API接口层)
 * 
 * @version: ${version}
 * @author:  ${author}
 * @time     ${createTime}
 */
public class AbstractController<S extends IService<T>,T>{
	
	@Autowired
    protected S baseService;

	protected JsonResult<T> result = new JsonResult<T>();
	/**
	 * @explain 查询对象  <swagger GET请求>
	 * @param   对象参数：id
	 * @return  JsonResult
	 * @author  ${author}
	 * @time    ${createTime}
	 */
	@GetMapping("/getById/{id}")
	<#if isSwagger=="true" >
	@ApiOperation(value = "获取对象", notes = "作者：${author}")
	@ApiImplicitParam(paramType="path", name = "id", value = "对象id", required = true, dataType = "Long")
	</#if>
	public JsonResult<T> getById(@PathVariable("id")Long id){
		T obj=baseService.getById(id);
		if (null!=obj ) {
			 result.success(obj);
		}else {
			 result.error("查询对象不存在！");
		}
		return result;
	}
	
	/**
	 * @explain 删除对象
	 * @param   对象参数：id
	 * @return  JsonResult
	 * @author  ${author}
	 * @time    ${createTime}
	 */
	@PostMapping("/deleteById")
	<#if isSwagger=="true" >
	@ApiOperation(value = "删除", notes = "作者：${author}")
	@ApiImplicitParam(paramType="query", name = "id", value = "对象id", required = true, dataType = "Long")
	</#if>
	public JsonResult<T> deleteById(Long id){
		JsonResult<T> result=new JsonResult<T>();
		T obj=baseService.getById(id);
		if (null!=obj) {
		  boolean rsg = baseService.removeById(id);
		  if (rsg) {
			    result.success("删除成功");
		  }else {
			   result.error("删除失败！");
		  }
		}else {
			 result.error("删除的对象不存在！");
		}
		return result;
	}
	
	/**
	 * @explain 添加
	 * @param   对象参数：T
	 * @return  Boolean
	 * @author  ${author}
	 * @time    ${createTime}
	 */
	@PostMapping("/insert")
	<#if isSwagger=="true" >
	@ApiOperation(value = "添加", notes = "作者：${author}")
	</#if>
	public JsonResult<T> insert(T entity){
		JsonResult<T> result=new JsonResult<T>();
		if (null!=entity) {
			boolean rsg = baseService.save(entity);
			if (rsg) {
				  result.success("添加成功");
			  }else {
				  result.error("添加失败！");
			  }
		}else {
			result.error("请传入正确参数！");
		}
		return result;
	}
	
	/**
	 * @explain 修改
	 * @param   对象参数：T
	 * @return  Boolean
	 * @author  ${author}
	 * @time    ${createTime}
	 */
	@PostMapping("/update")
	<#if isSwagger=="true" >
	@ApiOperation(value = "修改", notes = "作者：${author}")
	</#if>
	public JsonResult<T> update(T entity){
		JsonResult<T> result=new JsonResult<T>();
		if (null!=entity) {
			boolean rsg = baseService.updateById(entity);
			if (rsg) {
				  result.success("修改成功");
			  }else {
				  result.error("修改失败！");
			  }
		}else {
			result.error("请传入正确参数！");
		}
		return result;
	}
	
	/**
	 * @explain 分页条件查询用户   
	 * @param   对象参数：AppPage<${entityName}>
	 * @return  PageInfo<${entityName}>
	 * @author  ${author}
	 * @time    ${createTime}
	 */
	@GetMapping("/getPages")
	<#if isSwagger=="true" >
	@ApiOperation(value = "分页查询", notes = "分页查询返回[IPage<T>],作者：${author}")
	</#if>
	public JsonResult<IPage<T>> getPages(PageParam<T> param){
		JsonResult<IPage<T>> returnPage=new JsonResult<IPage<T>>();
		Page<T> page=new Page<T>(param.getPageNum(),param.getPageSize());
		QueryWrapper<T> queryWrapper =new QueryWrapper<T>();
		queryWrapper.setEntity(param.getParam());
		//分页数据
		IPage<T> pageData=baseService.page(page, queryWrapper);
		returnPage.success(pageData);
		
		return returnPage;
	}
}
