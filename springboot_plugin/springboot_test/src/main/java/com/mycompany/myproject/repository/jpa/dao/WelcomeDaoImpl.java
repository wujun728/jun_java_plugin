package com.mycompany.myproject.repository.jpa.dao;

import com.mycompany.myproject.repository.jpa.entity.WelcomeEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@CacheConfig(cacheNames = "welcome")
@Repository("welcomeDao")
public class WelcomeDaoImpl implements  WelcomeDao {

    private final static Logger logger = LoggerFactory.getLogger(WelcomeDaoImpl.class);


    @PersistenceContext
    private EntityManager em;

    @Cacheable
    public WelcomeEntity queryWelcomeById(Integer id){
        logger.debug("WelcomeDaoImpl.queryWelcomeById started");

       return (WelcomeEntity)em
               .createQuery("select t from WelcomeEntity t where t.id = :id",WelcomeEntity.class)
               .setParameter("id", id).getSingleResult();
    }

    public int saveWelcome(){
        return  1;
    }
}
