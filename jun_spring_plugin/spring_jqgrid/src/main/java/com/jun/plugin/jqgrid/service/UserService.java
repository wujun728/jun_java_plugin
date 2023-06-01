package com.jun.plugin.jqgrid.service;

import java.util.List;

import com.jun.plugin.jqgrid.model.TableModel;
import com.jun.plugin.jqgrid.model.User;

public interface UserService {
    List<User> getUsers(TableModel<User> tableModel);
    int getUsersAmount();
}
