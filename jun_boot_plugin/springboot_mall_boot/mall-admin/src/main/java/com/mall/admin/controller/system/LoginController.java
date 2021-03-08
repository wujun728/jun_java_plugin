package com.mall.admin.controller.system;

import com.mall.admin.service.AdminService;
import com.mall.common.annotation.Param;
import com.mall.common.annotation.ParamsType;
import com.mall.common.annotation.ParamsValidate;
import com.mall.common.constants.Constants;
import com.mall.common.controller.BaseController;
import com.mall.common.model.JwtToken;
import com.mall.common.model.UserSession;
import com.mall.common.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用户登录接口
 * @author Wujun
 * @version 1.0
 * @create_at 2018/11/2414:44
 */
@RestController
@Slf4j
@RequestMapping("/system/login")
public class LoginController extends BaseController {

    private final JwtToken jwtToken = JwtToken.getJwtToken(Constants.SECRET_KEY);

    @Autowired
    private AdminService adminService;

    @ParamsValidate(params = {
        @Param(name = "loginName", message = "用户名不能为空"),
        @Param(name = "password", message = "密码不能为空")
    }, paramsType = ParamsType.JSON_DATA)
    @PostMapping
    public Map login(@RequestBody Map params) {
        String loginName = (String)params.get("loginName");
        String password = (String)params.get("password");
        ResultCode resultCode = adminService.login(loginName, password);
        params.clear();
        if (resultCode.code == ResultCode.SUCCESS) {
            Map userMap = adminService.getAdminByLoginName(loginName);
            String token = jwtToken.createToken(userMap.get("id"), 24 * 60 * 60 * 1000 * 5); // 默认缓存5天
            params.put("token", token);
        }
        params.put("code", resultCode.getCode());
        params.put("message", resultCode.getMessage());
        return params;
    }

    @GetMapping("unAuth")
    public ResultCode unAuth() {
        return new ResultCode(ResultCode.UN_AUTH_ERROR_CODE, "用户未登录");
    }

    @GetMapping("logout")
    public ResultCode logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new ResultCode(ResultCode.SUCCESS, "退出成功");
    }
}
