package boot.spring.mapper;

import boot.spring.po.Staff;



public interface LoginMapper {
	Staff getpwdbyname(String name);
}
