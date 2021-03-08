package com.springboot.springbootwebflux.service;

import com.springboot.springbootwebflux.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Version: 1.0
 * @Desc:
 */
public interface UserSerivice {
    Mono<User> getUserById(Long id);
}
