package com.dionysun.graphqlkickstart.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.dionysun.graphqlkickstart.dao.UserRepository;
import com.dionysun.graphqlkickstart.entity.Article;
import com.dionysun.graphqlkickstart.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ArticleResolver implements GraphQLResolver<Article> {
    private final UserRepository userRepository;

    public ArticleResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User author(Article article) {
        return userRepository.findById(article.getAuthorId()).get();
    }
}
