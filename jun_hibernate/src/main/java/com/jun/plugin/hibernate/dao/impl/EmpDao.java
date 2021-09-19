package com.jun.plugin.hibernate.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.jun.plugin.hibernate.dao.IEmpDao;
import com.jun.plugin.hibernate.modal.EmpEntity;

import java.util.List;


@Repository
public class EmpDao implements IEmpDao {


    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public int count() {
        Integer cnt = hibernateTemplate.execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) throws HibernateException {
                String hql = "select count(e.empno) from EmpEntity e";
                Query query = session.createQuery(hql);
                Long sum = (Long) query.uniqueResult();
                return sum.intValue();
            }
        });
        return cnt;
    }

    @Override
    public EmpEntity findByid(Short empno) {
        return hibernateTemplate.get(EmpEntity.class,empno);
    }

    @Override
    public List<EmpEntity> findList(Integer pageNo, Integer pageSize) {
        List<EmpEntity> empList = hibernateTemplate.execute(new HibernateCallback<List<EmpEntity>>() {
            @Override
            public List<EmpEntity> doInHibernate(Session session) throws HibernateException {
                String hql = "from EmpEntity ";
                Query query = session.createQuery(hql);
                query.setFirstResult((pageNo-1)*pageSize);//从第几页开始
                query.setMaxResults(pageSize);//获取多少页
                List<EmpEntity> result = query.list();
                return result;
            }
        });
        return empList;
    }

    @Override
    public int addEmp(EmpEntity e) {
        hibernateTemplate.save(e);
        return 1;
    }

    @Override
    public int updateEmp(EmpEntity e) {
        hibernateTemplate.update(e);
        return 1;
    }

    @Override
    public int removeEmp(Short empno) {
        EmpEntity e  = new EmpEntity();
        e.setEmpno(empno);
        hibernateTemplate.delete(e);
        return 1;
    }
}
