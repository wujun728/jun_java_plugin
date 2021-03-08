package com.springboot.springbootjpaquerydsl.service;

import com.springboot.springbootjpaquerydsl.dto.UserDTO;
import com.springboot.springbootjpaquerydsl.model.UserModel;

import java.util.List;

public interface UserService {
    Long update(String id, String nickName);

    Long delete(String id);

    List<String> selectAllNameList();

    List<UserModel> selectAllUserModelList();

    List<UserDTO> selectAllUserDTOList();

    List<String> selectDistinctNameList();

    UserModel selectFirstUser();

    UserModel selectUser(String id);

    String mysqlFuncDemo(String id, String nickName, int age);
}
