package mapper;

import model.SysFunction;

public interface SysFunctionMapper {
    int deleteByPrimaryKey(Integer funid);

    int insert(SysFunction record);

    int insertSelective(SysFunction record);

    SysFunction selectByPrimaryKey(Integer funid);

    int updateByPrimaryKeySelective(SysFunction record);

    int updateByPrimaryKey(SysFunction record);
}