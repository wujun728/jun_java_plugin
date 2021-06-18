package com.andaily.springoauth.service.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON data:
 * {"archived":false,"email":"unity@wdcy.cc","guid":"55b713df1c6f423e842ad68668523c49","phone":"","privileges":["UNITY"],"username":"unity"}
 *
 * @author Shengzhao Li
 */
public class UserDto extends AbstractOauthDto {


    private boolean archived;
    private String email;
    private String uuid;

    private String phone;
    private String username;

    private List<String> privileges = new ArrayList<>();


    public UserDto() {
    }

    public UserDto(String error, String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
    }


    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }
}