package com.buxiaoxia.business.rest;

import com.buxiaoxia.business.entity.AccessToken;
import com.buxiaoxia.business.entity.Message;
import com.buxiaoxia.business.entity.User;
import com.buxiaoxia.business.repository.UserRepository;
import com.buxiaoxia.system.exception.AuthorizationException;
import com.buxiaoxia.system.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by xw on 2017/3/13.
 * 2017-03-13 14:09
 */
@Slf4j
@RestController
@RequestMapping("/api/v1.0")
public class AuthController {

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity addUser(@RequestBody User user) {
		if (StringUtils.isEmpty(user.getUserName()) ||
				StringUtils.isEmpty(user.getLoginName()) ||
				StringUtils.isEmpty(user.getPassword())
				) {
			return new ResponseEntity<>(new Message("资源不完全"), HttpStatus.BAD_REQUEST);
		}
		if (userRepository.findByLoginName(user.getLoginName()) != null) {
			return new ResponseEntity<>(new Message("用户已经存在"), HttpStatus.IM_USED);
		}
		user.setRoles("user");
		user.setCreateAt(new Date());
		user.setUpdateAt(new Date());
		userRepository.save(user);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}


	@RequestMapping(value = {"/auth/token", "/auth/refresh-token"},
			method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity getToken(@RequestBody User user) {
		if (StringUtils.isEmpty(user.getLoginName()) || StringUtils.isEmpty(user.getPassword())) {
			throw new AuthorizationException("认证失败");
		}
		User userDetail = userRepository.findByLoginNameAndPassword(user.getLoginName(), user.getPassword());
		if (userDetail == null) {
			return new ResponseEntity<>(new Message("认证失败"), HttpStatus.UNAUTHORIZED);
		} else {
			AccessToken accessToken = new AccessToken();
			accessToken.setAccess_token(jwtUtil.createJWT(Integer.toString(userDetail.getId()),
					userDetail.getLoginName(), userDetail.getRoles()));
			accessToken.setToken_type("Bearer");
			accessToken.setExpires_in(jwtUtil.getTtl());
			return new ResponseEntity<>(accessToken, HttpStatus.OK);
		}
	}


	@RequestMapping(value = "/users/current", method = RequestMethod.GET)
	public ResponseEntity getUser(final HttpServletRequest request) {
		final Claims claims = (Claims) request.getAttribute("claims");
		String userIdStr = claims.getId();
		if (StringUtils.isEmpty(userIdStr)) {
			return new ResponseEntity<>(new Message("认证失败"), HttpStatus.UNAUTHORIZED);
		}
		Integer userId = Integer.parseInt(userIdStr);
		User user = userRepository.findOne(userId);
		if (user == null) {
			return new ResponseEntity<>(new Message("资源不存在"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
