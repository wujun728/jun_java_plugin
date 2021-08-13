package com.mycompany.myproject.repository.jpa.dao;
import com.mycompany.myproject.repository.jpa.entity.WelcomeEntity;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = WelcomeEntity.class , idClass = Integer.class)
public interface WelcomeRepositoryByAnnotation {

    WelcomeEntity getById(Integer id);
}
