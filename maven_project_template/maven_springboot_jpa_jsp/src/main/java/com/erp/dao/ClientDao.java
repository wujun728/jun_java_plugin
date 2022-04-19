package com.erp.dao;

import com.erp.entity.Oauth2Client;

public interface ClientDao {

    Oauth2Client  findOauth2UsersByName(String name);
    void saveClient(Oauth2Client oc);



}
