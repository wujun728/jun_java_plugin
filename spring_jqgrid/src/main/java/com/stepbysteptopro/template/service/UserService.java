package com.stepbysteptopro.template.service;

import java.util.List;

import com.stepbysteptopro.template.model.TableModel;
import com.stepbysteptopro.template.model.User;

public interface UserService {
    List<User> getUsers(TableModel<User> tableModel);
    int getUsersAmount();
}
