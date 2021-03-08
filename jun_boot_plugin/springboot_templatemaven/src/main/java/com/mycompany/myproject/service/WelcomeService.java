package com.mycompany.myproject.service;

import com.mycompany.myproject.repository.jpa.entity.WelcomeEntity;

import java.util.List;

public interface WelcomeService {

	List<WelcomeEntity> jpaList();

	String sayWelcome() ;

	String updateWelcome(Integer id);
}
