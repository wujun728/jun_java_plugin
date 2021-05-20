package com.monkeyk.sos.service.business;

import com.monkeyk.sos.domain.dto.UserFormDto;
import com.monkeyk.sos.domain.user.User;
import com.monkeyk.sos.domain.user.UserRepository;
import com.monkeyk.sos.infrastructure.SOSCacheUtils;
import com.monkeyk.sos.web.WebUtils;
import com.monkeyk.sos.web.context.BeanProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2018/10/14
 *
 * @author Shengzhao Li
 * @since 1.0
 */
public class UserFormSaver {


    private static final Logger LOG = LoggerFactory.getLogger(UserFormSaver.class);

    private transient UserRepository userRepository = BeanProvider.getBean(UserRepository.class);


    private UserFormDto formDto;

    public UserFormSaver(UserFormDto formDto) {
        this.formDto = formDto;
    }

    public String save() {

        User user = formDto.newUser();
        userRepository.saveUser(user);
        LOG.debug("{}|Save User: {}", WebUtils.getIp(), user);

        //Add to cache
        SOSCacheUtils.userCache().put(user.username(), user);

        return user.guid();
    }
}
