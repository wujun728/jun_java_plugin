package dao;

import pojo.BizFile;

public interface BizFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BizFile record);

    int insertSelective(BizFile record);

    BizFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BizFile record);

    int updateByPrimaryKeyWithBLOBs(BizFile record);

    int updateByPrimaryKey(BizFile record);
}