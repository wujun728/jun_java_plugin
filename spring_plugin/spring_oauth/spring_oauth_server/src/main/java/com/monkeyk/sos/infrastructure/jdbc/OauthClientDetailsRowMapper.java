/*
 * Copyright (c) 2015 MONKEYK Information Technology Co. Ltd
 * www.monkeyk.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * MONKEYK Information Technology Co. Ltd ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with MONKEYK Information Technology Co. Ltd.
 */
package com.monkeyk.sos.infrastructure.jdbc;

import com.monkeyk.sos.domain.oauth.OauthClientDetails;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;

/**
 * 2015/11/16
 *
 * @author Shengzhao Li
 */
public class OauthClientDetailsRowMapper implements RowMapper<OauthClientDetails> {


    public OauthClientDetailsRowMapper() {
    }

    @Override
    public OauthClientDetails mapRow(ResultSet rs, int i) throws SQLException {
        OauthClientDetails clientDetails = new OauthClientDetails();

        clientDetails.clientId(rs.getString("client_id"));
        clientDetails.resourceIds(rs.getString("resource_ids"));
        clientDetails.clientSecret(rs.getString("client_secret"));

        clientDetails.scope(rs.getString("scope"));
        clientDetails.authorizedGrantTypes(rs.getString("authorized_grant_types"));
        clientDetails.webServerRedirectUri(rs.getString("web_server_redirect_uri"));

        clientDetails.authorities(rs.getString("authorities"));
        clientDetails.accessTokenValidity(getInteger(rs, "access_token_validity"));
        clientDetails.refreshTokenValidity(getInteger(rs, "refresh_token_validity"));

        clientDetails.additionalInformation(rs.getString("additional_information"));
        clientDetails.createTime(rs.getTimestamp("create_time").toLocalDateTime());
        clientDetails.archived(rs.getBoolean("archived"));

        clientDetails.trusted(rs.getBoolean("trusted"));
        clientDetails.autoApprove(rs.getString("autoapprove"));

        return clientDetails;
    }


    private Integer getInteger(ResultSet rs, String columnName) throws SQLException {
        final Object object = rs.getObject(columnName);
        if (object != null) {
            return (Integer) object;
        }
        return null;
    }

}
