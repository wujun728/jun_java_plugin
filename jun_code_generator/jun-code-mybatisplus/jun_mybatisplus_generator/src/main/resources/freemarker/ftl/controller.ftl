/**
 * @filename:${entityName}Controller ${createTime}
 * @project ${project}  ${version}
 * Copyright(c) 2020 ${author} Co. Ltd. 
 * All right reserved. 
 */
package ${controllerUrl};

import ${abstractControllerUrl}.AbstractController;
import ${entityUrl}.${entityName};
import ${serviceUrl}.${entityName}Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
<#if isSwagger=="true" >
import io.swagger.annotations.Api;
</#if>
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： ${entityComment}API接口层</P>
 * @version: ${version}
 * @author: ${author}
 * @time    ${createTime}
 *
 */
 <#if isSwagger=="true" >
@Api(description = "${entityComment}",value="${entityComment}" )
</#if>
@RestController
@RequestMapping("/${objectName}")
public class ${entityName}Controller extends AbstractController<${entityName}Service,${entityName}>{
	
}