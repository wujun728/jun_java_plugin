package cn.springboot.mapper.auth;

import org.apache.ibatis.annotations.Mapper;

import cn.springboot.mapper.BaseMapper;
import cn.springboot.model.auth.UserRole;

/** 
 * @Description 用户与角色关系Mapper
 * @author Wujun
 * @date Apr 12, 2017 9:13:44 AM  
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<String, UserRole> {

}
