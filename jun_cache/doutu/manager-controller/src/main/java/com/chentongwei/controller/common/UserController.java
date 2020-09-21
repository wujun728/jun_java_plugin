package com.chentongwei.controller.common;

import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.entity.common.io.LoginIO;
import com.chentongwei.entity.common.vo.UserVO;
import com.chentongwei.service.common.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 用户控制层接口
 *
 * @author TongWei.Chen 2017-06-10 15:35
 **/
@RestController
@RequestMapping("/common/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 用户登录
     *
     * @param loginIO：登录信息
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(HttpServletRequest request, @Valid LoginIO loginIO) {
        HttpSession session = request.getSession();
        UserVO userVO = userService.login(loginIO);
        session.setAttribute("user", userVO);
        return ResultCreator.getSuccess(userVO);
    }

}
