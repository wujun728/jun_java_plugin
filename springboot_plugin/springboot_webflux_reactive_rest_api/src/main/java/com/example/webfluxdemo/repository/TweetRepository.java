package com.example.webfluxdemo.repository;

import com.example.webfluxdemo.model.Tweet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by rajeevkumarsingh on 08/09/17.
 */
@Repository
public interface TweetRepository extends ReactiveMongoRepository<Tweet, String> {

}
