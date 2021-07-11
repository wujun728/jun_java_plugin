package com.wf.ew.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wf.ew.system.model.Authorities;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthoritiesMapper extends BaseMapper<Authorities> {

    List<String> listByUserId(Integer userId);

    List<String> listByRoleId(@Param("roleIds") List<Integer> roleIds);
}
