package com.mycompany.myproject.service;

import com.mycompany.myproject.jooq.tables.pojos.Welcome;

import java.util.List;

public interface JooqService {

    List<Welcome> findWelcomeAll();
}
