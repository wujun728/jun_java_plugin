package com.cosmoplat.controller.sys;

import com.cosmoplat.common.aop.annotation.LogAnnotation;
import com.cosmoplat.common.utils.DataResult;
import com.cosmoplat.entity.sys.vo.req.UserRoleOperationReqVO;
import com.cosmoplat.service.sys.UserRoleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 用户和角色关联
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@RequestMapping("/sys")
@RestController
public class UserRoleController {
    @Resource
    private UserRoleService userRoleService;

    @PostMapping("/user/role")
    @LogAnnotation(title = "用户和角色关联接口", action = "修改或者新增用户角色")
    public DataResult operationUserRole(@RequestBody @Valid UserRoleOperationReqVO vo) {
        userRoleService.addUserRoleInfo(vo);
        return DataResult.success();
    }
}
