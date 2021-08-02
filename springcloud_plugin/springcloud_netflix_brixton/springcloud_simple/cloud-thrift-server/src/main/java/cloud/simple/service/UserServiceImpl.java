package cloud.simple.service;

import org.apache.thrift.TException;
import cloud.simple.interfaces.*;

public class UserServiceImpl implements UserService.Iface{

	@Override
	public UserDto getUser() throws TException
	  {		
		UserDto user = new UserDto(1000,"david");
		return user;
	  }

}
