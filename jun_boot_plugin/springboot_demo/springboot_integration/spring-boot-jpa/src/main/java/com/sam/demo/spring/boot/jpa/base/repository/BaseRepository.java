package com.sam.demo.spring.boot.jpa.base.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;


/**
 * 持久化操作公共接口
 * 
 * @author Wujun
 *
 * @param <M>  实体类型
 * @param <ID> 实体主键类型
 */
@NoRepositoryBean  
public interface BaseRepository<M, ID extends Serializable> extends JpaRepository<M, ID>, JpaSpecificationExecutor<M> { 
	/**
	 * 定义公共方法
	 * 该方法会被所有BaseRepository子接口继承
	 */
	public void extendMethod();

} 
