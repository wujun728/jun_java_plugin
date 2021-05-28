package com.erp.dao.impl;

import org.springframework.stereotype.Repository;

import com.erp.dao.ClientDao;
import com.erp.entity.Oauth2Client;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class ClientDaoImpl implements ClientDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void saveClient(Oauth2Client oc) {
        this.entityManager.merge(oc);
    }

    @Override
    public Oauth2Client findOauth2UsersByName(String name) {
        String hql =" select c from  Oauth2Client c where c.clientName=:clientName";
         Query query =   this.entityManager.createQuery(hql)
        .setParameter("clientName",name);
        return (Oauth2Client) query.getSingleResult();
    }
}
