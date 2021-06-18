package org.typroject.tyboot.api.controller.privilage;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.typroject.tyboot.core.restful.doc.TycloudResource;

/**
 * <p>
 * 角色与菜单关系表 前端控制器
 * </p>
 *
 * @author Wujun
 * @since 2017-08-18
 */
@RestController
@TycloudResource(module = "privilege",value = "agencymenu")
@RequestMapping(value = "/v1/privilege/agencymenu")
@Api(tags = "privilege-机构菜单权限")
public class AgencyMenuResource {
	
}
