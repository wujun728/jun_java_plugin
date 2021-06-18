package mapper;

import model.Courier;

public interface CourierMapper {
    int deleteByPrimaryKey(Integer courieid);

    int insert(Courier record);

    int insertSelective(Courier record);

    Courier selectByPrimaryKey(Integer courieid);

    int updateByPrimaryKeySelective(Courier record);

    int updateByPrimaryKey(Courier record);
}