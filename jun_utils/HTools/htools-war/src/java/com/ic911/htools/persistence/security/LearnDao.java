package com.ic911.htools.persistence.security;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ic911.htools.entity.security.Learn;
/**
 * 知识库持久层
 * @author caoyang
 */
public interface LearnDao extends JpaRepository<Learn,Long>{
	
	public Collection<Learn> findByNumIn(Collection<Integer> nums);
	
}
