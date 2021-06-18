package mapper;

import model.SysAdmin;

public interface SysAdminMapper {
    int deleteByPrimaryKey(Integer adminid);

    int insert(SysAdmin record);

    int insertSelective(SysAdmin record);

    SysAdmin selectByPrimaryKey(Integer adminid);

    int updateByPrimaryKeySelective(SysAdmin record);

    int updateByPrimaryKey(SysAdmin record);
}