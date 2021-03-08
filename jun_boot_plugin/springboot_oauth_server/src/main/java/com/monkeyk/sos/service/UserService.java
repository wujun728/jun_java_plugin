package com.monkeyk.sos.service;

import com.monkeyk.sos.service.dto.UserFormDto;
import com.monkeyk.sos.service.dto.UserJsonDto;
import com.monkeyk.sos.service.dto.UserOverviewDto;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Wujun
 */
public interface UserService extends UserDetailsService {

    UserJsonDto loadCurrentUserJsonDto();

    UserOverviewDto loadUserOverviewDto(UserOverviewDto overviewDto);

    boolean isExistedUsername(String username);

    String saveUser(UserFormDto formDto);
}