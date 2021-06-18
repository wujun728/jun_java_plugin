package dao.impl;

import org.springframework.stereotype.Repository;

import po.Staff;
import common.dao.BaseDaoImpl;
import dao.StaffDao;
@Repository("staffDao")
public class StaffDaoImpl extends BaseDaoImpl<Staff> implements StaffDao{

}
