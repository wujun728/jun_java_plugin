package com.monkeyk.sos.service;

import com.monkeyk.sos.domain.dto.OauthClientDetailsDto;
import com.monkeyk.sos.domain.oauth.OauthClientDetails;

import java.util.List;

/**
 * @author Shengzhao Li
 */

public interface OauthService {

    OauthClientDetails loadOauthClientDetails(String clientId);

    List<OauthClientDetailsDto> loadAllOauthClientDetailsDtos();

    void archiveOauthClientDetails(String clientId);

    OauthClientDetailsDto loadOauthClientDetailsDto(String clientId);

    void registerClientDetails(OauthClientDetailsDto formDto);
}