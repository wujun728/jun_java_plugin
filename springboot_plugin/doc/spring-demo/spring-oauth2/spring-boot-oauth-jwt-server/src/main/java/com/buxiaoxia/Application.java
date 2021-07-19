package com.buxiaoxia;

import com.buxiaoxia.business.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Created by xw on 2017/2/20.
 * 2017-02-20 16:51
 */
@RestController
@SpringBootApplication
public class Application {


	/**
	 * 定义用户信息
	 *
	 * @param authentication
	 * @return
	 */
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity getUser(OAuth2Authentication authentication) {
		Authentication oauth = authentication.getUserAuthentication();
		User user = (User) oauth.getPrincipal();
		return new ResponseEntity<>(new HashMap<String, Object>() {{
			put("name", user.getName());
			put("username", user.getUsername());
			put("id", user.getId());
			put("createAt", user.getCreateAt());
			put("auth", user.getAuthorities());
		}}, HttpStatus.OK);
	}


	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

}
