package com.sam.demo.spring.boot.jpa.base.repository;

import java.io.Serializable;  
import javax.persistence.EntityManager;  
  
import org.springframework.data.jpa.repository.JpaRepository;  
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;  
import org.springframework.data.repository.core.support.RepositoryFactorySupport;    
/**
 * 持久化操作公共接口抽象工厂
 * @author Wujun
 *
 * @param <T>
 * @param <ID>
 */
public class DefaultRepositoryFactoryBean<T extends JpaRepository<S, ID>, S, ID extends Serializable>  
        extends JpaRepositoryFactoryBean<T, S, ID> { 
	
	public DefaultRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
		super(repositoryInterface);
	}

	/**
	 * 指定创建Repository公共父类的工厂
	 */
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new DefaultRepositoryFactory<T, ID>(entityManager);
	}

	
} 
