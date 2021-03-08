package com.ketayao.learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ketayao.learn.dao.DogDao;
import com.ketayao.learn.entity.Dog;

/** 
 * @author Wujun
 * @since   2013年12月5日 上午11:02:59 
 */
@Repository
@Transactional
public class DogService {
	@Autowired
	private DogDao dogDao;
	
	public void saveOrUpDate(Dog dog) {
		dogDao.save(dog);
	};
}
