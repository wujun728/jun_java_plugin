package dao.impl;

import org.springframework.stereotype.Repository;

import po.Staff;
import common.dao.BaseDaoImpl;
import dao.UserDao;
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<Staff> implements UserDao{

}
