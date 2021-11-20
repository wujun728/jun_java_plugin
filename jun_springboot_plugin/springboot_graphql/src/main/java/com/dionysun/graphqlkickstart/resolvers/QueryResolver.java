package com.dionysun.graphqlkickstart.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dionysun.graphqlkickstart.dao.ArticleRepository;
import com.dionysun.graphqlkickstart.dao.UserRepository;
import com.dionysun.graphqlkickstart.entity.Article;
import com.dionysun.graphqlkickstart.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QueryResolver implements GraphQLQueryResolver {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public QueryResolver(UserRepository userRepository, ArticleRepository articleRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    public Article article(String title) {
        return articleRepository.findArticleByTitle(title);
    }

    public User user(String nickname) {
        return userRepository.findUserByNickname(nickname);
    }

    public List<User> users() {
        return userRepository.findAll();
    }
}
