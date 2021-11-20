package com.dionysun.graphqlkickstart.dao;

import com.dionysun.graphqlkickstart.entity.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {
    Article findArticleByTitle(String title);
    List<Article> findArticlesByAuthorId(String authorId);
}
