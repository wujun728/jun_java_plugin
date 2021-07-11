package kr.hwb.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.hwb.example.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    
}
