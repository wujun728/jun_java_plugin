package com.itstyle.jwt.modules.user.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itstyle.jwt.common.constant.SystemConstant;
import com.itstyle.jwt.common.entity.R;
import com.itstyle.jwt.common.util.JwtUtils;
import com.itstyle.jwt.modules.user.model.User;
import com.itstyle.jwt.modules.user.repository.UserRepository;

@Api(tags ="用户管理")
@RestController
public class LoginController {
	@Autowired
	UserRepository userRepository;
	
	@ApiOperation(value="用户登陆")
	@RequestMapping(value="login",method = RequestMethod.POST)
    public R  login(String username, String password,HttpServletResponse
			response) {
		User user =  userRepository.findByUsername(username);
		if(user!=null){
			if(user.getPassword().equals(password)){
				//把token返回给客户端-->客户端保存至cookie-->客户端每次请求附带cookie参数
				String JWT = JwtUtils.createJWT("1", username, SystemConstant.JWT_TTL);
				return R.ok(JWT);
			}else{
				return R.error();
			}
		}else{
			return R.error();
		}
    }
	@ApiOperation(value="获取用户信息")
	@RequestMapping(value="description",method = RequestMethod.POST)
    public R  description(String username) {
		User user =  userRepository.findByUsername(username);
		return R.ok(user.getDescription());
    }
}
