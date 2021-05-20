package mapper;

import model.GoodType;

public interface GoodTypeMapper {
    int deleteByPrimaryKey(Integer typeid);

    int insert(GoodType record);

    int insertSelective(GoodType record);

    GoodType selectByPrimaryKey(Integer typeid);

    int updateByPrimaryKeySelective(GoodType record);

    int updateByPrimaryKey(GoodType record);
}