package com.sam.demo.spring.boot.jpa.base.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
/**
 * 持久化操作公共接口工厂
 * @author Wujun
 *
 * @param <T>
 * @param <ID>
 */
public class DefaultRepositoryFactory<M, ID extends Serializable> extends
		JpaRepositoryFactory {

	public DefaultRepositoryFactory(EntityManager entityManager) {
		super(entityManager);
	}
	/**
	 * 创建Repository公共父类实例
	 */
	@Override
	protected <M, ID extends Serializable> SimpleJpaRepository<M, ID> getTargetRepository(
			RepositoryInformation information, EntityManager entityManager) {
		return new BaseRepositoryImpl<M,ID>((Class<M>) information.getDomainType(), entityManager);
	}
	
	/**
	 * 指定Repository公共父类
	 */
	@Override
	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
		return BaseRepositoryImpl.class;
	}

}
