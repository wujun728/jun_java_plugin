package ${conf.controllerPackage}<#if table.prefix!="">.${table.prefix}</#if>;
<#assign beanName = table.beanName/>
<#assign beanNameuncap_first = beanName?uncap_first/>
<#assign implName = beanNameuncap_first+"ServiceImpl"/>
<#assign serviceName = beanNameuncap_first+"Service"/>
import com.github.wujun728.Routes;
import ${conf.entityPackage}<#if table.prefix!="">.${table.prefix}</#if>.${beanName};
import ${conf.servicePackage}<#if table.prefix!="">.${table.prefix}</#if>.${beanName}Service;
import com.github.wujun728.common.web.BaseController;
import com.github.wujun728.common.web.JsonResult;
import com.github.wujun728.common.web.PageResult;
import com.github.wujun728.common.web.RestDoing;
import com.github.wujun728.Routes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

<#--
import javax.validation.Valid;

import java.util.HashMap;
import java.util.Map;
-->
/**
 * Copyright (C), 2017-2020, cn.zlinks
 * FileName: ${beanName}Controller
 * Author:   Wujun
 * Date:     ${.now}
 * Description: 控制层
 */
@RestController
@RequestMapping(value = Routes.API_VERSION)
public class ${beanName}Controller extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


	@Autowired
	private ${beanName}Service ${serviceName};




	/**
     * @api {post} /${beanNameuncap_first}s/save 01. ${beanName}删除
     * @apiPermission Login in Users
     * @apiGroup  ${beanName}
     * @apiVersion 1.0.1
	 * @apiParam {Number} id <code>必须参数</code> id
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *     "code": 0,
     *     "data": 1
     *     "desc": "Success",
     *     "timestamp": "${.now}:082"
     * }
     * @apiErrorExample {json} Error-Response:
     * HTTP/1.1 200 OK
     * {
     *     "code": 110002,
     *     "desc": "Param is null or error",
     *     "timestamp": "${.now}:479"
     * }
     */
	@RequestMapping(value = "/${beanNameuncap_first}s/delete/{id}", method = RequestMethod.DELETE)
	public JsonResult deleteJson(HttpServletRequest request, @PathVariable("id") int id) {
		RestDoing doing = jsonResult -> {

            int counts = ${serviceName}.deleteById(id);
            jsonResult.data = counts;
        };
        return doing.go(request, logger);
	}


	/**
     * @api {post} /${beanNameuncap_first}s/save 02. ${beanName}详细信息
     * @apiPermission Login in Users
     * @apiGroup  ${beanName}
     * @apiVersion 1.0.1
	<#assign allPropInfo = table.allPropInfo/>
	<#list allPropInfo as prop>
	 * @apiParam {${prop.propertyType}} prop.propertyName <code>必须参数</code> ${beanName}的prop.propertyName
	</#list>
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *     "code": 0,
     *     "data": 1
     *     "desc": "Success",
     *     "timestamp": "${.now}:082"
     * }
     * @apiErrorExample {json} Error-Response:
     * HTTP/1.1 200 OK
     * {
     *     "code": 110002,
     *     "desc": "Param is null or error",
     *     "timestamp": "${.now}:479"
     * }
     */
	@RequestMapping(value = "/${beanNameuncap_first}s/info/{id}")
	public JsonResult info(HttpServletRequest request, @PathVariable("id") Long id) {

		RestDoing doing = jsonResult -> {

			${beanName} entity  = ${serviceName}.queryInfoById(id);
            jsonResult.data = entity;
        };
        return doing.go(request, logger);
	}

	/**
     * @api {post} /${beanNameuncap_first}s/list 03. ${beanName}列表查询
     * @apiPermission Login in Users
     * @apiGroup  ${beanName}
     * @apiVersion 1.0.1
	 * @apiParam {Number} pageNo <code>必须参数</code> 页码，从1开始
	 * @apiParam {Number} pageSize <code>必须参数</code> 页码，每页显示的记录数量
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *     "code": 0,
     *     "data": 1
     *     "desc": "Success",
     *     "timestamp": "${.now}:082"
     * }
     * @apiErrorExample {json} Error-Response:
     * HTTP/1.1 200 OK
     * {
     *     "code": 110002,
     *     "desc": "Param is null or error",
     *     "timestamp": "${.now}:479"
     * }
     */
	@RequestMapping(value = "/${beanNameuncap_first}s/list")
	public JsonResult page(HttpServletRequest request, ${beanName} ${beanNameuncap_first}) {

        RestDoing doing = jsonResult -> {
            ${beanName} pageInfo = getPage(${beanNameuncap_first}, ${beanName}.class);
            PageResult<${beanName}> pageResult = ${serviceName}.findPage(${beanNameuncap_first});
            jsonResult.data = pageResult;
        };
        return doing.go(request, logger);
	}


	/**
     * @api {post} /${beanNameuncap_first}s/save 03. ${beanName}新增
     * @apiPermission Login in Users
     * @apiGroup  ${beanName}
     * @apiVersion 1.0.1
	 <#assign allPropInfo = table.allPropInfo/>
     <#list allPropInfo as prop>
	 * @apiParam {${prop.propertyType}} prop.propertyName <code>必须参数</code> ${beanName}的prop.propertyName
	 </#list>
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *     "code": 0,
     *     "data": 1
     *     "desc": "Success",
     *     "timestamp": "${.now}:082"
     * }
     * @apiErrorExample {json} Error-Response:
     * HTTP/1.1 200 OK
     * {
     *     "code": 110002,
     *     "desc": "Param is null or error",
     *     "timestamp": "${.now}:479"
     * }
     */
	@RequestMapping(value = "/${beanNameuncap_first}s/save", method = RequestMethod.POST)
	public JsonResult add(HttpServletRequest request, @RequestBody ${beanName} ${beanNameuncap_first}) {

  		RestDoing doing = jsonResult -> {

            int counts = ${serviceName}.add(${beanNameuncap_first});
            jsonResult.data = counts;
        };
        return doing.go(request, logger);
	}


	/**
     * @api {post} /${beanNameuncap_first}s/update/{id} 03. ${beanName}修改
     * @apiPermission Login in Users
     * @apiGroup  ${beanName}
     * @apiVersion 1.0.1
     <#assign allPropInfo = table.allPropInfo/>
     <#list allPropInfo as prop>
	 * @apiParam {${prop.propertyType}} prop.propertyName <code>必须参数</code> ${beanName}的prop.propertyName
	 </#list>
     * @apiSuccessExample {json} Success-Response:
     * HTTP/1.1 200 OK
     * {
     *     "code": 0,
     *     "data": 1
     *     "desc": "Success",
     *     "timestamp": "${.now}:082"
     * }
     * @apiErrorExample {json} Error-Response:
     * HTTP/1.1 200 OK
     * {
     *     "code": 110002,
     *     "desc": "Param is null or error",
     *     "timestamp": "${.now}:479"
     * }
     */
	@RequestMapping(value = "/${beanNameuncap_first}s/save", method = RequestMethod.PUT)
	public JsonResult update(HttpServletRequest request, @RequestBody ${beanName} ${beanNameuncap_first}) {

  		RestDoing doing = jsonResult -> {

            int counts = ${serviceName}.update(${beanNameuncap_first});
            jsonResult.data = counts;
        };
        return doing.go(request, logger);
	}

}
