package com.zh.springbootjpa.dao;

import com.zh.springbootjpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByNameIs(String name);

    List<User> findByAgeIs(Integer age);

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query("UPDATE User SET age = :age WHERE name = :name")
    void updateAgeByName(@Param("name") String name, @Param("age") Integer age);

}