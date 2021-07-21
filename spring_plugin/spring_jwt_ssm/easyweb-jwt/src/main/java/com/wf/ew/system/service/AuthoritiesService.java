package com.wf.ew.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.wf.ew.system.model.Authorities;

import java.util.List;

public interface AuthoritiesService extends IService<Authorities> {

    List<String> listByUserId(Integer userId);

    List<String> listByRoleIds(List<Integer> roleId);

    List<String> listByRoleId(Integer roleId);

}
