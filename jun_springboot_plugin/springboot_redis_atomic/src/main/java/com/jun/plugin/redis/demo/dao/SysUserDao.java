package com.jun.plugin.redis.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jun.plugin.redis.demo.pojo.entity.SysUser;

@Repository
public interface SysUserDao extends JpaRepository<SysUser, String> {
}
