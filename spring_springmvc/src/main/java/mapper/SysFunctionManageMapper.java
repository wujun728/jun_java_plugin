package mapper;

import model.SysFunctionManage;

public interface SysFunctionManageMapper {
    int deleteByPrimaryKey(Integer manageid);

    int insert(SysFunctionManage record);

    int insertSelective(SysFunctionManage record);

    SysFunctionManage selectByPrimaryKey(Integer manageid);

    int updateByPrimaryKeySelective(SysFunctionManage record);

    int updateByPrimaryKey(SysFunctionManage record);
}