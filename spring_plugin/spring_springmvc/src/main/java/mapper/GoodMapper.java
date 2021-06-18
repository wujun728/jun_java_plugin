package mapper;

import model.Good;

public interface GoodMapper {
    int deleteByPrimaryKey(Integer goodid);

    int insert(Good record);

    int insertSelective(Good record);

    Good selectByPrimaryKey(Integer goodid);

    int updateByPrimaryKeySelective(Good record);

    int updateByPrimaryKey(Good record);
}