package com.dionysun.graphqlkickstart.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.dionysun.graphqlkickstart.dao.ArticleRepository;
import com.dionysun.graphqlkickstart.dao.UserRepository;
import com.dionysun.graphqlkickstart.entity.Article;
import com.dionysun.graphqlkickstart.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MutationResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public MutationResolver(ArticleRepository articleRepository, UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }
    public User addUser(String mail, String nickname, String password) {
        if(userRepository.findUserByNickname(nickname) != null){
            return null;
        }
        return userRepository.save(User.builder()
                .nickname(nickname)
                .mail(mail)
                .password(encoder.encode(password))
                .build());
    }

    public  Article addArticle(String title, String content, String authorId) {
        if(!userRepository.findById(authorId).isPresent()){
            return null;
        }
        return articleRepository.save(Article.builder()
                .authorId(authorId)
                .title(title)
                .content(content)
                .createBy(new Date())
                .thumbUp(0)
                .build());
    }

}
