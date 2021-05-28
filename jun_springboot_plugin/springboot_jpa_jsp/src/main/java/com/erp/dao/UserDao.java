package com.erp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.erp.entity.Oauth2User;

@Repository
public interface UserDao extends JpaRepository<Oauth2User,Long>{

    @Query("select t from Oauth2User t where username=:username")
    Oauth2User findOauth2UsersByName(@Param("username") String username);



}
