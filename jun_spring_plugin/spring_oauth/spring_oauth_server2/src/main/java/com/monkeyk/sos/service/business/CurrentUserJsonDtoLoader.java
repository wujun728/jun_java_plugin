package com.monkeyk.sos.service.business;

import com.monkeyk.sos.domain.dto.UserJsonDto;
import com.monkeyk.sos.domain.shared.security.WdcyUserDetails;
import com.monkeyk.sos.domain.user.UserRepository;
import com.monkeyk.sos.web.context.BeanProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * 2018/10/14
 *
 * @author Shengzhao Li
 * @since 1.0
 */
public class CurrentUserJsonDtoLoader {


    private transient UserRepository userRepository = BeanProvider.getBean(UserRepository.class);

    public CurrentUserJsonDtoLoader() {
    }

    public UserJsonDto load() {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Object principal = authentication.getPrincipal();

        if (authentication instanceof OAuth2Authentication &&
                (principal instanceof String || principal instanceof org.springframework.security.core.userdetails.User)) {
            OauthUserJsonDtoLoader jsonDtoLoader = new OauthUserJsonDtoLoader((OAuth2Authentication) authentication);
            return jsonDtoLoader.load();
        } else {
            final WdcyUserDetails userDetails = (WdcyUserDetails) principal;
            return new UserJsonDto(userRepository.findByGuid(userDetails.user().guid()));
        }
    }

}
