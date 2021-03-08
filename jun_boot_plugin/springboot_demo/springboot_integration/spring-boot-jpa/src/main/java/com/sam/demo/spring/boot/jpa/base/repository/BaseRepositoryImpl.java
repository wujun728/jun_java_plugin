package com.sam.demo.spring.boot.jpa.base.repository;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

import java.io.Serializable;

/**
 * 持久化操作公共接口实现类
 * 
 * @author Wujun
 *
 * @param <M>
 *            实体类型
 * @param <ID>
 *            实体主键类型
 */
public class BaseRepositoryImpl<M, ID extends Serializable> extends SimpleJpaRepository<M, ID>
		implements BaseRepository<M, ID> {
	
	public BaseRepositoryImpl(Class<M> domainClass, EntityManager em) {
		super(domainClass, em);
	}
	/**
	 * 实现公共方法
	 */
	@Override
	public void extendMethod() {
		System.out.println("extendMethod");
	}

}
