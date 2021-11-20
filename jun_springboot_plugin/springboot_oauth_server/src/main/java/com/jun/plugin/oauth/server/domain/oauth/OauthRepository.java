package com.jun.plugin.oauth.server.domain.oauth;

import java.util.List;

import com.jun.plugin.oauth.server.domain.shared.Repository;

/**
 * @author Wujun
 */
public interface OauthRepository extends Repository {

    OauthClientDetails findOauthClientDetails(String clientId);

    List<OauthClientDetails> findAllOauthClientDetails();

    void updateOauthClientDetailsArchive(String clientId, boolean archive);

    void saveOauthClientDetails(OauthClientDetails clientDetails);
}