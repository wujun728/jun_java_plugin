package com.neo.model.repository.primary;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Wujun
 */
public interface PrimaryRepository extends MongoRepository<PrimaryMongoObject, String> {
}
