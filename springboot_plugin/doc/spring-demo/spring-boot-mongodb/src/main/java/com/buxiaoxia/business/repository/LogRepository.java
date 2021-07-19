package com.buxiaoxia.business.repository;

import com.buxiaoxia.business.entity.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by xw on 2017/2/23.
 * 2017-02-23 12:54
 */
public interface LogRepository extends MongoRepository<Log, String> {
}
