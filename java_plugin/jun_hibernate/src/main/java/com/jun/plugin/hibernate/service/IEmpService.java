package com.jun.plugin.hibernate.service;

import java.util.List;

import com.jun.plugin.hibernate.modal.EmpEntity;

public interface IEmpService {

    int count();

    EmpEntity findByid(Short empno);

    List<EmpEntity> findList(Integer pageNo, Integer pageSize);

    int addEmp(EmpEntity e);

    int updateEmp(EmpEntity e);

    int removeEmp(Short empno);

}
