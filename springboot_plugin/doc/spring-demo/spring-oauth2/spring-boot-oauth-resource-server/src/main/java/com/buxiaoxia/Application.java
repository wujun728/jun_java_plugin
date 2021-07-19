package com.buxiaoxia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xw on 2017/3/19.
 * 2017-03-19 15:55
 */
@RestController
@EnableResourceServer
@SpringBootApplication
public class Application {


	@RequestMapping(value = "/auth", method = RequestMethod.GET)
	public ResponseEntity authDemo(OAuth2Authentication auth) {
		// 获取当前用户资源
		Map user = (Map) auth.getUserAuthentication().getDetails();
		Integer userId = (Integer) user.get("id");
		String name = (String) user.get("name");
		return new ResponseEntity<>(new HashMap<String, Object>() {{
			put("id", userId);
			put("name", name);
			put("auth", "OK");
		}}, HttpStatus.OK);
	}


	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

}
