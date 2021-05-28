package com.chentongwei.controller.common;

import com.alibaba.fastjson.JSON;
import com.chentongwei.cache.redis.IBasicCache;
import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.common.entity.io.GetGeetestIO;
import com.chentongwei.common.util.MD5Util;
import com.chentongwei.common.util.geetest.GeetestConstant;
import com.chentongwei.common.util.geetest.GeetestLib;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 使用Get的方式返回challenge和capthca_id,此方式以实现前后端完全分离的开发模式
 */
@RestController
@RequestMapping("/common/captcha")
public class GeetestController {

	private static final Logger LOG = LogManager.getLogger(GeetestController.class);

	@Autowired
	private GeetestConstant geetestConstant;
	@Autowired
	private IBasicCache basicCache;

	/**
	 * 获取验证码
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	@GetMapping("/getCaptcha")
	public Result getCaptcha() throws ServletException, IOException {
		GeetestLib gtSdk = new GeetestLib(geetestConstant.getId(), geetestConstant.getKey(),
				geetestConstant.getNewfailback());
		String resStr = "{}";
		//token
		String token = MD5Util.encode(UUID.randomUUID().toString());
		//进行验证预处理
		int gtServerStatus = gtSdk.preProcess(token);
		
		//将服务器状态设置到redis中，设置5分钟失效
		basicCache.set(gtSdk.gtServerStatusSessionKey + token , String.valueOf(gtServerStatus), 5, TimeUnit.MINUTES);

		resStr = gtSdk.getResponseStr();
		LOG.info("获取验证码成功");
		GetGeetestIO getGeetestIO = JSON.parseObject(resStr, GetGeetestIO.class);
		getGeetestIO.setToken(token);

		return ResultCreator.getSuccess(getGeetestIO);

	}
}
