package com.zhu.webservice.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zhu.webservice.bean.UserBean;

@Repository
public interface IUserOperate {
    @Select("select * from t_user where uid=#{id}")
	public UserBean selectUserByID(int id);
}
