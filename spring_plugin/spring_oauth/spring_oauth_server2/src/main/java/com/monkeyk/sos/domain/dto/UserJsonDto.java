package com.monkeyk.sos.domain.dto;

import com.monkeyk.sos.domain.user.Privilege;
import com.monkeyk.sos.domain.user.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shengzhao Li
 */
public class UserJsonDto implements Serializable {


    private String guid;
    private boolean archived;

    private String username;
    private String phone;
    private String email;

    private List<String> privileges = new ArrayList<>();

    public UserJsonDto() {
    }

    public UserJsonDto(User user) {
        this.guid = user.guid();
        this.archived = user.archived();
        this.username = user.username();

        this.phone = user.phone();
        this.email = user.email();

        final List<Privilege> privilegeList = user.privileges();
        for (Privilege privilege : privilegeList) {
            this.privileges.add(privilege.name());
        }
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }
}