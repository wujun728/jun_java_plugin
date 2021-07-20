package com.lin.mapper;

import com.lin.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: LinZhaoguan
 * Date: 2018-03-15
 * Time: 22:54
 */
@Mapper
@Repository
public interface UserMapper {

    List<User> findAll();
}
