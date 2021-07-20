package com.jun.plugin.oauth.server.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.jun.plugin.oauth.server.service.dto.UserFormDto;
import com.jun.plugin.oauth.server.service.dto.UserJsonDto;
import com.jun.plugin.oauth.server.service.dto.UserOverviewDto;

/**
 * @author Wujun
 */
public interface UserService extends UserDetailsService {

    UserJsonDto loadCurrentUserJsonDto();

    UserOverviewDto loadUserOverviewDto(UserOverviewDto overviewDto);

    boolean isExistedUsername(String username);

    String saveUser(UserFormDto formDto);
}