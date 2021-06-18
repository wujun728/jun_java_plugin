package mapper;

import model.GoodTheme;

public interface GoodThemeMapper {
    int deleteByPrimaryKey(Integer themeid);

    int insert(GoodTheme record);

    int insertSelective(GoodTheme record);

    GoodTheme selectByPrimaryKey(Integer themeid);

    int updateByPrimaryKeySelective(GoodTheme record);

    int updateByPrimaryKey(GoodTheme record);
}