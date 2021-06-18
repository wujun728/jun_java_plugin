package com.zhm.ssr.controller;

import com.zhm.ssr.model.DataResult;
import com.zhm.ssr.model.UserInfo;
import com.zhm.ssr.service.UserInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by zhm on 2015/7/10.
 */
@Controller
public class UserController {
    @Autowired
    private UserInfoService userInfoService;
    @RequestMapping("/login")
    public String login(HttpServletRequest request,HttpServletResponse respose) throws IOException {
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            respose.setContentType("text/html; utf-8");
            respose.getWriter().write("nologin");
            return null;
        }else{
            return "login";
        }
    }
    @RequestMapping(value="/user/doLogin")
    public String doLogin(String username,String password,String rememberMe,ModelMap model,HttpServletRequest request,HttpServletResponse response){

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.logout();
        token.setRememberMe((rememberMe!=null&&"1".equals(rememberMe))?true:false);
        subject.login(token);
        try {

            response.sendRedirect(request.getContextPath()+"/home/main.html");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping(value="/user/logout")
    public @ResponseBody String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "success";
    }
    @RequestMapping(value="/user/listAllUserJson")
    public @ResponseBody DataResult<UserInfo> listAllUserJson(int offset,int limit,String search,String order){
        search = search==null?"%%":"%"+search+"%";
        DataResult<UserInfo> result = new DataResult<UserInfo>();
        long totalRecords = userInfoService.findNumsByCond(search);
        result.setTotal(totalRecords);
        List<UserInfo> userInfos = userInfoService.findByCond(search,order,offset,limit);
        result.setRows(userInfos);
        return result;
    }
    @RequiresPermissions("admin:manage")
    @RequestMapping(value="/user/edit/del")
    public @ResponseBody String delUser(int id){
        userInfoService.delInfoById(id);
        return "success";
    }
}
