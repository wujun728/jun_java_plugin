package com.yzm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.concurrent.BasicFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzm.common.ImgToFile;
import com.yzm.common.cache.GlobalStore;
import com.yzm.scoket.GetCheckV2WebScoket;
import com.yzm.util.DateUtil;
import com.yzm.util.HttpUtil;
import com.yzm.util.IPUtil;
import com.yzm.util.RequestUtil;
import com.yzm.vo.ResponseResult;

@Controller
@RequestMapping(value = "/api")
public class ApiController {

	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
	public final static int timeout = 3000;

	@Value("${12306.img.path}")
	private String imgPath;

	@Autowired
	private TaskExecutor taskExecutor;

	/**
	 * 获取check
	 * 
	 * @param base64
	 * @return
	 * @throws InterruptedException
	 */
	@PostMapping(value = "/v2/getCheck", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseResult getCheck(HttpServletRequest request) {
		long startTime = System.currentTimeMillis();
		String key = UUID.randomUUID().toString();
		ResponseResult.Builder rr = new ResponseResult.Builder().fail();
		if (!DateUtil.isNormalTime()) {
			return rr.message("仅在6:00-23:00提供服务！").build();
		}
		try {
			String img = RequestUtil.get().getRequestPayload(request);
			if (StringUtils.isBlank(img)) {
				return rr.message("缺少验证码图片").build();
			}
			JSONObject jsonObject = JSON.parseObject(img);

			if (jsonObject == null || StringUtils.isBlank(jsonObject.getString("base64"))) {
				return rr.message("缺少验证码图片").build();
			}
			// 另起线程报错图片
			//taskExecutor.execute(new ImgToFile(jsonObject.getString("base64"), imgPath));

			String ip = IPUtil.getIpAddr(request);

			CopyOnWriteArraySet<GetCheckV2WebScoket> webSocketSet = GetCheckV2WebScoket.webSocketSet;
			if (null == webSocketSet) {
				return rr.message("系统异常，验证码识别失败！").build();
			}
			for (GetCheckV2WebScoket item : webSocketSet) {
				try {
					item.sendMessage(key + "&" + jsonObject.getString("base64"));
				} catch (IOException e) {
					e.printStackTrace();
					continue;
				}
			}
			GlobalStore.checkResponseMap.put(key, new BasicFuture<>(null));
			Future<String> future = GlobalStore.checkResponseMap.get(key);
			String check = future.get(timeout, TimeUnit.MILLISECONDS);

			logger.info("API调用成功:{}，耗时:{}ms，地址:{}", check, System.currentTimeMillis() - startTime, ip);
			return rr.success().data(new ModelMap().addAttribute("check", check)).message("识别成功").build();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return rr.message("api调用失败，请联系我，QQ：1274598858").build();
		}
	}

	

}
