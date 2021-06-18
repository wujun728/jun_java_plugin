package com.monkeyk.sos.service.impl;

import com.monkeyk.sos.domain.dto.UserDto;
import com.monkeyk.sos.domain.dto.UserFormDto;
import com.monkeyk.sos.domain.dto.UserJsonDto;
import com.monkeyk.sos.domain.dto.UserOverviewDto;
import com.monkeyk.sos.domain.shared.security.WdcyUserDetails;
import com.monkeyk.sos.domain.user.User;
import com.monkeyk.sos.domain.user.UserRepository;
import com.monkeyk.sos.service.UserService;
import com.monkeyk.sos.service.business.CurrentUserJsonDtoLoader;
import com.monkeyk.sos.service.business.UserFormSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 处理用户, 账号, 安全相关业务
 *
 * @author Wujun
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null || user.archived()) {
            throw new UsernameNotFoundException("Not found any user for username[" + username + "]");
        }

        return new WdcyUserDetails(user);
    }

    @Override
    public UserJsonDto loadCurrentUserJsonDto() {
        CurrentUserJsonDtoLoader dtoLoader = new CurrentUserJsonDtoLoader();
        return dtoLoader.load();
    }

    @Override
    public UserOverviewDto loadUserOverviewDto(UserOverviewDto overviewDto) {
        List<User> users = userRepository.findUsersByUsername(overviewDto.getUsername());
        overviewDto.setUserDtos(UserDto.toDtos(users));
        return overviewDto;
    }

    @Override
    public boolean isExistedUsername(String username) {
        final User user = userRepository.findByUsername(username);
        return user != null;
    }

    @Override
    public String saveUser(UserFormDto formDto) {
        UserFormSaver saver = new UserFormSaver(formDto);
        return saver.save();
    }


}