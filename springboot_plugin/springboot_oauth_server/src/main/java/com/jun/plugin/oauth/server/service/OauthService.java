package com.jun.plugin.oauth.server.service;

import java.util.List;

import com.jun.plugin.oauth.server.domain.oauth.OauthClientDetails;
import com.jun.plugin.oauth.server.service.dto.OauthClientDetailsDto;

/**
 * @author Wujun
 */

public interface OauthService {

    OauthClientDetails loadOauthClientDetails(String clientId);

    List<OauthClientDetailsDto> loadAllOauthClientDetailsDtos();

    void archiveOauthClientDetails(String clientId);

    OauthClientDetailsDto loadOauthClientDetailsDto(String clientId);

    void registerClientDetails(OauthClientDetailsDto formDto);
}