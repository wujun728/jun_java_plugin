package com.sam.demo.server.service;

import org.springframework.stereotype.Service;

@Service
public class OrgServiceImpl implements OrgService {

	@Override
	public String getOrgName(Integer id) {
		return id + "abcd";
	}

}
