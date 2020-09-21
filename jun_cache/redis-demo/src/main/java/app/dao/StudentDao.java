package app.dao;

import org.springframework.stereotype.Repository;

import app.common.RedisDao;
import app.entity.Student;

@Repository
public class StudentDao extends RedisDao<Student> {

}
