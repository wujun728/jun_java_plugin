package dao;

import pojo.BizNovel;

public interface BizNovelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BizNovel record);

    int insertSelective(BizNovel record);

    BizNovel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BizNovel record);

    int updateByPrimaryKeyWithBLOBs(BizNovel record);

    int updateByPrimaryKey(BizNovel record);
}