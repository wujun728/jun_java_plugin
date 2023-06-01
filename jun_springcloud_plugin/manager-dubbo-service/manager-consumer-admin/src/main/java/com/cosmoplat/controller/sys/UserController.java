package com.cosmoplat.controller.sys;

import com.cosmoplat.common.aop.annotation.LogAnnotation;
import com.cosmoplat.common.utils.DataResult;
import com.cosmoplat.common.utils.ExcelUtils;
import com.cosmoplat.entity.sys.SysUser;
import com.cosmoplat.entity.sys.vo.req.LoginReqVO;
import com.cosmoplat.entity.sys.vo.req.UpdatePasswordReqVO;
import com.cosmoplat.service.sys.HttpSessionService;
import com.cosmoplat.service.sys.UserRoleService;
import com.cosmoplat.service.sys.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 用户管理
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@RestController
@RequestMapping("/sys")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private HttpSessionService httpSessionService;

    @PostMapping(value = "/user/login")
    public DataResult login(@RequestBody @Valid LoginReqVO vo, HttpServletRequest httpServletRequest) {
        return DataResult.success(userService.login(vo, httpServletRequest));
    }

    /**
     * 根据token获取用户登陆信息
     *
     * @return
     */
    @GetMapping(value = "/getUserInfo")
    public DataResult getUserInfo() {
        return DataResult.success(userService.getUserInfoByUserId(httpSessionService.getCurrentUserId()));
    }

    @PostMapping("/user/update")
    @LogAnnotation(title = "用户管理", action = "更新用户信息")
//    @RequiresPermissions("sys:user:update")
    public DataResult updateUserInfo(@RequestBody SysUser vo) {
        if (StringUtils.isEmpty(vo.getId())) {
            return DataResult.fail("id不能为空");
        }
        String userId = httpSessionService.getCurrentUserId();
        userService.updateUserInfo(vo, userId);
        return DataResult.success();
    }

    @PostMapping("/user/updateMy")
    @LogAnnotation(title = "用户管理", action = "更新用户信息")
    public DataResult updateUserInfoById(@RequestBody SysUser vo) {
        String userId = httpSessionService.getCurrentUserId();
        vo.setId(userId);
        userService.updateUserInfoMy(vo, userId);
        return DataResult.success();
    }

    @GetMapping("/user/{id}")
    @LogAnnotation(title = "用户管理", action = "查询用户详情")
//    @RequiresPermissions("sys:user:detail")
    public DataResult detailInfo(@PathVariable("id") String id) {
        return DataResult.success(userService.getById(id));
    }

    @GetMapping("/user")
    @LogAnnotation(title = "用户管理", action = "查询用户详情")
    public DataResult youSelfInfo() {
        String userId = httpSessionService.getCurrentUserId();
        return DataResult.success(userService.getById(userId));
    }

    @GetMapping("/user/listByPage")
//    @RequiresPermissions("sys:user:list")
    @LogAnnotation(title = "用户管理", action = "分页获取用户列表")
    public DataResult pageInfo(SysUser vo) {
        return DataResult.success(userService.pageInfo(vo));
    }

    @PostMapping("/user/add")
//    @RequiresPermissions("sys:user:add")
    @LogAnnotation(title = "用户管理", action = "新增用户")
    public DataResult addUser(@RequestBody @Valid SysUser vo) {
        vo.setCreateId(httpSessionService.getCurrentUserId());
        vo.setCreateWhere(1);
        userService.addUser(vo);
        return DataResult.success();
    }

    @GetMapping("/user/logout")
    @LogAnnotation(title = "用户管理", action = "退出")
    public DataResult logout(HttpServletResponse response) {
        userService.logout();
        return DataResult.success();
    }

    @PostMapping("/user/pwd")
    @LogAnnotation(title = "用户管理", action = "更新密码")
    public DataResult updatePwd(@RequestBody UpdatePasswordReqVO vo) {
        String userId = httpSessionService.getCurrentUserId();
        userService.updatePwd(vo, userId);
        return DataResult.success();
    }

    @PostMapping("/user/delete")
    @LogAnnotation(title = "用户管理", action = "删除用户")
    @RequiresPermissions("sys:user:deleted")
    public DataResult deletedUser(@RequestBody List<String> userIds) {
        //删除用户， 删除redis的绑定的角色跟权限

        userService.removeByIds(userIds);
        return DataResult.success();
    }

    @GetMapping("/user/roles/{userId}")
    @LogAnnotation(title = "用户管理", action = "赋予角色-获取所有角色接口")
//    @RequiresPermissions("sys:user:role:detail")
    public DataResult getUserOwnRole(@PathVariable("userId") String userId) {
        DataResult result = DataResult.success();
        result.setData(userService.getUserOwnRole(userId));
        return result;
    }

    @PostMapping("/user/roles/{userId}")
    @LogAnnotation(title = "用户管理", action = "赋予角色-用户赋予角色接口")
//    @RequiresPermissions("sys:user:update:role")
    public DataResult setUserOwnRole(@PathVariable("userId") String userId, @RequestBody List<String> roleIds) {

        userRoleService.setUserOwnRole(userId, roleIds);

        httpSessionService.refreshUerId(userId);
        return DataResult.success();
    }

    @GetMapping("/user/export")
    public void export(SysUser vo, HttpServletResponse httpServletResponse) {
        List<SysUser> list = userService.listAll(vo);
        ExcelUtils.exportExcel(list, "用户信息", "用户信息", SysUser.class, "用户信息.xls", httpServletResponse);
    }

    @PostMapping("/user/importExcel")
    public DataResult importExcel(@RequestParam(required = false) MultipartFile file) {
        List<SysUser> list = ExcelUtils.importExcel(file, 1, 1, SysUser.class);
        return DataResult.success(list);
    }

}
