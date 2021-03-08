package com.sam.demo.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.demo.remote.config.RemoteClient;
import com.sam.demo.server.service.OrgService;

@RestController
@RequestMapping(value = { "/user", "/users" })
public class UserController {

	@RemoteClient("${host.uas}")
	private OrgService orgService;

	@RequestMapping(value = { "/remote" })
	public String createUser() {
		return orgService.getOrgName(102);
	}

}
