package org.typroject.tyboot.api.controller.privilage;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.typroject.tyboot.core.restful.doc.TycloudResource;

/**
 * <p>
 * 用户角色关系表 前端控制器
 * </p>
 *
 * @author Wujun
 * @since 2017-08-18
 */
@TycloudResource(module = "privilege",value = "userrole")
@RequestMapping(value = "/v1/privilege/userrole")
@Api(tags = "privilege-用户角色")
@RestController
public class UserRoleResource {


}
