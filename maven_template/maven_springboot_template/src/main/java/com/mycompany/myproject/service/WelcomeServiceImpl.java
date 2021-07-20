package com.mycompany.myproject.service;


import com.mycompany.myproject.repository.jpa.dao.WelcomeDao;
import com.mycompany.myproject.repository.jpa.dao.WelcomeRepositoryByAnnotation;
import com.mycompany.myproject.repository.jpa.dao.WelcomeRepositoryByInherit;
import com.mycompany.myproject.repository.jpa.entity.WelcomeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("welcomeService")
public class WelcomeServiceImpl implements WelcomeService {

	@Autowired
	private WelcomeDao welcomeDao;

	@Autowired
	private WelcomeRepositoryByInherit welcomeRepositoryByInherit;

	@Autowired
	private WelcomeRepositoryByAnnotation welcomeRepositoryByAnnotation;

	@Cacheable("jpaWelcomeList")
	public List<WelcomeEntity> jpaList() {
//		welcomeDao.queryWelcomeById(1).getWelcomeMsg();
//		WelcomeEntity entity = welcomeRepositoryByAnnotation.getById(1);

		List<WelcomeEntity> list =  welcomeRepositoryByInherit.findAll();
		return list ;
	}


	public String sayWelcome() {
		welcomeDao.queryWelcomeById(1).getWelcomeMsg();

		WelcomeEntity entity = (WelcomeEntity) welcomeRepositoryByInherit.getOne(1);

		//entity = welcomeRepositoryByAnnotation.getById(1);

		return entity.getWelcomeMsg();
	}

	@Transactional
	public String addWelcome() {
		return welcomeDao.queryWelcomeById(1).getWelcomeMsg();
	}

	@Transactional
	public String updateWelcome(Integer id) {
		try{
			WelcomeEntity entity = new WelcomeEntity();
			entity.setId(id);
			entity.setWelcomeMsg("欢迎使用");
			welcomeRepositoryByInherit.save(entity);

//			if(entity.getId() == 1){
//				throw new RuntimeException("Transactional test");
//			}
		}catch (Exception e){
			throw e;
		}

		return "SUCCESS";

	}
}
