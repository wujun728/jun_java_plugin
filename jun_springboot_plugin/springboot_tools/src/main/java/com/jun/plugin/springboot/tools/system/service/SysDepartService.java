package com.jun.plugin.springboot.tools.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.plugin.springboot.tools.common.service.AbstractService;
import com.jun.plugin.springboot.tools.system.mapper.SysDepartMapper;
import com.jun.plugin.springboot.tools.system.model.DepartIdModel;
import com.jun.plugin.springboot.tools.system.model.SysDepart;
import com.jun.plugin.springboot.tools.system.model.SysDepartTreeModel;

import java.util.List;

@Service
public class SysDepartService extends AbstractService<SysDepart> {
    @Autowired(required = false)
    private SysDepartMapper sysDepartMapper;

    public List<SysDepart> queryUserDeparts(String userId) {
        return sysDepartMapper.queryUserDeparts(userId);
    }

    public List<SysDepart> queryDepartsByUsername(String username) {
        return sysDepartMapper.queryDepartsByUsername(username);
    }



}
