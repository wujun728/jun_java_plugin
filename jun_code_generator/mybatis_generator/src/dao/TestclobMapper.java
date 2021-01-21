package dao;

import pojo.Testclob;

public interface TestclobMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Testclob record);

    int insertSelective(Testclob record);

    Testclob selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Testclob record);

    int updateByPrimaryKeyWithBLOBs(Testclob record);
}