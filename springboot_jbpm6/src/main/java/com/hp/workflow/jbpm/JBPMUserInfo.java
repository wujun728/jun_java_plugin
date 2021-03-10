package com.hp.workflow.jbpm;

import org.jbpm.services.task.impl.model.UserImpl;
import org.kie.api.task.model.Group;
import org.kie.api.task.model.OrganizationalEntity;
import org.kie.internal.task.api.UserInfo;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Administrator on 2015/08/17.
 */
@Component
public class JBPMUserInfo implements UserInfo {
    public String getDisplayName(OrganizationalEntity organizationalEntity) {
        return "黄朋";
    }

    public Iterator<OrganizationalEntity> getMembersForGroup(Group group) {
        OrganizationalEntity user = new UserImpl("HuangPeng");
        return Arrays.asList(user).iterator();
    }

    public boolean hasEmail(Group group) {
        return false;
    }

    public String getEmailForEntity(OrganizationalEntity organizationalEntity) {
        return null;
    }

    public String getLanguageForEntity(OrganizationalEntity organizationalEntity) {
        return null;
    }
}
