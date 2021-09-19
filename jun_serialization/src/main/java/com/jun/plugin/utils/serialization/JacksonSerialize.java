package com.jun.plugin.utils.serialization;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonSerialize {
	
	
	public static void main(String[] args) throws ParseException, IOException {
		User user = new User();
		user.setName("小民");	
		user.setEmail("xiaomin@sina.com");
		user.setAge(20);
		
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		user.setBirthday(dateformat.parse("1996-10-01"));		
		
		/**
		 * ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。
		 * ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。
		 * writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。
		 * writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。
		 * writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。
		 * writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。
		 */
		ObjectMapper mapper = new ObjectMapper();
		
		//User类转JSON
		//输出结果：{"name":"小民","age":20,"birthday":844099200000,"email":"xiaomin@sina.com"}
		String json = mapper.writeValueAsString(user);
		System.out.println(json);
		
		//Java集合转JSON
		//输出结果：[{"name":"小民","age":20,"birthday":844099200000,"email":"xiaomin@sina.com"}]
		List<User> users = new ArrayList<User>();
		users.add(user);
		String jsonlist = mapper.writeValueAsString(users);
		System.out.println(jsonlist);
	} 
	public static void main2(String[] args) throws ParseException, IOException {
		String json = "{\"name\":\"小民\",\"age\":20,\"birthday\":844099200000,\"email\":\"xiaomin@sina.com\"}";
		
		/**
		 * ObjectMapper支持从byte[]、File、InputStream、字符串等数据的JSON反序列化。
		 */
		ObjectMapper mapper = new ObjectMapper();
		User user = mapper.readValue(json, User.class);
		System.out.println(user);
	}
	
	public static void main3(String[] args) throws ParseException, IOException {
		User user = new User();
		user.setName("小民");	
		user.setEmail("xiaomin@sina.com");
		user.setAge(20);
		
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		user.setBirthday(dateformat.parse("1996-10-01"));		
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(user);
		System.out.println(json);
		//输出结果：{"name":"小民","birthday":"1996年09月30日","mail":"xiaomin@sina.com"}
	}
	
	
	
	
	
	
	static  class User {
		private String name;
		
		//不JSON序列化年龄属性
		@JsonIgnore 
		private Integer age;
		
		//格式化日期属性
		@JsonFormat(pattern = "yyyy年MM月dd日")
		private Date birthday;
		
		//序列化email属性为mail
		@JsonProperty("mail")
		private String email;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		
		public Date getBirthday() {
			return birthday;
		}
		public void setBirthday(Date birthday) {
			this.birthday = birthday;
		}
		
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
	}
}
