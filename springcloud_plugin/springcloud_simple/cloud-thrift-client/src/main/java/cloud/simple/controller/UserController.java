package cloud.simple.controller;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cloud.simple.interfaces.*;
import cloud.simple.provider.UserServiceProvider;

@Controller
public class UserController {
	
	
	@Autowired
	UserServiceProvider userServiceProvider;
	
	@ResponseBody
	@RequestMapping(value = "/hello")
	String hello() throws TException {
		UserService.Client svr=userServiceProvider.getBalanceUserService();
		UserDto userDto= svr.getUser();
		return "hi "+userDto.getUsername();
	}
}
