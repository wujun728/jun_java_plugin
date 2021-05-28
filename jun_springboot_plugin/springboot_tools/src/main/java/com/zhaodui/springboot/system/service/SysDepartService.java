package com.zhaodui.springboot.system.service;

import com.zhaodui.springboot.common.service.AbstractService;
import com.zhaodui.springboot.system.mapper.SysDepartMapper;
import com.zhaodui.springboot.system.model.DepartIdModel;
import com.zhaodui.springboot.system.model.SysDepart;
import com.zhaodui.springboot.system.model.SysDepartTreeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
