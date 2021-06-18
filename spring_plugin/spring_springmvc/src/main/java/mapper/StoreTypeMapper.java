package mapper;

import model.StoreType;

public interface StoreTypeMapper {
    int deleteByPrimaryKey(Integer storetypeid);

    int insert(StoreType record);

    int insertSelective(StoreType record);

    StoreType selectByPrimaryKey(Integer storetypeid);

    int updateByPrimaryKeySelective(StoreType record);

    int updateByPrimaryKey(StoreType record);
}