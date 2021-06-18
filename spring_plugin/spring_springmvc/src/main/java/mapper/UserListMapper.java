package mapper;

import model.UserList;

public interface UserListMapper {
    int deleteByPrimaryKey(Integer listid);

    int insert(UserList record);

    int insertSelective(UserList record);

    UserList selectByPrimaryKey(Integer listid);

    int updateByPrimaryKeySelective(UserList record);

    int updateByPrimaryKey(UserList record);
}