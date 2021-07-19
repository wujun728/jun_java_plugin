package com.buxiaoxia.business.repository;

import com.buxiaoxia.business.entity.User;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xw on 2017/3/27.
 * 2017-03-27 18:26
 */
public interface UserRepository extends JpaRepository<User, Integer> {

	// 使用 p0 做key
	@Cacheable(value = "userCache", key = "#p0")
	User findByName(String name);

	// 使用自定义key生成器  二者公用一个userCache
	@Cacheable(value = "userCache", keyGenerator = "myKeyGenerator")
	User findByCardId(String cardId);

	@Override
	@CachePut(value = "userCache", key = "#p0.name")
	User save(User user);

}
