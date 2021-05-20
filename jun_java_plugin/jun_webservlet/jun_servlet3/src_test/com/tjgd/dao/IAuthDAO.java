package com.tjgd.dao;

import java.util.List;
import com.tjgd.bean.Auth;

public interface IAuthDAO {
	//---------删除权限----------------
	public void delAuth(int authId); 
	//---------获取所有权限------------
	public List<Auth> listAuths();    
	//---------添加单个权限------------
	public void saveAuth(Auth auth);  
}
