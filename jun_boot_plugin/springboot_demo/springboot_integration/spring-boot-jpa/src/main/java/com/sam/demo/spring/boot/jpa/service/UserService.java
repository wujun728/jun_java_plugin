package com.sam.demo.spring.boot.jpa.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sam.demo.spring.boot.jpa.entity.User;
import com.sam.demo.spring.boot.jpa.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public User findOne(Long id){
		return userRepository.findOne(id);
	}
	
	@Transactional
	public User updateUser(User user){
		userRepository.save(user);
		return user;
	}
	
	@Transactional
	public User deleteUser(Long id){
		User user = userRepository.findOne(id);
		userRepository.delete(user);
		return user;
	}
	
	public  List<User> extendMethod(final String username,final String email){
		return userRepository.findAll(new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> namePath = root.get("username");
				Path<String> emailPath = root.get("email");
				Predicate predicate = cb.or(cb.like(namePath, "z%"),cb.equal(emailPath, email));
				predicate = cb.and(predicate,cb.equal(root.get("mobilePhoneNumber"), "13900000007"));
				List<Order> orderList = Lists.newArrayList(cb.asc(namePath),cb.desc(emailPath));
				query.orderBy(orderList);
				return predicate;
			}			
		});
	}
	
	public List<User> finaAll(){
		return userRepository.findAll();
	}
}
