package cn.mldn.lxh.demo ;
import java.util.HashMap ;
import java.util.Map ;
public class Register {
	private String name ;
	private String age ;	// 这种接收参数的时候如果是用户自己输入的情况下，就是使用String
	private String email ;
	private Map<String,String> errors = null ;
	public Register(){
		this.name = "" ;
		this.age = "" ;
		this.email = "" ;
		this.errors = new HashMap<String,String>() ;
	}
	public boolean isValidate(){
		boolean flag = true ;
		if(!this.name.matches("\\w{6,15}")){
			flag = false ;
			this.name = "" ;	// 清空原本的name内容
			this.errors.put("errname","用户名是6~15位的字母或数字。") ;
		}
		if(!this.email.matches("\\w+@\\w+\\.\\w+\\.?\\w*")){
			flag = false ;
			this.email = "" ;	// 清空原本的name内容
			this.errors.put("erremail","输入的email地址不合法。") ;
		}
		if(!this.age.matches("\\d+")){
			flag = false ;
			this.age = "" ;	// 清空原本的name内容
			this.errors.put("errage","年龄只能是数字。") ;
		}
		return flag ;
	}
	public String getErrorMsg(String key){
		String value = this.errors.get(key) ;
		return value==null?"":value ;
	}
	public void setName(String name){
		this.name = name ;
	}
	public void setAge(String age){
		this.age = age ;
	}
	public void setEmail(String email){
		this.email = email ;
	}
	public String getName(){
		return this.name ;
	}
	public String getAge(){
		return this.age ;
	}
	public String getEmail(){
		return this.email ;
	}
}