package com.sam.demo.spring.boot.jpa.json.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sam.demo.spring.boot.jpa.json.entity.UserInfo;
import com.sam.demo.spring.boot.jpa.json.repository.UserInfoRepository;


@Service
public class UserInfoService {

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Transactional
	public UserInfo save(UserInfo info){
		userInfoRepository.save(info);
		return info;
	}
	
	@Transactional
	public UserInfo update(UserInfo info){
		userInfoRepository.save(info);
		return info;
	}
	
	@Transactional
	public UserInfo findOne(Long id){
		return userInfoRepository.findOne(id);
	}
}
