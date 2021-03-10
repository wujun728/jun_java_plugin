package com.mall.admin.service;


import com.mall.common.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Wujun
 * @version 1.0
 * @create_at 2018/11/2415:13
 */
@Service
@Slf4j
public class AdminService extends AdminBaseService {

    public Map getAdminByLoginName(String loginName) {
        return sqlSessionTemplate.selectOne ("system.admin.findAdminByLoginName", loginName);
    }

    /**
     * 用户登录
     * @param loginName
     * @param password
     * @return
     */
    public ResultCode login(String loginName, String password) {
        ResultCode resultCode = new ResultCode(ResultCode.SUCCESS, "登录成功");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password, "127.0.0.1:8080");
        try {
           // token.setRememberMe(true);
            subject.login(token);
        } catch (Exception e) {
            log.error("登录失败", e);
            if (e instanceof UnknownAccountException) {
                resultCode = new ResultCode(ResultCode.FAIL, "用户不存在");
            } else {
                resultCode = new ResultCode(ResultCode.FAIL, "登录失败");
            }
        }
        return resultCode;
    }

    /**
     * 管理员列表
     * @param params
     * @return
     */
    public List<Map> list(Map params) {
        return sqlSessionTemplate.selectList("system.admin.list", params);
    }
}
