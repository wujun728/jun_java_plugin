package com.jun.plugin.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.jun.plugin.restful.model.User;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends JpaRepository<User, Long> {

    @RestResource(path = "nickname", rel = "nickname")
    List<User> findByNickname(@Param("nickname") String nickname);

}
