package com.lf.Dao;

import com.lf.bean.userBean;

import java.util.List;

public interface Dao {
      //注册.从前端传过来的数据,要写入到数据库中去
      int addMember(userBean userBean) throws Exception;
       List<userBean> selectAllDao() throws Exception;

}
