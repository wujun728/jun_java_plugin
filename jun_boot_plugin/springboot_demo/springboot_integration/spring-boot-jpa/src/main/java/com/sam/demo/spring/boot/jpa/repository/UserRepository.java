package com.sam.demo.spring.boot.jpa.repository;

import org.springframework.stereotype.Repository;

import com.sam.demo.spring.boot.jpa.base.repository.BaseRepository;
import com.sam.demo.spring.boot.jpa.entity.User;

@Repository
public interface UserRepository extends BaseRepository<User, Long>{

}
