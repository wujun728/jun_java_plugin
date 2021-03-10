package com.zhaodui.springboot.system.mapper;

import com.zhaodui.springboot.common.mapper.BaseMapper;
import com.zhaodui.springboot.system.model.SysDepart;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SysDepartMapper extends BaseMapper<SysDepart> {

    /**
     * 根据用户ID查询部门集合
     */
    public List<SysDepart> queryUserDeparts(@org.springframework.data.repository.query.Param("userId") String userId);

    /**
     * 根据用户名查询部门
     *
     * @param username
     * @return
     */
    public List<SysDepart> queryDepartsByUsername(@org.springframework.data.repository.query.Param("username") String username);

    @Select("select id from sys_depart where org_code=#{orgCode}")
    public String queryDepartIdByOrgCode(@org.springframework.data.repository.query.Param("orgCode") String orgCode);

    @Select("select id,parent_id from sys_depart where id=#{departId}")
    public SysDepart getParentDepartId(@org.springframework.data.repository.query.Param("departId") String departId);

}