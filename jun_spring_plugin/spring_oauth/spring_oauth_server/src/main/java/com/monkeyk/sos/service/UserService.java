package com.monkeyk.sos.service;

import com.monkeyk.sos.domain.dto.UserFormDto;
import com.monkeyk.sos.domain.dto.UserJsonDto;
import com.monkeyk.sos.domain.dto.UserOverviewDto;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Shengzhao Li
 */
public interface UserService extends UserDetailsService {

    UserJsonDto loadCurrentUserJsonDto();

    UserOverviewDto loadUserOverviewDto(UserOverviewDto overviewDto);

    boolean isExistedUsername(String username);

    String saveUser(UserFormDto formDto);
}