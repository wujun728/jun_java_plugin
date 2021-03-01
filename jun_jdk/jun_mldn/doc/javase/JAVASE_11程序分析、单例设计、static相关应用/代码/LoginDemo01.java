public class LoginDemo01{
	public static void main(String args[]){
		String name = args[0] ;	// 取出用户名
		String password = args[1] ;	// 取出密码
		if(name.equals("abc")&&password.equals("123")){
			System.out.println("登陆成功！") ;
		}else{
			System.out.println("登陆失败！") ;
		}
	}
};