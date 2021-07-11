package com.buxiaoxia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xw on 2017/2/20.
 * 2017-02-20 16:51
 */
@RestController
@SpringBootApplication
public class Application {


	@RequestMapping(value = "/auth", method = RequestMethod.GET)
	public ResponseEntity authDemo(OAuth2Authentication auth) {
		// 获取当前用户资源
		Map user = (Map) auth.getPrincipal();
		return new ResponseEntity<>(new HashMap<String, Object>() {{
			put("username", user.get("user_name"));
			put("name", user.get("name"));
			put("createAt", user.get("createAt"));
			put("auth", "OK");
		}}, HttpStatus.OK);
	}


	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

}
