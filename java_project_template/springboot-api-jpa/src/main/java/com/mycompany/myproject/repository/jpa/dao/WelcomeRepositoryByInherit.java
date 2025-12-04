package com.mycompany.myproject.repository.jpa.dao;

import com.mycompany.myproject.repository.jpa.entity.WelcomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WelcomeRepositoryByInherit extends JpaRepository<WelcomeEntity, Integer>
{
}
