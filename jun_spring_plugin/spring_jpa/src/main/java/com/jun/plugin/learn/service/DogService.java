package com.jun.plugin.learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jun.plugin.learn.dao.DogDao;
import com.jun.plugin.learn.entity.Dog;

/** 
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
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
