package com.jun.plugin.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jun.plugin.common.BaseController;
import com.jun.plugin.entity.Header;
import com.jun.plugin.entity.User;
import com.jun.plugin.service.TestService;
import com.jun.plugin.util.MsgUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 框架测试类
 * @ClassName: TestController
 * @Description:
 * @author: renkai721
 * @date: 2018年6月25日 下午1:32:32
 */
@Controller
@RequestMapping("/test")
@Slf4j
public class TestController extends BaseController {
	@Autowired
	TestService testService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		System.out.println("==========访问成功==");
		log.info("==========访问成功==");
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("user", "和格");
		mav.addObject("age", 16);
		return mav;
	}
	/**
	 * 测试请求和数据库连接
	 * @author: renkai721
	 * @date: 2018年6月25日 下午1:32:48
	 * @Title: t
	 * @Description:
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@GetMapping(value = "/t/{userId}")
	public String t(@PathVariable("userId") String userId) {
		if ("1".equals(userId)) {
			userId = "201712111427160042";
		}
		System.out.println("用户id=" + userId + "，登录成功！");
		log.info("用户id=" + userId + "，登录成功！");
//		BkSysLog bkSysLog = testService.selectByPrimaryKey(userId);
		Header header = new Header();
		header.setMsg_type("1");
		header.setData_type(2+"");
		return MsgUtil.outJSON(header, null);
	}
	/**
	 * 调用REST接口DEMO，待测试
	 * @author: renkai721
	 * @date: 2018年6月25日 上午9:29:00
	 * @Title: RestTem
	 * @Description:
	 * @param method
	 * @return
	 */
	@RequestMapping("/restTest")
	@ResponseBody
	public User restTest(String method) {
		User user = null;
		// 查找,查看
		if ("get".equals(method)) {
			user = restTemplate.getForObject("http://localhost:8080/tao-manager-web/get/{id}", User.class, "呜呜呜呜");
			// getForEntity与getForObject的区别是可以获取返回值和状态、头等信息
			ResponseEntity<User> re = restTemplate.getForEntity("http://localhost:8080/tao-manager-web/get/{id}",
					User.class, "呜呜呜呜");
			System.out.println(re.getStatusCode());
			System.out.println(re.getBody().getUsername());
			// 新增,创建
		} else if ("post".equals(method)) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("X-Auth-Token", UUID.randomUUID().toString());
			MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<String, String>();
			postParameters.add("id", "啊啊啊");
			postParameters.add("username", "部版本");
			HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(
					postParameters, headers);
			user = restTemplate.postForObject("http://localhost:8080/tao-manager-web/post/aaa", requestEntity,
					User.class);
			// 删除
		} else if ("delete".equals(method)) {
			restTemplate.delete("http://localhost:8080/tao-manager-web/delete/{id}", "aaa");
			// 修改,更新或创建
		} else if ("put".equals(method)) {
			restTemplate.put("http://localhost:8080/tao-manager-web/put/{id}", null, "bbb");
		}
		return user;
	}
	/**
	 * redis DEMO
	 * @author: renkai721
	 * @date: 2018年6月25日 下午1:33:23
	 * @Title: sredis
	 * @Description:
	 * @return
	 */
//	@ResponseBody
//	@GetMapping(value = "/sredis")
//	public String sredis() {
//		stringRedisTemplate.opsForHash().put("xingming", "1", "zhangsan");
//		System.out.println("保存redis,xingming");
//		stringRedisTemplate.opsForHash().get("xingming", "1");
//		System.out.println("读取redis,xingming=" + String.valueOf(stringRedisTemplate.opsForHash().get("xingming", "1")));
//		stringRedisTemplate.opsForHash().delete("xingming", "1");
//		System.out.println("删除redis,xingming");
//		stringRedisTemplate.opsForHash().put("xingming", "1", "李四");
//		System.out.println("保存redis,xingming");
//		stringRedisTemplate.opsForHash().get("xingming", "1");
//		System.out.println("读取redis,xingming=" + String.valueOf(stringRedisTemplate.opsForHash().get("xingming", "1")));
//		return "success";
//	}
}
