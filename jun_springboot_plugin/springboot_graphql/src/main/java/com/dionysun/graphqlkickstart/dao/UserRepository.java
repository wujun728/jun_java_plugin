package com.dionysun.graphqlkickstart.dao;

import com.dionysun.graphqlkickstart.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findUserByNickname(String nickname);
}
