package com.jun.plugin.hibernate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.plugin.hibernate.dao.IEmpDao;
import com.jun.plugin.hibernate.modal.EmpEntity;
import com.jun.plugin.hibernate.service.IEmpService;

import java.util.List;

@Service
public class EmpService  implements IEmpService {

    @Autowired
    private IEmpDao empDao;

    @Override
    public int count() {
        return empDao.count();
    }

    @Override
    public EmpEntity findByid(Short empno) {
        return empDao.findByid(empno);
    }

    @Override
    public List<EmpEntity> findList(Integer pageNo, Integer pageSize) {
        return empDao.findList(pageNo,pageSize);
    }

    @Override
    public int addEmp(EmpEntity e) {
        return empDao.addEmp(e);
    }

    @Override
    public int updateEmp(EmpEntity e) {
        return empDao.updateEmp(e);
    }

    @Override
    public int removeEmp(Short empno) {
        return empDao.removeEmp(empno);
    }
}
