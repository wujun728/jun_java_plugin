package spring_mvc.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import spring_mvc.serialize.DoubleSerialize;

public class user {
	@JsonIgnore //忽略
	@Max(value=10000)
	public String id;
	private String username;
	private String password;
	@NotNull
	@NotEmpty
	public  String name;
	
	
	@JsonSerialize(using=DoubleSerialize.class) //自定义序列化格式
	@NumberFormat(pattern="#,###.##")
	private Double money;
	
	@Past
	//@JsonFormat(pattern="yyyy年MM月dd日")//日期格式
	@JsonProperty(value="生日") //修改json显示属性名称
	@DateTimeFormat(iso=ISO.DATE)
	private Date birthday;
	
	private String sex;
	private String age;
	private address address;
	private List<address> books;
	private List<address> addresss;
	private Map<String,address> map;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<address> getBooks() {
		return books;
	}
	public void setBooks(List<address> books) {
		this.books = books;
	}
	public user() {
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public address getAddress() {
		return address;
	}
	public void setAddress(address address) {
		this.address = address;
	}
	public List<address> getAddresss() {
		return addresss;
	}
	public void setAddresss(List<address> addresss) {
		this.addresss = addresss;
	}
	
	public Map<String, address> getMap() {
		return map;
	}
	public void setMap(Map<String, address> map) {
		this.map = map;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	
	public user(String id, String name, Double money, String birthday) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		this.id = id;
		this.name = name;
		this.money = money;
		try {
			this.birthday =sdf.parse(birthday);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public String toString() {
		return "user [name=" + name + ", sex=" + sex + ", age=" + age + ", address=" + address + ", addresss="
				+ addresss + ", map=" + map + "money为"+money+", birthday=" + new SimpleDateFormat("yyyy年MM月dd日").format( birthday) + "]";
	}
}
