package com.mycompany.myproject.repository.jpa.dao;

import com.mycompany.myproject.repository.jpa.entity.WelcomeEntity;

public interface WelcomeDao {

    WelcomeEntity queryWelcomeById(Integer id);
}
