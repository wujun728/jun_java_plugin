package com.ketayao.learn.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ketayao.learn.entity.Dog;

/** 
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * @since   2013年12月5日 上午11:02:22 
 */
public interface DogDao extends JpaRepository<Dog, Long>{

}
