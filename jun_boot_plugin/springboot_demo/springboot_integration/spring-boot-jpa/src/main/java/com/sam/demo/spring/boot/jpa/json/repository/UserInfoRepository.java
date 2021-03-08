package com.sam.demo.spring.boot.jpa.json.repository;

import org.springframework.stereotype.Repository;

import com.sam.demo.spring.boot.jpa.base.repository.BaseRepository;
import com.sam.demo.spring.boot.jpa.json.entity.UserInfo;


@Repository
public interface UserInfoRepository extends BaseRepository<UserInfo, Long>{

}
