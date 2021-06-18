public class LoginDemo02{
	public static void main(String args[]){
		if(args.length!=2){
			System.out.println("输入的参数不正确！") ;
			System.exit(1) ;	// 系统退出，只要设置了一个非零的数字
		}
		String name = args[0] ;	// 取出用户名
		String password = args[1] ;	// 取出密码
		if(name.equals("abc")&&password.equals("123")){
			System.out.println("登陆成功！") ;
		}else{
			System.out.println("登陆失败！") ;
		}
	}
};