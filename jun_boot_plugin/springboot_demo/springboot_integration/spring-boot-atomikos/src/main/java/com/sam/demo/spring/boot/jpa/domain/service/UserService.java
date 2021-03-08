package com.sam.demo.spring.boot.jpa.domain.service;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sam.demo.spring.boot.jpa.primary.PrimaryUser;
import com.sam.demo.spring.boot.jpa.primary.UserPrimaryRepository;
import com.sam.demo.spring.boot.jpa.secondary.SecondaryUser;
import com.sam.demo.spring.boot.jpa.secondary.UserSecondaryRepository;

@Service
public class UserService {

	@Autowired
	private UserPrimaryRepository userPrimaryRepository;
	
	@Autowired
	private UserSecondaryRepository userSecondaryRepository;
	
	public PrimaryUser findPrimaryUser(Long id){
		return userPrimaryRepository.findOne(id);
	}
	
	
	@Transactional
	public void saveUser(String name) throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException{
		PrimaryUser primaryUser = new PrimaryUser();
		primaryUser.setName(name);
		userPrimaryRepository.save(primaryUser);
		
		
		SecondaryUser secondaryUser = new SecondaryUser();
		secondaryUser.setName(name);
		userSecondaryRepository.save(secondaryUser);
		
		//throw new RuntimeException();
	}
	
}
