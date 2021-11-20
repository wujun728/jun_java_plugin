package com.jun.plugin.upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jun.plugin.upload.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    
}
