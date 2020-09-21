package app.dao;

import org.springframework.stereotype.Repository;

import app.common.RedisDao;
import app.entity.Department;

@Repository
public class DepartmentDao extends RedisDao<Department> {

}
