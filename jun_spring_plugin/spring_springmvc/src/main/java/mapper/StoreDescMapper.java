package mapper;

import model.StoreDesc;

public interface StoreDescMapper {
    int deleteByPrimaryKey(Integer storedescid);

    int insert(StoreDesc record);

    int insertSelective(StoreDesc record);

    StoreDesc selectByPrimaryKey(Integer storedescid);

    int updateByPrimaryKeySelective(StoreDesc record);

    int updateByPrimaryKey(StoreDesc record);
}