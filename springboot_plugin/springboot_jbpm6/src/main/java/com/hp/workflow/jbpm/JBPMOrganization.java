package com.hp.workflow.jbpm;

import org.jbpm.services.task.impl.model.UserImpl;
import org.kie.api.task.UserGroupCallback;
import org.kie.api.task.model.OrganizationalEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2015/08/17.
 */
@Component
public class JBPMOrganization implements UserGroupCallback {
    public boolean existsUser(String s) {
        return true;
    }

    public boolean existsGroup(String s) {
        return true;
    }

    @Override
    public List<String> getGroupsForUser(String s) {
        return null;
    }

    public List<String> getGroupsForUser(String s, List<String> list, List<String> list1) {
        return null;
    }

    public List<OrganizationalEntity> getUsers(String operators) {
        OrganizationalEntity user = new UserImpl(operators);
        return Arrays.asList(user);
    }
}
