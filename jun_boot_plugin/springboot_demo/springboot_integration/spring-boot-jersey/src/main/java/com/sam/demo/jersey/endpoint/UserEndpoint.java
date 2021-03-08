package com.sam.demo.jersey.endpoint;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sam.demo.jersey.entity.User;
import com.sam.demo.jersey.service.UserService;

@Component
@Path("/users")
public class UserEndpoint {
	
	/**
	 * 注入Service
	 */
	@Autowired
	private UserService userService;
	
	@GET
	@Path("/show")
	@Produces(MediaType.APPLICATION_JSON)
	public User show() {
		return userService.findOne(1758L);
	}

	/**
	 * 说明：@Path指定请求路径
	 * 	   @PathParam和@QueryParam用于绑定参数
	 *     @Produces指定响应类型
	 * 请求本方法的正确URL为http://127.0.0.1:8080/users/1758?id=123
	 * @param id
	 * @param from/save
	 * @return
	 */
	
	//public User findUser(@QueryParam("id") int id)
	@GET
	@Path("/find")
	@Produces(MediaType.APPLICATION_JSON)
	public User findUser(@QueryParam("id") Long id) {
		return userService.findOne(id);
	}
	
	@GET
	@Path("/find/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User findUserById(@PathParam("id") Long id) {
		return userService.findOne(id);
	}
	
	
	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	public User saveUser(User user) {
		if(user.getCreateDate() == null)
			user.setCreateDate(new DateTime().toDate());
		//user = userService.saveUser(user);
		System.out.println(user.getId());
		System.out.println(user.getUsername());
		return user;
	}
	
	@POST
	@Consumes(value={ MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/save/form")
	@Produces(value={ MediaType.APPLICATION_JSON })
	public User saveUserForm(@FormParam("name") String name) {
		User user = new User();
		user.setUsername(name);	
		user.setCreateDate(new DateTime().toDate());
		user = userService.saveUser(user);
		return user;
	}

}
