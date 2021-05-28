package com.zhaodui.springboot.system.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.zhaodui.springboot.baseutil.JwtUtil;
import com.zhaodui.springboot.baseutil.MD5Util;
import com.zhaodui.springboot.baseutil.PasswordUtil;
import com.zhaodui.springboot.baseutil.RedisUtil;
import com.zhaodui.springboot.common.config.CacheConstant;
import com.zhaodui.springboot.common.config.CommonConstant;
import com.zhaodui.springboot.common.config.DefContants;
import com.zhaodui.springboot.common.controller.BaseController;
import com.zhaodui.springboot.common.mdoel.Result;
import com.zhaodui.springboot.system.aspect.LoginUser;
import com.zhaodui.springboot.system.mapper.SysDepartMapper;
import com.zhaodui.springboot.system.model.SysDepart;
import com.zhaodui.springboot.system.model.SysLoginModel;
import com.zhaodui.springboot.system.model.SysUser;
import com.zhaodui.springboot.system.service.SysDepartService;
import com.zhaodui.springboot.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wujun
 */
@Slf4j
@Api(tags="system")
@RestController
@RequestMapping("/sys")
public class IndexController extends BaseController {

	private static final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";

@Autowired
private SysUserService sysUserService;
@Autowired
private SysDepartService sysDepartService;
@Autowired
private RedisUtil redisUtil;
	/**
	 * 进入登录页面
	 *
	 * @return 登录页面
	 */
	@GetMapping("/testlogin")
	@ApiOperation(value = "测试数据", notes = "测试数据")
	public String login() {
		return "login";
	}

	/**
	 * 执行登录
	 *
	 * @return 主页
	 */
	@GetMapping("/doLogin/{username}")
	@ResponseBody
	@ApiOperation(value = "测试", notes = "测试")
	public Result<?> doLogin(@ApiParam(name = "username", value = "username", required = true)@PathVariable(name = "username")String username,
						  @ApiParam(name = "user01", value = "user01", required = true) @RequestParam(name = "user01")String user01) {

		SysUser user = new SysUser();
		user.setId(username);
		user.setUsername(username);
		user.setRealname(user01);
		sysUserService.add(user);

//		UsernamePasswordToken token = new UsernamePasswordToken("admin", "123456");
//		Subject subject = SecurityUtils.getSubject();
//		subject.login(token);
		return  Result.ok("ok");
	}

	/**
	 * 获取校验码
	 */
	@ApiOperation("获取验证码")
	@GetMapping(value = "/getCheckCode")
	public Result<Map<String,String>> getCheckCode(){
		Result<Map<String,String>> result = new Result<Map<String,String>>();
		Map<String,String> map = new HashMap<String,String>();
		try {
			String code = RandomUtil.randomString(BASE_CHECK_CODES,4);
			String key = MD5Util.MD5Encode(code+System.currentTimeMillis(), "utf-8");
			redisUtil.set(key, code, 60);
			map.put("key", key);
			map.put("code",code);
			result.setResult(map);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	/**
	 * 退出登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public Result<Object> logout(HttpServletRequest request, HttpServletResponse response) {
		//用户退出逻辑
		String token = request.getHeader(DefContants.X_ACCESS_TOKEN);
		if(StringUtils.isEmpty(token)) {
			return Result.error("退出登录失败！");
		}
		String username = JwtUtil.getUsername(token);
		SysUser sysUser = sysUserService.getUserByName(username);
		if(sysUser!=null) {
			log.info(" 用户名:  "+sysUser.getRealname()+",退出成功！ ");
			//清空用户登录Token缓存
			redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + token);
			//清空用户登录Shiro权限缓存
			redisUtil.del(CommonConstant.PREFIX_USER_SHIRO_CACHE + sysUser.getId());
			//清空用户的缓存信息（包括部门信息），例如sys:cache:user::<username>
			redisUtil.del(String.format("%s::%s", CacheConstant.SYS_USERS_CACHE, sysUser.getUsername()));
			//调用shiro的logout
			SecurityUtils.getSubject().logout();
			return Result.ok("退出登录成功！");
		}else {
			return Result.error("Token无效!");
		}
	}
	@ApiOperation("登录接口")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Result<JSONObject> login(@RequestBody SysLoginModel sysLoginModel){
		Result<JSONObject> result = new Result<JSONObject>();
		String username = sysLoginModel.getUsername();
		String password = sysLoginModel.getPassword();
		Object checkCode = redisUtil.get(sysLoginModel.getCheckKey());
		if(checkCode==null) {
			result.error500("验证码失效");
			return result;
		}
		if(!checkCode.equals(sysLoginModel.getCaptcha())) {
			result.error500("验证码错误");
			return result;
		}

		//1. 校验用户是否有效
		SysUser sysUser = sysUserService.getUserByName(username);
		result = (Result<JSONObject>) sysUserService.checkUserIsEffective(sysUser);
		if(!result.isSuccess()) {
			return result;
		}

		//2. 校验用户名或密码是否正确
		String userpassword = PasswordUtil.encrypt(username, password, sysUser.getSalt());
		String syspassword = sysUser.getPassword();
		if (!syspassword.equals(userpassword)) {
			result.error500("用户名或密码错误");
			return result;
		}

		//用户登录信息
		userInfo(sysUser, result);

		return result;
	}

	/**
	 * 用户信息
	 *
	 * @param sysUser
	 * @param result
	 * @return
	 */
	private Result<JSONObject> userInfo(SysUser sysUser, Result<JSONObject> result) {
		String syspassword = sysUser.getPassword();
		String username = sysUser.getUsername();
		// 生成token
		String token = JwtUtil.sign(username, syspassword);
		// 设置token缓存有效时间
		redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
		redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME*2 / 1000);

		// 获取用户部门信息
		JSONObject obj = new JSONObject();
		List<SysDepart> departs = sysDepartService.queryUserDeparts(sysUser.getId());
		obj.put("departs", departs);
		if (departs == null || departs.size() == 0) {
			obj.put("multi_depart", 0);
		} else if (departs.size() == 1) {
			sysUserService.updateUserDepart(username, departs.get(0).getOrgCode());
			obj.put("multi_depart", 1);
		} else {
			obj.put("multi_depart", 2);
		}
		obj.put("token", token);
		obj.put("userInfo", sysUser);
		result.setResult(obj);
		result.success("登录成功");
		return result;
	}

}
