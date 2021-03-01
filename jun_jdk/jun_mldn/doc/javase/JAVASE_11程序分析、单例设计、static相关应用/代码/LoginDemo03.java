class Login{	// 这样的操作叫做真实的业务操作
	private String args[] ; 
	public Login(String args[]){
		this.args = args ;
	}
	public boolean validate(){
		String name = this.args[0] ;	// 取出用户名
		String password = this.args[1] ;	// 取出密码
		if(name.equals("abc")&&password.equals("123")){
			return true ;
		}else{
			return false ;
		}
	}
};
public class LoginDemo03{
	public static void main(String args[]){
		if(args.length!=2){
			System.out.println("输入的参数不正确！") ;
			System.exit(1) ;	// 系统退出，只要设置了一个非零的数字
		}
		System.out.println(new Login(args).validate()?"登陆成功！":"登陆失败！") ;
	}
};