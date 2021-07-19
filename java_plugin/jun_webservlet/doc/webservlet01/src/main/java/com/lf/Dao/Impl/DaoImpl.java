package com.lf.Dao.Impl;
import com.lf.Dao.Dao;
import com.lf.bean.userBean;
import com.lf.util.dbConnector;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * @ClassName: DaoImpl
 * @Description:
 * @Author: 李峰
 * @Date: 2021 年 01月 18 18:10
 * @Version 1.0
 */
public class DaoImpl implements Dao {

    QueryRunner queryRunner = dbConnector.getInstance();

    @Override
    public int addMember(userBean userBean) throws Exception {
        int result=0;
        queryRunner.update("insert into user values(null,?,?,?,?)",
                userBean.getNaem(),userBean.getSex(),userBean.getAddress(),userBean.getDate());
        return result;
    }

    @Override
    public List<userBean> selectAllDao() throws Exception {
        List<userBean> list=null;
        queryRunner.query("select * from user",new BeanListHandler<>(userBean.class));
        return list;

    }
}
