package com.mycompany.myproject.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mycompany.myproject.repository.jpa.entity.WelcomeEntity;
import com.mycompany.myproject.jooq.tables.pojos.Welcome;
import com.mycompany.myproject.service.JooqService;
import com.mycompany.myproject.service.MyRedisUserService;
import com.mycompany.myproject.service.WelcomeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@EnableConfigurationProperties
@RequestMapping("welcome/")
@Api("welcome")
public class WelcomeController {
	
	@Value("${spring.application.name}")
	private String name ;
	
	@Autowired
	private WelcomeService welcomeService ;

	@Autowired
	private JooqService jooqService;

	@Autowired
	private MyRedisUserService myRedisUserService;

	
	@GetMapping("jpaList")
	@ApiOperation(value="jpaList", notes="jpaList")
	public List<WelcomeEntity> list(HttpServletRequest request) {

		return welcomeService.jpaList() ;
	}

	@GetMapping("sayWelcome")
	@ApiOperation(value="sayWelcome", notes="sayWelcome")
	public String sayWelcome(HttpServletRequest request) {

		return welcomeService.sayWelcome() + " " + name ;
	}

	@GetMapping("jooqlist")
	@ApiOperation(value="jooqlist", notes="jooqlist")
	public List<Welcome> jooqlist(HttpServletRequest request) {
		return jooqService.findWelcomeAll();
	}

	@RequestMapping(value="saveWelcome/{id}", method = RequestMethod.PUT)
	@ApiOperation(value="saveWelcome", notes="saveWelcome")
	public String saveWelcome(HttpServletRequest request, @PathVariable("id") Integer id) {

		return welcomeService.updateWelcome(id)  ;
	}

	@RequestMapping(value="saveMyRedisUser", method = RequestMethod.PUT)
	@ApiOperation(value="saveMyRedisUser", notes="saveMyRedisUser")
	public String saveMyRedisUser(HttpServletRequest request) {

		myRedisUserService.saveMyRedisUser();

		return "save MyRedisUser success";
	}
	
	@RequestMapping(value="getSession", method = RequestMethod.GET)
	public String getSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
        String sessionId = (String)session.getId();
        String website = (String)session.getAttribute("website");

		return "sessionId:" + sessionId + ", session: "+ website;
	}

	public WelcomeService getWelcomeService() {
		return welcomeService;
	}

	public void setWelcomeService(WelcomeService welcomeService) {
		this.welcomeService = welcomeService;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
